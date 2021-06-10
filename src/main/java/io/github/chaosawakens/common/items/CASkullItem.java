package io.github.chaosawakens.common.items;

import com.mojang.authlib.GameProfile;
import io.github.chaosawakens.common.blocks.tileentities.CASkullTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class CASkullItem extends WallOrFloorItem {
    public CASkullItem(Block floorBlockIn, Block wallBlockIn, Item.Properties builder) {
        super(floorBlockIn, wallBlockIn, builder);
    }

    public ITextComponent getDisplayName(ItemStack stack) {
        if (stack.getItem() == Items.PLAYER_HEAD && stack.hasTag()) {
            String s = null;
            CompoundNBT compoundnbt = stack.getTag();
            if (compoundnbt.contains("SkullOwner", 8)) {
                s = compoundnbt.getString("SkullOwner");
            } else if (compoundnbt.contains("SkullOwner", 10)) {
                CompoundNBT compoundnbt1 = compoundnbt.getCompound("SkullOwner");
                if (compoundnbt1.contains("Name", 8)) {
                    s = compoundnbt1.getString("Name");
                }
            }

            if (s != null) {
                return new TranslationTextComponent(this.getTranslationKey() + ".named", s);
            }
        }

        return super.getDisplayName(stack);
    }

    /**
     * Called when an ItemStack with NBT data is read to potentially that ItemStack's NBT data
     */
    public boolean updateItemStackNBT(CompoundNBT nbt) {
        super.updateItemStackNBT(nbt);
        if (nbt.contains("SkullOwner", 8) && !StringUtils.isBlank(nbt.getString("SkullOwner"))) {
            GameProfile gameprofile = new GameProfile((UUID)null, nbt.getString("SkullOwner"));
            gameprofile = CASkullTileEntity.updateGameProfile(gameprofile);
            nbt.put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), gameprofile));
            return true;
        } else {
            return false;
        }
    }
}