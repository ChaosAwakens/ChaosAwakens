package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.animation.geckolib.object.model.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import org.joml.Vector2d;
import org.joml.Vector3d;

import java.util.Optional;

public class CAModelTestProvider extends CodecBasedDataProvider<GeckolibModelWrapper> {

    public CAModelTestProvider(PackOutput targetOutput) {
        super(CAConstants.MODID, "models", targetOutput, GeckolibModelWrapper.CODEC);
    }

    @Override
    protected void addDataEntries() {
        putElement(CAConstants.prefix("test"), new GeckolibModelWrapper(
                new ModelGeometryInfo(
                        Optional.of(new ModelMetadataInfo(
                                "geometry.test",
                                5,
                                3,
                                22.7,
                                1,
                                new Vector3d(0, 0, 0)
                        )),
                        Util.make(new ObjectArrayList<>(), list -> {
                            list.add(new ModelBoneInfo("bone", new Vector3d(0, 4, 12), Util.make(new ObjectArrayList<>(), list1 -> {
                                list1.add(new ModelCubeInfo(new Vector3d(0, 4, 12), new Vector3d(0, 4, 12), new ModelUVInfo(
                                        new ModelDirectionBasedUVInfo(new Vector2d(2, 0), new Vector2d(1, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(0, 3), new Vector2d(0, 23)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(0, 0), new Vector2d(0, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(0, 123), new Vector2d(-11, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(0, 0), new Vector2d(0, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(12, 4), new Vector2d(0, 0))
                                )));
                                list1.add(new ModelCubeInfo(new Vector3d(12, 4, 2), new Vector3d(0, 4, 12), new ModelUVInfo(
                                        new ModelDirectionBasedUVInfo(new Vector2d(2, 0), new Vector2d(1, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(20, 3), new Vector2d(0, 23)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(0, 0), new Vector2d(0, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(0, 123), new Vector2d(-11, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(0, 0), new Vector2d(0, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(12, 4), new Vector2d(0, 0))
                                )));
                            })));

                            list.add(new ModelBoneInfo("bone 2", new Vector3d(0, 4, 12), Util.make(new ObjectArrayList<>(), list1 -> {
                                list1.add(new ModelCubeInfo(new Vector3d(0, 4, 12), new Vector3d(0, 4, 12), new ModelUVInfo(
                                        new ModelDirectionBasedUVInfo(new Vector2d(2, 0), new Vector2d(1, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(0, 3), new Vector2d(0, 23)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(0, 0), new Vector2d(0, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(0, 123), new Vector2d(-11, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(0, 0), new Vector2d(0, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(12, 4), new Vector2d(0, 0))
                                )));
                                list1.add(new ModelCubeInfo(new Vector3d(12, 4, 2), new Vector3d(0, 4, 12), new ModelUVInfo(
                                        new ModelDirectionBasedUVInfo(new Vector2d(2, 0), new Vector2d(1, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(20, 3), new Vector2d(0, 23)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(0, 0), new Vector2d(0, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(0, 123), new Vector2d(-11, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(0, 0), new Vector2d(0, 0)),
                                        new ModelDirectionBasedUVInfo(new Vector2d(12, 4), new Vector2d(0, 0))
                                )));
                            })));
                        }))
        ));
    }
}
