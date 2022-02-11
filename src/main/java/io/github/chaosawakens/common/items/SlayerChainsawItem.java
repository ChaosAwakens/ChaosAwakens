package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class SlayerChainsawItem extends ExtendedHitWeaponItem implements IVanishable, IAnimatable, ISyncable {
    private static final String CONTROLLER_NAME = "popupController";
    private static final int ANIM = 0;
    public static float attackDamage;
    public AnimationFactory factory = new AnimationFactory(this);

    public SlayerChainsawItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, double reachDistance, double knockBack, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, reachDistance, knockBack, builderIn);
        attackDamage = (float) attackDamageIn + tier.getAttackDamageBonus();
        GeckoLibNetwork.registerSyncable(this);
    }


    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getEntity().getType() == CAEntityTypes.ACACIA_ENT.get() || target.getEntity().getType() == CAEntityTypes.BIRCH_ENT.get() ||
                target.getEntity().getType() == CAEntityTypes.CRIMSON_ENT.get() || target.getEntity().getType() == CAEntityTypes.DARK_OAK_ENT.get() ||
                target.getEntity().getType() == CAEntityTypes.JUNGLE_ENT.get() || target.getEntity().getType() == CAEntityTypes.OAK_ENT.get() ||
                target.getEntity().getType() == CAEntityTypes.SPRUCE_ENT.get() || target.getEntity().getType() == CAEntityTypes.WARPED_ENT.get() && !target.level.isClientSide) {
            target.hurt(DamageSource.GENERIC, (attackDamage * 2));
        }
        stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        return super.hurtEnemy(stack, target, attacker);
    }

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if(event.getLimbSwing() >= 0.0F) {
        	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.slayer_chainsaw.use_animation", false));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }



    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide) {
            // Gets the item that the player is holding, should be a JackInTheBoxItem
            final ItemStack stack = player.getItemInHand(hand);
            final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) world);
            // Tell all nearby clients to trigger this JackInTheBoxItem
            final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player);
            GeckoLibNetwork.syncAnimation(target, this, id, ANIM);
        }
        return super.use(world, player, hand);
    }

    @Override
    public void onAnimationSync(int id, int state) {
        if (state == ANIM) {
            // Always use GeckoLibUtil to get AnimationControllers when you don't have
            // access to an AnimationEvent
            final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, CONTROLLER_NAME);

            if (controller.getAnimationState() == AnimationState.Stopped) {
                final ClientPlayerEntity player = Minecraft.getInstance().player;
                // If you don't do this, the popup animation will only play once because the
                // animation will be cached.
                controller.markNeedsReload();
                // Set the animation to open the JackInTheBoxItem which will start playing music
                // and
                // eventually do the actual animation. Also sets it to not loop
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.slayer_chainsaw.use_animation", false));
            }
        }
    }
    
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "slayerchainsawcontroller", 0, this::predicate));
    }
    
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }
}
