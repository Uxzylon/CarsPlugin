package com.gmail.anthony17j.carsplugin;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public class Utils {
    public static void setSlotsDisabled(ArmorStand as, boolean slotsDisabled) {
        for(EquipmentSlot slot : EquipmentSlot.values()) {
            for(ArmorStand.LockType lockType : ArmorStand.LockType.values()) {
                if(slotsDisabled) {
                    as.addEquipmentLock(slot, lockType);
                } else {
                    as.removeEquipmentLock(slot, lockType);
                }
            }
        }
    }

    public static ArmorStand getClosestCar(Player player) {
        Entity closestCar = null;
        double lowestDistance = Double.MAX_VALUE;
        for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(),6d,6d,6d, (entity -> entity.getType() == EntityType.ARMOR_STAND))) {
            if (entity.getCustomName() != null) {
                if (entity.getCustomName().contains("CAR_SKIN_")) {
                    double distance = entity.getLocation().distance(player.getLocation());
                    if (distance < lowestDistance) {
                        lowestDistance = distance;
                        closestCar = entity;
                    }
                }
            }
        }

        return (ArmorStand) closestCar;
    }
}
