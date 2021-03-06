package org.kilocraft.essentials.craft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.indicode.fabric.permissions.Thimble;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.GameMode;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class GamemodeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> fullLiteral = CommandManager.literal("gamemode");
        LiteralArgumentBuilder<ServerCommandSource> shortLiteral = CommandManager.literal("gm");
        LiteralArgumentBuilder<ServerCommandSource> shortCommand;

        buildFullLiteral(fullLiteral);
        buildFullLiteral(shortLiteral);
        buildShortCommand(dispatcher);

        dispatcher.register(fullLiteral);
        dispatcher.register(shortLiteral);
    }

    private static GameMode[] gameModes = GameMode.values();
    private static int var = gameModes.length;

    private static void buildFullLiteral(LiteralArgumentBuilder<ServerCommandSource> builder) {
        for (int i = 0; i < var; ++i) {
            GameMode mode = gameModes[i];
            if (!mode.equals(GameMode.NOT_SET)) {

                builder.then(CommandManager.literal(mode.getName())
                        .then(
                                CommandManager.argument("target", EntityArgumentType.players())
                                        .requires(source -> Thimble.hasPermissionChildOrOp(source, "kc.command.gamemode." + mode.getName() + ".others", 2))
                                        .executes(context -> execute(EntityArgumentType.getPlayers(context, "target"), mode, context.getSource()))
                        )
                    .requires(source -> Thimble.hasPermissionChildOrOp(source, "kc.command.gamemode." + mode.getName() + ".self", 2))
                    .executes(context -> execute(Collections.singleton(context.getSource().getPlayer()), mode, context.getSource()))
                );


            }
        }

        builder.then(CommandManager.argument("GameType", IntegerArgumentType.integer(0, 3))
                    .then(CommandManager.argument("target(s)", EntityArgumentType.players())
                            .requires(source -> Thimble.hasPermissionChildOrOp(source, "kc.command.gamemode", 2))
                            .executes(context -> executeByInteger(EntityArgumentType.getPlayers(context, "target(s)"), IntegerArgumentType.getInteger(context, "GameType"), context.getSource()))
                    )
                .requires(source -> Thimble.hasPermissionChildOrOp(source, "kc.command.gamemode", 2))
                .executes(context -> executeByInteger(Collections.singleton(context.getSource().getPlayer()), IntegerArgumentType.getInteger(context, "GameType"), context.getSource()))
        );

    }

    private static void buildShortCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        HashMap<String, GameMode> hashMap = new HashMap<String, GameMode>(){{
            put("gms", GameMode.SURVIVAL);
            put("gmc", GameMode.CREATIVE);
            put("gma", GameMode.ADVENTURE);
            put("gmsp", GameMode.SPECTATOR);
        }};

        hashMap.forEach((name, mode) -> {
            dispatcher.register(
                    CommandManager.literal(name)
                            .then(
                                    CommandManager.argument("target(s)", EntityArgumentType.players())
                                        .requires(source -> Thimble.hasPermissionChildOrOp(source, "kc.command.gamemode." + mode.getName(), 2))
                                        .executes(context -> execute(EntityArgumentType.getPlayers(context, "target(s)"), mode, context.getSource()))
                            )
                        .requires(source -> Thimble.hasPermissionChildOrOp(source, "kc.command.gamemode." + mode.getName() + ".self", 2))
                        .executes(context -> execute(Collections.singleton(context.getSource().getPlayer()), mode, context.getSource()))
            );

        });
    }

    private static int execute(Collection<ServerPlayerEntity> playerEntities, GameMode gameMode, ServerCommandSource source) {
        playerEntities.forEach((player) -> {
            player.setGameMode(gameMode);
            player.addChatMessage(new LiteralText("set gamemode to " + gameMode.getName()), false);
        });
        return 0;
    }

    private static int executeByInteger(Collection<ServerPlayerEntity> playerEntities, int int_1, ServerCommandSource source) {
        GameMode gameMode = GameMode.byId(int_1);
        execute(playerEntities, gameMode, source);
        return 0;
    }

}
