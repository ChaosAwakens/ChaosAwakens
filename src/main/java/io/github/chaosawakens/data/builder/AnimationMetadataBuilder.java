package io.github.chaosawakens.data.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.chaosawakens.api.animation.AnimationDataHolder;
import io.github.chaosawakens.common.codec.assets.AnimationDataCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.builder.ILoopType;

public class AnimationMetadataBuilder {
    private final ResourceLocation clientLoc;
    private final ObjectOpenHashSet<AnimationDataHolder> storedAnims = new ObjectOpenHashSet<>();

    public AnimationMetadataBuilder(ResourceLocation clientLoc) {
        this.clientLoc = clientLoc;
    }

    public AnimationMetadataBuilder addAnim(AnimationDataHolder heldAnim) {
        storedAnims.add(heldAnim);
        return this;
    }

    public JsonObject serialize() {
        JsonObject animJsonObj = new JsonObject();
        JsonArray actualAnimDataArray = new JsonArray();

        animJsonObj.addProperty("anim_file_loc", this.clientLoc.toString());

        for (AnimationDataHolder heldAnim : storedAnims) {
            JsonObject animDataArray = new JsonObject();

            animDataArray.addProperty("animation_name", heldAnim.getAnimationName());
            animDataArray.addProperty("animation_length", heldAnim.getAnimationLength());
            animDataArray.addProperty("loop_type", ((ILoopType.EDefaultLoopTypes) heldAnim.getLoopType()).toString());

            actualAnimDataArray.add(animDataArray);
        }

        animJsonObj.add("animations", actualAnimDataArray);

        return animJsonObj;
    }
}
