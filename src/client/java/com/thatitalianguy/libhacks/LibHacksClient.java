package com.thatitalianguy.libhacks;

import com.mojang.brigadier.arguments.IntegerArgumentType;
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
import net.minecraft.world.GameMode;

public class LibHacksClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.


		ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("libFly").executes(context -> {

				//MinecraftClient.getInstance().player.getAbilities().setWalkSpeed(1000F);
				MinecraftClient.getInstance().player.getAbilities().allowFlying = true;
				MinecraftClient.getInstance().player.getAbilities().flying = true;

				return 1;
			}));
			dispatcher.register(ClientCommandManager.literal("libSpeed")
					.then(ClientCommandManager.argument("speed", IntegerArgumentType.integer())
							.executes(context -> {
								int speedVal = IntegerArgumentType.getInteger(context, "speed");
								float speedValFloat = (float) speedVal;
								//MinecraftClient.getInstance().player.getAbilities().setWalkSpeed(speedValFloat);
								MinecraftClient.getInstance().player.setMovementSpeed(speedValFloat);

								return 1;
							})));

			dispatcher.register(ClientCommandManager.literal("libSetInvincible").executes(context -> {
				PlayerEntity plr = MinecraftClient.getInstance().player;
				plr.setInvulnerable(true);
                if(plr.getAbilities().invulnerable) {
					//plr.getAbilities().invulnerable = false;
					plr.setInvulnerable(false);
				} else if (!plr.getAbilities().invulnerable) {
					//plr.getAbilities().invulnerable = true;
					plr.setInvulnerable(true);
				}

				return 1;
			}));
		}));
	}
}