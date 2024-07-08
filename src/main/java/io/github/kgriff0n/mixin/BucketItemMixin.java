package io.github.kgriff0n.mixin;

import io.github.kgriff0n.Config;
import io.github.kgriff0n.DyeableShulkers;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public class BucketItemMixin {
    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (Config.canDyeMob && itemStack.getItem() == Items.WATER_BUCKET) {
            Vec3d pos = user.raycast(4, 0, false).getPos();
            for (ShulkerEntity shulker : world.getEntitiesByClass(ShulkerEntity.class, user.getBoundingBox().expand(6), EntityPredicates.VALID_ENTITY)) {
                DyeableShulkers.LOGGER.info("pos:"+pos.toString());
                DyeableShulkers.LOGGER.info("shulker-pos:"+(shulker.getBlockPos().getSquaredDistance(pos) < 1));
                if (shulker.getBlockPos().getSquaredDistance(pos) < 1) {
                    DyeableShulkers.LOGGER.info("shulker:"+shulker.getPos().toString());
                    cir.cancel();
                    cir.setReturnValue(TypedActionResult.success(itemStack));
                }
            }
        }
    }
}