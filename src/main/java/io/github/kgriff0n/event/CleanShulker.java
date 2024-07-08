package io.github.kgriff0n.event;

import io.github.kgriff0n.Config;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.kgriff0n.DyeableShulkers.setColor;

public class CleanShulker implements UseEntityCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        ShulkerEntity shulkerEntity;
        if (Config.canDyeMob && entity instanceof ShulkerEntity && (shulkerEntity = (ShulkerEntity) entity).isAlive() && shulkerEntity.getColor() != null && hand == Hand.MAIN_HAND && player.getMainHandStack().getItem() == Items.WATER_BUCKET) {
            if (!player.isCreative()) {
                player.getMainHandStack().decrement(1);
                player.getInventory().setStack(player.getInventory().selectedSlot, Items.BUCKET.getDefaultStack());
            }
            setColor(shulkerEntity, 16);

            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }
}
