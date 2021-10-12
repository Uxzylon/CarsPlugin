package com.gmail.anthony17j.carsplugin.Events;

import com.gmail.anthony17j.carsplugin.Movement.PacketHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerJoinEvent implements Listener {
    @EventHandler
    public void onEvent (PlayerJoinEvent event) {
        PacketHandler.movement(event.getPlayer());
    }
}
