package io.github.chaosawakens.data.provider;

import com.google.common.collect.Lists;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.AnimationDataHolder;
import io.github.chaosawakens.common.util.FileUtil;
import io.github.chaosawakens.data.builder.provider.AnimationMetadataProvider;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.Minecraft;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.IReloadableResourceManager;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.file.AnimationFile;
import software.bernie.geckolib3.file.AnimationFileLoader;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.shadowed.eliotlash.molang.MolangParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CAAnimationMetadataProvider extends AnimationMetadataProvider {

    public CAAnimationMetadataProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void initializeAnimMetadata() { //TODO Automation doesn't work cuz Minecraft's Resource Manager doesn't exist on the server :p
        createAnimationMetadata(ChaosAwakens.prefix("animations/boss/miniboss/hercules_beetle.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 3.52D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Death", 1.76D, ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME)));

  /*      MolangParser glibMolangParser = GeckoLibCache.getInstance().parser;

        try {
            FileUtil.findClientAnimResources().forEach(animRl -> {
                AnimationFileLoader loader = new AnimationFileLoader();
                IReloadableResourceManager resourceManager = (IReloadableResourceManager) Minecraft.getInstance().getResourceManager();
                AnimationFile animFile = loader.loadAllAnimations(glibMolangParser, animRl, resourceManager);
                List<AnimationDataHolder> animMD = new ObjectArrayList<>();

                animFile.getAllAnimations().forEach(targetAnim -> {
                    AnimationDataHolder newHolder = new AnimationDataHolder(targetAnim.animationName, targetAnim.animationLength, targetAnim.loop);

                    animMD.add(newHolder);
                });

                createAnimationMetadata(animRl, animMD);
            });
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }
}
