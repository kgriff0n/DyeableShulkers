package io.github.kgriff0n.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class DyeShulkerBox implements UseBlockCallback {

    private static final Map<Integer, BlockState> SHULKER_MAP = Util.make(new HashMap<>(), map -> {
        map.put(0, Blocks.WHITE_SHULKER_BOX.getDefaultState());
        map.put(1, Blocks.ORANGE_SHULKER_BOX.getDefaultState());
        map.put(2, Blocks.MAGENTA_SHULKER_BOX.getDefaultState());
        map.put(3, Blocks.LIGHT_BLUE_SHULKER_BOX.getDefaultState());
        map.put(4, Blocks.YELLOW_SHULKER_BOX.getDefaultState());
        map.put(5, Blocks.LIME_SHULKER_BOX.getDefaultState());
        map.put(6, Blocks.PINK_SHULKER_BOX.getDefaultState());
        map.put(7, Blocks.GRAY_SHULKER_BOX.getDefaultState());
        map.put(8, Blocks.LIGHT_GRAY_SHULKER_BOX.getDefaultState());
        map.put(9, Blocks.CYAN_SHULKER_BOX.getDefaultState());
        map.put(10, Blocks.PURPLE_SHULKER_BOX.getDefaultState());
        map.put(11, Blocks.BLUE_SHULKER_BOX.getDefaultState());
        map.put(12, Blocks.BROWN_SHULKER_BOX.getDefaultState());
        map.put(13, Blocks.GREEN_SHULKER_BOX.getDefaultState());
        map.put(14, Blocks.RED_SHULKER_BOX.getDefaultState());
        map.put(15, Blocks.BLACK_SHULKER_BOX.getDefaultState());
        map.put(16, Blocks.SHULKER_BOX.getDefaultState());
    });

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos();
        Block block = world.getBlockState(pos).getBlock();
        ItemStack itemStack = player.getMainHandStack();
        Item item = itemStack.getItem();
        if (block instanceof ShulkerBoxBlock && hand == Hand.MAIN_HAND) {
            if (item instanceof DyeItem && SHULKER_MAP.get(((DyeItem) item).getColor().getId()) != block.getDefaultState()) {
                ShulkerBoxBlockEntity oldShulkerBox = (ShulkerBoxBlockEntity) world.getBlockEntity(pos);
                NbtCompound nbt = oldShulkerBox.createNbt();
                DyeColor color = ((DyeItem) item).getColor();

                world.setBlockState(pos, SHULKER_MAP.get(color.getId()));
                ShulkerBoxBlockEntity newShulkerBox = (ShulkerBoxBlockEntity) world.getBlockEntity(pos);
                newShulkerBox.readNbt(nbt);

                if (!player.isCreative()) {
                    player.getMainHandStack().decrement(1);
                }

                return ActionResult.success(world.isClient);

            } else if (item == Items.WATER_BUCKET && block.getDefaultState() != Blocks.SHULKER_BOX.getDefaultState()) {
                ShulkerBoxBlockEntity oldShulkerBox = (ShulkerBoxBlockEntity) world.getBlockEntity(pos);
                NbtCompound nbt = oldShulkerBox.createNbt();

                if (!player.isCreative()) {
                    player.getInventory().setStack(player.getInventory().selectedSlot, Items.BUCKET.getDefaultStack());
                }

                world.setBlockState(pos, SHULKER_MAP.get(16));
                ShulkerBoxBlockEntity newShulkerBox = (ShulkerBoxBlockEntity) world.getBlockEntity(pos);
                newShulkerBox.readNbt(nbt);

                return ActionResult.success(world.isClient);
            } else if (item == Items.NAME_TAG) {
                if (itemStack.hasCustomName()) {
                    ShulkerBoxBlockEntity shulkerBox = (ShulkerBoxBlockEntity) world.getBlockEntity(pos);
                    shulkerBox.setCustomName(itemStack.getName());
                    if (!player.isCreative()) {
                        player.getMainHandStack().decrement(1);
                    }
                    return ActionResult.success(world.isClient);
                }
            }
        }
        return ActionResult.PASS;
    }
}
