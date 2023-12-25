package io.github.chaosawakens.common.network.packets.s2c;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.api.network.ICAPacket;
import io.github.chaosawakens.common.codec.assets.AnimationDataCodec;
import io.github.chaosawakens.common.registry.CADataLoaders;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

public class EnforceAssetsPacket implements ICAPacket {
    private static final Codec<Map<ResourceLocation, AnimationDataCodec>> MAPPER = Codec.unboundedMap(ResourceLocation.CODEC, AnimationDataCodec.CODEC);
    private final Map<ResourceLocation, AnimationDataCodec> data;

    public EnforceAssetsPacket(Map<ResourceLocation, AnimationDataCodec> data) {
        this.data = data;
    }

    public static EnforceAssetsPacket decode(PacketBuffer buf) {
        return new EnforceAssetsPacket(MAPPER.parse(NBTDynamicOps.INSTANCE, buf.readNbt()).result().orElseGet(Object2ObjectLinkedOpenHashMap::new));
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeNbt((CompoundNBT) MAPPER.encodeStart(NBTDynamicOps.INSTANCE, data).result().orElse(new CompoundNBT()));
    }

    @Override
    public void onRecieve(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(this::setAnimMetadata);
        ctx.get().setPacketHandled(true);
    }

    private void setAnimMetadata() {
        CADataLoaders.ANIMATION_DATA.data = data;
    }
}
