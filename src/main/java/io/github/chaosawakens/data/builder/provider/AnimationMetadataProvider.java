package io.github.chaosawakens.data.builder.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.AnimationDataHolder;
import io.github.chaosawakens.data.builder.AnimationMetadataBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AnimationMetadataProvider implements IDataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, AnimationMetadataBuilder> animMDFiles = new LinkedHashMap();
    protected final DataGenerator generator;

    public AnimationMetadataProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(DirectoryCache pCache) throws IOException {
        this.animMDFiles.clear();
        this.initializeAnimMetadata();

        for (Map.Entry<String, AnimationMetadataBuilder> animMDDataEntry : this.animMDFiles.entrySet()) {
            String animFileName = animMDDataEntry.getKey();
            Path targetPath = generator.getOutputFolder().resolve("data/" + ChaosAwakens.MODID + "/animation_metadata/" + animFileName);

            try {
                IDataProvider.save(GSON, pCache, (animMDDataEntry.getValue()).serialize(), targetPath);
            } catch (IOException var7) {
                throw new RuntimeException("Couldn't save particle type file for particle type: " + animFileName, var7);
            }
        }
    }

    @Override
    public String getName() {
        return ChaosAwakens.MODNAME.concat(": Server Animation Metadata");
    }

    protected abstract void initializeAnimMetadata();

    protected AnimationMetadataBuilder createAnimationMetadata(ResourceLocation clientFileLoc, AnimationDataHolder heldAnim) {
        String fileName = clientFileLoc.getPath().substring(clientFileLoc.getPath().lastIndexOf("/") + 1);

        if (this.animMDFiles.containsValue(fileName)) throw new RuntimeException("Animation metadata file '" + fileName + "' has already been registered.");
        else {
            AnimationMetadataBuilder animationMetadata = new AnimationMetadataBuilder(clientFileLoc);

            animationMetadata.addAnim(heldAnim);
            this.animMDFiles.put(fileName, animationMetadata);

            return animationMetadata;
        }
    }

    protected AnimationMetadataBuilder createAnimationMetadata(ResourceLocation clientFileLoc, List<AnimationDataHolder> heldAnims) {
        String fileName = clientFileLoc.getPath().substring(clientFileLoc.getPath().lastIndexOf("/") + 1);

        if (this.animMDFiles.containsValue(fileName)) throw new RuntimeException("Animation metadata file '" + fileName + "' has already been registered.");
        else {
            AnimationMetadataBuilder animationMetadata = new AnimationMetadataBuilder(clientFileLoc);

            heldAnims.forEach(animationMetadata::addAnim);
            this.animMDFiles.put(fileName, animationMetadata);

            return animationMetadata;
        }
    }
}
