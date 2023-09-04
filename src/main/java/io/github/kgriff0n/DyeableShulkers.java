package io.github.kgriff0n;

import io.github.kgriff0n.event.CleanShulker;
import io.github.kgriff0n.event.DyeShulkerBox;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DyeableShulkers implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("Dyeable Shulkers");

	@Override
	public void onInitialize() {
		UseEntityCallback.EVENT.register(new CleanShulker());
		UseBlockCallback.EVENT.register(new DyeShulkerBox());
	}


	public static void setColor(ShulkerEntity entity, int color) {
		NbtCompound nbt = new NbtCompound();
		nbt.putInt("Color", color);
		entity.readCustomDataFromNbt(nbt);
	}
}