package com.gmail.anthony17j.carsplugin.Events;

import com.gmail.anthony17j.carsplugin.CarsPlugin;
import com.gmail.anthony17j.carsplugin.Vehicle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.Collection;

public class carClickEvent implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity a = event.getRightClicked();

        if (a.getCustomName() == null) {
            return;
        }

        if (!a.getCustomName().contains("CAR")) {
            return;
        }

        event.setCancelled(true);

        if (a.getCustomName().contains("CAR_SEAT")) {
            if (a.isEmpty()) {

                String id = a.getCustomName().split("_")[2];

                Vehicle.seatSize.put(id, CarsPlugin.plugin.getConfig().getConfigurationSection("CarsList.Test.Seats").getKeys(false).size());
                Collection<Entity> near = player.getWorld().getNearbyEntities(player.getLocation(),3d,3d,3d);
                for (Entity entity : near) {
                    if (entity.getCustomName() != null) {
                        if (entity.getCustomName().equalsIgnoreCase("CAR_SKIN_" + id)) {
                            Vehicle.autoStand.put(entity.getCustomName(), (ArmorStand) entity);
                        } else if (entity.getCustomName().contains("CAR_SEAT")) {
                            for (int i=1;i<=Vehicle.seatSize.get(id);i++) {
                                if (entity.getCustomName().equalsIgnoreCase("CAR_SEAT" + i + "_" + id)) {
                                    Vehicle.autoStand.put("CAR_SEAT" + i + "_" + id, (ArmorStand) entity);
                                    Vehicle.seatX.put("CAR_SEAT" + i + "_" + id, CarsPlugin.plugin.getConfig().getDouble("CarsList.Test.Seats." + i + ".X"));
                                    Vehicle.seatY.put("CAR_SEAT" + i + "_" + id, CarsPlugin.plugin.getConfig().getDouble("CarsList.Test.Seats." + i + ".Y"));
                                    Vehicle.seatZ.put("CAR_SEAT" + i + "_" + id, CarsPlugin.plugin.getConfig().getDouble("CarsList.Test.Seats." + i + ".Z"));
                                }
                            }
                        }
                    }
                }

                a.addPassenger(player);
            }
        }
    }
}
