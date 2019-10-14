package org.kilocraft.essentials.api.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.kilocraft.essentials.api.KiloServer;

import java.util.concurrent.atomic.AtomicBoolean;

public class CommandHelper {
    public static boolean isConsole(ServerCommandSource source) {
        boolean bool = false;
        try {
            source.getPlayer();
        } catch (CommandSyntaxException e) {
            bool = true;
        }

        return bool;
    }

    public static boolean isOnline(ServerPlayerEntity playerEntity) {
        AtomicBoolean bool = new AtomicBoolean(false);
        try {
            KiloServer.getServer().getPlayerManager().getPlayerList().forEach((player) -> {
                if (player == playerEntity) bool.set(true);
            });
        } catch (Exception e) {
            bool.set(false);
        }

        return bool.get();
    }


}