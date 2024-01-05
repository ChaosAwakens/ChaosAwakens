/*

The MIT License (MIT)

Copyright (c) 2020 Joseph Bettendorff a.k.a. "Commoble"

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

 */

package io.github.chaosawakens.api.animation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;

import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * TODO Copied from Databuddy to patch uncaught {@link IllegalStateException} due to accessing the server too early (it's the exception thrown whenever that happens,
 * but this also catches NPE too so dw). Also makes {@link #data} field public to allow for data syncing w/packets.
 * <br> </br>
 * <br> </br>
 * <p>See drullkus's primer on what codecs are and how to assemble them:<br>
 * https://gist.github.com/Drullkus/1bca3f2d7f048b1fe03be97c28f87910</p>
 *
 * <p>Usage for this is as follows:</p>
 *
 * <p>A) Make a codec for your json<br>
 * e.g. if you have a json like</p>
 *
 * <pre>
 * {
 * 	"value": "some:block"
 * }</pre>
 *
 * And we have a data class that looks like<br>
 * <pre>
 * public class BlockHolder
 * {
 * 	public final Block block;
 * 	public BlockHolder(Block block) {this.block = block;}
 * }
 * </pre>
 *
 * Then we can make a codec via<br>
 * <pre>
 * // use the ResourceLocation codec to create a Block codec
 * Codec{@literal <Block>} BLOCK_CODEC = ResourceLocation.CODEC.xmap(ForgeRegistries.BLOCKS::getValue, Block::getRegistryName);
 * // use that to build a codec for BlockHolder. RecordCodecBuilder builds codecs that deserialize {}objects or CompoundNBTs.
 * Codec{@literal <BlockHolder>} BLOCK_HOLDER_CODEC = RecordCodecBuilder.create(instance {@literal ->} instance.group(
 * 		// we can stuff up to 16 fields into the group method, using a codec for each field...
 * 		BLOCK_CODEC.fieldOf("block").forGetter(blockHolder {@literal ->} blockHolder.block)
 * 	).apply(instance, BlockHolder::new); // use the constructor to finish assembling the codec
 * </pre>
 *
 * <p>B) Now that we have a codec, we can register our data manager to the server as a reload listener</p>
 * <pre>
 * public static final CodecJsonDataManager{@literal <BlockHolder>} BLOCK_HOLDERS = new CodecJsonDataManager("block_holders", BLOCK_HOLDER_CODEC, LOGGER);
 * public static void onAddReloadListeners(AddReloadListenerEvent event)
 * {
 * 	event.addListener(BLOCK_HOLDERS);
 * }
 * </pre>
 * <p>The above event runs during server init. The data manager's map will be recreated during server init and when
 * somebody uses the /reload command. Storing this in a static field should be safe in most cases, but if somebody
 * were to somehow be running multiple servers at once then problems may occur.</p>
 *
 * <p>Finally, we can use BLOCK_HOLDERS.getData("some_modid:some_json") to get the data
 * represented by the json at data/modid/block_holders/some_json.json</p>
 *
 * <p>The folder name that we specified as "block_holders" can potentially include subfolders in the name as well,
 * so we (the people who made the data loader) could use our own modid as part of the folder name to avoid
 * name collisions, e.g. using "blockholdermod/block_holders" as the folder name would result in a json with the
 * id "bananas:banana_block" to be located at data/bananas/blockholdermod/block_holders/some_json.json</p>
 */
public class CodecJsonDataManager<T> extends JsonReloadListener
{
    // default gson if unspecified
    private static final Gson STANDARD_GSON = new Gson();

    /** The codec we use to convert jsonelements to Ts **/
    private final Codec<T> codec;

    /** Logger that will log data parsing errors **/
    private final Logger logger;

    private final String folderName;

    /** The raw data that we parsed from json last time resources were reloaded **/
    public Map<ResourceLocation, T> data = new HashMap<>();
    private Optional<Runnable> syncOnReloadCallback = Optional.empty();

    /**
     * Creates a data manager with a standard gson parser
     * @param folderName The name of the data folder that we will load from, vanilla folderNames are "recipes", "loot_tables", etc<br>
     * Jsons will be read from data/all_modids/folderName/all_jsons<br>
     * folderName can include subfolders, e.g. "some_mod_that_adds_lots_of_data_loaders/cheeses"
     * @param codec A codec to deserialize the json into your T, see javadocs above class
     * @param logger A logger that will log json parsing problems when they are caught.
     */
    public CodecJsonDataManager(String folderName, Codec<T> codec, Logger logger)
    {
        this(folderName, codec, logger, STANDARD_GSON);
    }

    /**
     * As above but with a custom GSON
     * @param folderName The name of the data folder that we will load from, vanilla folderNames are "recipes", "loot_tables", etc<br>
     * Jsons will be read from data/all_modids/folderName/all_jsons<br>
     * folderName can include subfolders, e.g. "some_mod_that_adds_lots_of_data_loaders/cheeses"
     * @param codec A codec to deserialize the json into your T, see javadocs above class
     * @param logger A logger that will log json parsing problems when they are caught.
     * @param gson A gson for parsing the raw json data into JsonElements. JsonElement-to-T conversion will be done by the codec,
     * so gson type adapters shouldn't be necessary here
     */
    public CodecJsonDataManager(String folderName, Codec<T> codec, Logger logger, Gson gson)
    {
        super(gson, folderName);
        this.folderName = folderName; // superclass has this but it's a private field
        this.codec = codec;
        this.logger = logger;
    }

    /**
     * Get the data object for the given key
     * @param id A resourcelocation identifying a json; e.g. a json at data/some_modid/folderName/some_json.json has id "some_modid:some_json"
     * @return The java object that was deserializd from the json with the given ID, or null if no such object is associated with that ID
     */
    @Nullable
    public T getData(ResourceLocation id)
    {
        return this.data.get(id);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, IResourceManager resourceManager, IProfiler profiler)
    {
        this.logger.info("Beginning loading of data for data loader: {}", this.folderName);
        this.data = this.mapValues(jsons);
        this.logger.info("Data loader for {} loaded {} jsons", this.folderName, this.data.size());

        // hacky server test until we can find a better way to do this
        boolean isServer = true;
        try
        {
            LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        }
        catch(NullPointerException | IllegalStateException e)
        {
            isServer = false;
        }
        if (isServer)
        {
            // if we're on the server and we are configured to send syncing packets, send syncing packets
            this.syncOnReloadCallback.ifPresent(Runnable::run);
        }
    }

    private Map<ResourceLocation, T> mapValues(Map<ResourceLocation, JsonElement> inputs)
    {
        Map<ResourceLocation, T> newMap = new HashMap<>();

        for (Entry<ResourceLocation, JsonElement> entry : inputs.entrySet())
        {
            ResourceLocation key = entry.getKey();
            JsonElement element = entry.getValue();
            // if we fail to parse json, log an error and continue
            // if we succeeded, add the resulting T to the map
            this.codec.decode(JsonOps.INSTANCE, element)
                    .get()
                    .ifLeft(result -> newMap.put(key, result.getFirst()))
                    .ifRight(partial -> this.logger.error("Failed to parse data json for {} due to: {}", key.toString(), partial.message()));
        }

        return newMap;
    }

    /**
     * This should be called at most once, in a mod constructor (FMLCommonSetupEvent *may* work as well)
     * Calling this method in static init may cause it to be called later than it should be.
     * Calling this method A) causes the data manager to send a data-syncing packet to all players when a server /reloads data,
     * and B) subscribes the data manager to the PlayerLoggedIn event to allow it to sync itself to players when they log in.
     * Be aware that the invoker must still manually subscribe the relevant packet type to the channel themselves.
     * @param <PACKET> the packet type that will be sent on the given channel
     * @param channel The networking channel of your mod
     * @param packetFactory  A packet constructor or factory method that converts the given map to a packet object to send on the given channel
     * @return this manager object
     */
    public <PACKET> CodecJsonDataManager<T> subscribeAsSyncable(final SimpleChannel channel,
                                                                final Function<Map<ResourceLocation, T>, PACKET> packetFactory)
    {
        MinecraftForge.EVENT_BUS.addListener(this.getLoginListener(channel, packetFactory));
        this.syncOnReloadCallback = Optional.of(() -> channel.send(PacketDistributor.ALL.noArg(), packetFactory.apply(this.data)));
        return this;
    }

    /** Generate an event listener function for the player-login-event **/
    private <PACKET> Consumer<PlayerEvent.PlayerLoggedInEvent> getLoginListener(final SimpleChannel channel,
                                                                                final Function<Map<ResourceLocation, T>, PACKET> packetFactory)
    {
        return event -> {
            PlayerEntity player = event.getPlayer();
            if (player instanceof ServerPlayerEntity)
            {
                channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player), packetFactory.apply(this.data));
            }
        };
    }
}

