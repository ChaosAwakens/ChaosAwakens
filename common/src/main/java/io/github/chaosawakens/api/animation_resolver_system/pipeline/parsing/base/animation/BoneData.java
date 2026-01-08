package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation;

import java.util.Map;

public interface BoneData {

    String getBoneName();

    <KFD extends KeyframeData> Map<KeyframeType, Map<Double, KFD>> getKeyframeData();

}