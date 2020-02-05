package org.kilocraft.essentials.listeners;

import org.kilocraft.essentials.api.KiloServer;
import org.kilocraft.essentials.api.event.EventHandler;
import org.kilocraft.essentials.api.event.player.PlayerConnectedEvent;
import org.kilocraft.essentials.api.user.OnlineUser;
import org.kilocraft.essentials.provided.BrandedServer;
import org.kilocraft.essentials.user.OnlineServerUser;

public class PlayerJoinedEvent implements EventHandler<PlayerConnectedEvent> {
    @Override
    public void handle(PlayerConnectedEvent event) {
        BrandedServer.provide(event.getPlayer());
        KiloServer.getServer().getMetaManager().onPlayerJoined(event.getPlayer());

        OnlineUser user = KiloServer.getServer().getOnlineUser(event.getPlayer());
        ((OnlineServerUser) user).onJoined();
        ((OnlineServerUser) user).updateLocation();
    }
}