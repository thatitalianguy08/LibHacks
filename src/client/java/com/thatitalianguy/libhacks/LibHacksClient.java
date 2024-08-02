package com.thatitalianguy.libhacks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.impl.lib.tinyremapper.extension.mixin.common.Logger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

public class LibHacksClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.


		CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("libFly").executes(context -> {
				context.getSource().sendFeedback(() -> Text.literal("Called libFly"), true);
				//MinecraftClient.getInstance().player.getAbilities().setWalkSpeed(1000F);
				MinecraftClient.getInstance().player.getAbilities().allowFlying = true;
				MinecraftClient.getInstance().player.getAbilities().flying = true;

				return 1;
			}));
		}));
	}
}