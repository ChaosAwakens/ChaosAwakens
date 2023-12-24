package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.CodecJsonDataManager;
import io.github.chaosawakens.common.codec.assets.AnimationDataCodec;

public class CADataLoaders {
    public static final CodecJsonDataManager<AnimationDataCodec> ANIMATION_DATA = new CodecJsonDataManager<>("chaosawakens/animation_metadata", AnimationDataCodec.CODEC, ChaosAwakens.LOGGER);
}
