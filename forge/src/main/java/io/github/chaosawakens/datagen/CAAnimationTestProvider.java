package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.animation.geckolib.math.easing.base.MathematicalEasing;
import io.github.chaosawakens.api.animation.geckolib.object.animation.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import org.joml.Vector3d;

import java.util.Optional;

public class CAAnimationTestProvider extends CodecBasedDataProvider<GeckolibAnimationWrapper> {

    public CAAnimationTestProvider(PackOutput targetOutput) {
        super(CAConstants.MODID, "animations", targetOutput, GeckolibAnimationWrapper.CODEC);
    }

    @Override
    protected void addDataEntries() {
        putElement(CAConstants.prefix("test"), new GeckolibAnimationWrapper(Util.make(new Object2ObjectOpenHashMap<>(), map -> {
            map.put("test animation", new AnimationInfo(Optional.of(false), Optional.of(1.0), Util.make(new ObjectArrayList<>(), list -> {
                list.add(new AnimationBoneInfo("bone", Util.make(new Object2ObjectOpenHashMap<>(), map1 -> {
                    map1.put(KeyframeType.POSITION, Util.make(new Object2ObjectOpenHashMap<>(), map2 -> {
                        map2.put(1.0D, new AnimationKeyframeInfo(new Vector3d(1, 0, 1), Optional.of(MathematicalEasing.QUADRATIC_EASE_IN), Optional.empty()));
                    }));
                })));
            })));

            map.put("test animation 3", new AnimationInfo(Optional.empty(), Optional.of(12.0), Util.make(new ObjectArrayList<>(), list -> {
                list.add(new AnimationBoneInfo("bone", Util.make(new Object2ObjectOpenHashMap<>(), map1 -> {
                    map1.put(KeyframeType.POSITION, Util.make(new Object2ObjectOpenHashMap<>(), map2 -> {
                        map2.put(1.0D, new AnimationKeyframeInfo(new Vector3d(1, 0, 1), Optional.of(MathematicalEasing.QUADRATIC_EASE_IN), Optional.empty()));
                        map2.put(4.0D, new AnimationKeyframeInfo(new Vector3d(1, 0, 1), Optional.of(MathematicalEasing.LINEAR), Optional.of(ObjectArrayList.of(1.0D, 2.0D, 3.0D))));
                    }));
                    map1.put(KeyframeType.ROTATION, Util.make(new Object2ObjectOpenHashMap<>(), map2 -> {
                        map2.put(2.0D, new AnimationKeyframeInfo(new Vector3d(1, 0, 1), Optional.of(MathematicalEasing.QUADRATIC_EASE_IN), Optional.empty()));
                        map2.put(1.0D, new AnimationKeyframeInfo(new Vector3d(1, 0, 1), Optional.of(MathematicalEasing.LINEAR), Optional.of(ObjectArrayList.of(1.0D, 2.0D, 3.0D))));
                    }));
                    map1.put(KeyframeType.SCALE, Util.make(new Object2ObjectOpenHashMap<>(), map2 -> {
                        map2.put(3.0D, new AnimationKeyframeInfo(new Vector3d(1, 0, 1), Optional.empty(), Optional.empty()));
                        map2.put(12.0D, new AnimationKeyframeInfo(new Vector3d(1, 0, 1), Optional.of(MathematicalEasing.LINEAR), Optional.of(ObjectArrayList.of(1.0D, 2.0D, 3.0D))));
                    }));
                })));
            })));

            map.put("test animation 2", new AnimationInfo(Optional.of(true), Optional.empty(), Util.make(new ObjectArrayList<>(), list -> {
                list.add(new AnimationBoneInfo("bone", Util.make(new Object2ObjectOpenHashMap<>(), map1 -> {
                    map1.put(KeyframeType.POSITION, Util.make(new Object2ObjectOpenHashMap<>(), map2 -> {

                    }));
                    map1.put(KeyframeType.SCALE, Util.make(new Object2ObjectOpenHashMap<>(), map2 -> {

                    }));
                })));
            })));
        })));
    }
}
