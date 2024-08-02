package com.thatitalianguy.libhacks;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.impl.lib.tinyremapper.extension.mixin.common.Logger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.apache.logging.log4j.core.jmx.Server;

public class LibHacksClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		//Client commands
		ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("libFly").executes(context -> {

				//MinecraftClient.getInstance().player.getAbilities().setWalkSpeed(1000F);
				MinecraftClient.getInstance().player.getAbilities().allowFlying = true;
				MinecraftClient.getInstance().player.getAbilities().flying = true;
				context.getSource().getPlayer().setMovementSpeed(100f);

				return 1;
			}));

		}));
		//Server side commands
		CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("libSpeed")
					.then(CommandManager.argument("speedVal", IntegerArgumentType.integer())
							.executes(context -> {
								int speedValueNonFloat = IntegerArgumentType.getInteger(context, "speedVal");
								float speedValueFloat = (float) speedValueNonFloat;
								System.out.println("Speed value has been floatified --> " + speedValueFloat);
								ServerPlayerEntity plr = context.getSource().getPlayerOrThrow();
								plr.setMovementSpeed(2f);
								System.out.println("Player speed changed to --> " );
								return 1;
							})));


//			dispatcher.register(CommandManager.literal("commandTest")
//					.then(CommandManager.argument("outputnum", IntegerArgumentType.integer())
//							.executes(context -> {
//								int commandArg = IntegerArgumentType.getInteger(context, "outputnum");
//								context.getSource().sendMessage(Text.of("Your inputed num is --> " + commandArg));
//								ServerPlayerEntity plr = context.getSource().getPlayerOrThrow();;
//								plr.addExperienceLevels(50);
//
//								return 1;
//							})));

			dispatcher.register(CommandManager.literal("libAddXP")
					.then(CommandManager.argument("xp amount", IntegerArgumentType.integer())
							.executes(context -> {
								ServerPlayerEntity plr = context.getSource().getPlayerOrThrow();
								int levelsToAdd = IntegerArgumentType.getInteger(context, "xp amount");
								plr.addExperienceLevels(levelsToAdd);
								return 1;
							})));

			dispatcher.register(CommandManager.literal("libSurvival").executes(context -> {
				ServerPlayerEntity plr = context.getSource().getPlayerOrThrow();;
				plr.changeGameMode(GameMode.SURVIVAL);
				context.getSource().sendMessage(Text.of("Gamemode --> Survival"));

				return 1;
			}));

			dispatcher.register(CommandManager.literal("libCreative").executes(context -> {
				ServerPlayerEntity plr = context.getSource().getPlayerOrThrow();
				plr.changeGameMode(GameMode.CREATIVE);
				context.getSource().sendMessage(Text.of("Gamemode --> Creative"));
				return 1;
			}));

			dispatcher.register(CommandManager.literal("libHeal").executes(context -> {
				ServerPlayerEntity plr = context.getSource().getPlayerOrThrow();
				plr.heal(20);
				return 1;
			}));
		}));

	}
}