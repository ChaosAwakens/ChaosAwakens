package io.github.chaosawakens.common.integration;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.BirdEntity;
import io.github.chaosawakens.common.entity.DimetrodonEntity;
import io.github.chaosawakens.common.entity.FrogEntity;
import mcjty.theoneprobe.api.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.InterModComms;

import java.util.function.Function;

public class TheOneProbePlugin {
    private TheOneProbePlugin() {}

    public static void register() {
        InterModComms.sendTo("theoneprobe", "getTheOneProbe", GetTheOneProbe::new);
    }

    public static class GetTheOneProbe implements Function<ITheOneProbe, Void> {
        @Override
        public Void apply(ITheOneProbe iTheOneProbe) {
            iTheOneProbe.registerEntityProvider(new IProbeInfoEntityProvider() {
                @Override
                public String getID() {
                    return ChaosAwakens.MODID + ":default";
                }

                @Override
                public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, PlayerEntity playerEntity, World world, Entity entity, IProbeHitEntityData iProbeHitEntityData) {
                    String s = TextFormatting.stripFormatting(entity.getName().getString());
                    if ("Froakie".equalsIgnoreCase(s)) {
                        iProbeInfo.text(CompoundText.createLabelInfo("Special Frog Species: ", "Blue"));
                    }
                    
                    if (entity instanceof BirdEntity) {
                    	int type = ((BirdEntity) entity).getColorTextureType();
                    	switch(type) {
                    	case 0:
                    		default:
                    			iProbeInfo.text(CompoundText.createLabelInfo("Bird Species: ", "Black (" + type + ")"));
                    		case 1:
                    			iProbeInfo.text(CompoundText.createLabelInfo("Bird Species: ", "Brown (" + type + ")"));
                    		case 2:
                    			iProbeInfo.text(CompoundText.createLabelInfo("Bird Species: ", "Blue (" + type + ")"));
                    		case 3:
                    			iProbeInfo.text(CompoundText.createLabelInfo("Bird Species: ", "Red (" + type + ")"));
                    		case 40:
                    			iProbeInfo.text(CompoundText.createLabelInfo("Bird Species: ", "Black (" + type + ")"));
                    	}
                    }

                    if (entity instanceof FrogEntity) {
                        int type = ((FrogEntity) entity).getFrogType();
                        switch(type) {
                            case 0:
                            default:
                                iProbeInfo.text(CompoundText.createLabelInfo("Frog Species: ", "Green (" + type + ")"));
                                return;
                            case 1:
                                iProbeInfo.text(CompoundText.createLabelInfo("Frog Species: ", "Brown (" + type + ")"));
                                return;
                            case 2:
                                iProbeInfo.text(CompoundText.createLabelInfo("Frog Species: ", "Pink (" + type + ")"));
                                return;
                            case 3:
                                iProbeInfo.text(CompoundText.createLabelInfo("Frog Species: ", "Dark Green (" + type + ")"));
                                return;
                            case 4:
                                iProbeInfo.text(CompoundText.createLabelInfo("Frog Species: ", "Red (" + type + ")"));
                                return;
                            case 5:
                                iProbeInfo.text(CompoundText.createLabelInfo("Frog Species: ", "Orange (" + type + ")"));
                                return;
                            case 6:
                                iProbeInfo.text(CompoundText.createLabelInfo("Frog Species: ", "Pale (" + type + ")"));
                                return;
                            case 7:
                                iProbeInfo.text(CompoundText.createLabelInfo("Frog Species: ", "Yellow (" + type + ")"));
                                return;
                            case 99:
                                iProbeInfo.text(CompoundText.createLabelInfo("Frog Species: ", "Black (" + type + ")"));
                        }
                    }

                    if (entity instanceof DimetrodonEntity) {
                        int type = ((DimetrodonEntity) entity).getDimetrodonType();
                        switch(type) {
                            case 0:
                            default:
                                iProbeInfo.text(CompoundText.createLabelInfo("Dimetrodon Species: ", "Green (" + type + ")"));
                                return;
                            case 1:
                                iProbeInfo.text(CompoundText.createLabelInfo("Dimetrodon Species: ", "Orange (" + type + ")"));
                                return;
                            case 2:
                                iProbeInfo.text(CompoundText.createLabelInfo("Dimetrodon Species: ", "Purple (" + type + ")"));
                        }
                    }
                }
            });
            return null;
        }
    }
}
