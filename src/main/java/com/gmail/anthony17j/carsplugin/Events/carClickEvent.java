package com.gmail.anthony17j.carsplugin.Events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class carClickEvent implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity a = event.getRightClicked();

        if (a.getCustomName() == null) {
            return;
        }

        if (!a.getCustomName().contains("CARS")) {
            return;
        }

        event.setCancelled(true);

        if (a.getCustomName().contains("CARS_SEAT")) {
            if (a.isEmpty()) {
                a.addPassenger(player);
            }
        }
    }
}
