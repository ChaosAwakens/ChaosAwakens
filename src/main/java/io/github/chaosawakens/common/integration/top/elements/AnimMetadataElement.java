package io.github.chaosawakens.common.integration.top.elements;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.common.integration.top.TheOneProbePlugin;
import io.github.chaosawakens.mixins.IElementEntityAccessor;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mcjty.theoneprobe.api.IEntityStyle;
import mcjty.theoneprobe.apiimpl.elements.ElementEntity;
import mcjty.theoneprobe.network.NetworkTools;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.List;
import java.util.UUID;

public class AnimMetadataElement extends ElementEntity {
    private final UUID targetAnimatableUUID;
    private final List<String> mappedAnimsByRegex;
    private final List<String> stoppedAnimsByRegex;
    private final int clampedTickCount;

    public AnimMetadataElement(Entity entity, IEntityStyle style) {
        super(entity, style);
        this.targetAnimatableUUID = entity instanceof IAnimatableEntity ? entity.getUUID() : null;
        this.mappedAnimsByRegex = new ObjectArrayList<>();
        this.stoppedAnimsByRegex = new ObjectArrayList<>();

        if (entity instanceof IAnimatableEntity) {
            IAnimatableEntity animatable = (IAnimatableEntity) entity;

            animatable.getWrappedControllers().forEach(curController -> {
                if (curController.getCurrentAnimation() != null && !curController.getCurrentAnimation().animationName.equalsIgnoreCase("None")) {
                    mappedAnimsByRegex.add(curController.getName() + "|" + curController.getCurrentAnimation().animationName + "|" + curController.getAnimSpeed());

                    animatable.getCachedAnimations().forEach(anim -> {
                        if (!anim.getAnimationName().equalsIgnoreCase(curController.getCurrentAnimation().animationName)) {
                            stoppedAnimsByRegex.add(curController.getName() + "|" + curController.getCurrentAnimation().animationName + "|" + curController.getAnimSpeed());
                        }
                    });
                }
            });
        }

        this.clampedTickCount = (int) (Math.ceil(Math.max(ServerLifecycleHooks.getCurrentServer().getNextTickTime() - Util.getMillis(), 0.0) / 50.0) + 3);
    }

    public AnimMetadataElement(PacketBuffer buf) {
        super(buf);
        this.targetAnimatableUUID = buf.readUUID();

        int size = buf.readVarInt();
        int stoppedSize = buf.readVarInt();

        this.mappedAnimsByRegex = new ObjectArrayList<>(size);
        this.stoppedAnimsByRegex = new ObjectArrayList<>(stoppedSize);

        for (int i = 0; i < size; i++) this.mappedAnimsByRegex.add(NetworkTools.readString(buf));
        for (int i = 0; i < stoppedSize; i++) this.stoppedAnimsByRegex.add(NetworkTools.readString(buf));

        this.clampedTickCount = buf.readInt();
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        if (targetAnimatableUUID == null) super.render(matrixStack, x, y);
        else {
            IElementEntityAccessor accessor = (IElementEntityAccessor) this;

            AnimMetadataElementRenderer.render(accessor.getEntityName(), accessor.getEntityNBT(), accessor.getStyle(), matrixStack, x, y, mappedAnimsByRegex, stoppedAnimsByRegex, clampedTickCount);
        }
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        super.toBytes(buf);

        if (targetAnimatableUUID == null) return;

        buf.writeUUID(targetAnimatableUUID);
        buf.writeVarInt(mappedAnimsByRegex.size());
        buf.writeVarInt(stoppedAnimsByRegex.size());

        mappedAnimsByRegex.forEach(mappedAnim -> NetworkTools.writeString(buf, mappedAnim));
        stoppedAnimsByRegex.forEach(stoppedAnim -> NetworkTools.writeString(buf, stoppedAnim));

        buf.writeInt(clampedTickCount);
    }

    @Override
    public int getID() {
        return TheOneProbePlugin.GetTheOneProbe.ANIM_METADATA_ID;
    }
}
