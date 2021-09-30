package com.gmail.anthony17j.carsplugin.Events;

import com.gmail.anthony17j.carsplugin.CarsPlugin;
import com.gmail.anthony17j.carsplugin.Vehicle;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.persistence.PersistentDataType;

public class carClickEvent implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity a = event.getRightClicked();

        if (a.getCustomName() == null) {
            return;
        }

        if (!a.getCustomName().contains("CAR_SEAT")) {
            return;
        }

        event.setCancelled(true);

        if (a.isEmpty()) {
            if (a.getCustomName().contains("CAR_SEAT1")) {
                String id = a.getCustomName().split("_")[2];

                Vehicle.seatSize.putIfAbsent(id, a.getPersistentDataContainer().get(new NamespacedKey(CarsPlugin.plugin, "cars.seatSize"), PersistentDataType.INTEGER));
                Vehicle.breakingSpeed.putIfAbsent(id, a.getPersistentDataContainer().get(new NamespacedKey(CarsPlugin.plugin, "cars.breakingSpeed"), PersistentDataType.DOUBLE));
                Vehicle.maxSpeed.putIfAbsent(id, a.getPersistentDataContainer().get(new NamespacedKey(CarsPlugin.plugin, "cars.maxSpeed"), PersistentDataType.DOUBLE));
                Vehicle.maxSpeedBackwards.putIfAbsent(id, a.getPersistentDataContainer().get(new NamespacedKey(CarsPlugin.plugin, "cars.maxSpeedBackwards"), PersistentDataType.DOUBLE));
                Vehicle.accelerationSpeed.putIfAbsent(id, a.getPersistentDataContainer().get(new NamespacedKey(CarsPlugin.plugin, "cars.accelerationSpeed"), PersistentDataType.DOUBLE));
                Vehicle.rotateSpeed.putIfAbsent(id, a.getPersistentDataContainer().get(new NamespacedKey(CarsPlugin.plugin, "cars.rotateSpeed"), PersistentDataType.DOUBLE));
                Vehicle.frictionSpeed.putIfAbsent(id, a.getPersistentDataContainer().get(new NamespacedKey(CarsPlugin.plugin, "cars.frictionSpeed"), PersistentDataType.DOUBLE));

                for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(),10d,10d,10d, (entity -> entity.getType() == EntityType.ARMOR_STAND))) {
                    if (entity.getCustomName() != null) {
                        if (entity.getCustomName().equalsIgnoreCase("CAR_SKIN_" + id)) {
                            Vehicle.autoStand.putIfAbsent(entity.getCustomName(), (ArmorStand) entity);
                        } else if (entity.getCustomName().contains("CAR_SEAT")) {
                            for (int i=1;i<=Vehicle.seatSize.get(id);i++) {
                                if (entity.getCustomName().equalsIgnoreCase("CAR_SEAT" + i + "_" + id)) {
                                    Vehicle.autoStand.putIfAbsent(entity.getCustomName(), (ArmorStand) entity);

                                    Vehicle.seatX.putIfAbsent(entity.getCustomName(), a.getPersistentDataContainer().get(
                                            new NamespacedKey(CarsPlugin.plugin, "cars.seat" + i + ".X"), PersistentDataType.DOUBLE));
                                    Vehicle.seatY.putIfAbsent(entity.getCustomName(), a.getPersistentDataContainer().get(
                                            new NamespacedKey(CarsPlugin.plugin, "cars.seat" + i + ".Y"), PersistentDataType.DOUBLE));
                                    Vehicle.seatZ.putIfAbsent(entity.getCustomName(), a.getPersistentDataContainer().get(
                                            new NamespacedKey(CarsPlugin.plugin, "cars.seat" + i + ".Z"), PersistentDataType.DOUBLE));
                                }
                            }
                        }
                    }
                }

                player.sendMessage("=======================================");
                player.sendMessage(ChatColor.GREEN + Vehicle.autoStand.toString());
                player.sendMessage(ChatColor.YELLOW + Vehicle.seatSize.toString());
                player.sendMessage(ChatColor.GREEN + Vehicle.seatX.toString());
                player.sendMessage(ChatColor.YELLOW + Vehicle.seatY.toString());
                player.sendMessage(ChatColor.GREEN + Vehicle.seatZ.toString());
            }
            a.addPassenger(player);
        }
    }
}
