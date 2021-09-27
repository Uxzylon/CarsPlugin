package com.gmail.anthony17j.carsplugin.Movement;

import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;

public class VehicleMovement {

    private double speed = 0;

    private static ArmorStand getStand(Collection<Entity> near, String name) {
        for (Entity entity : near) {
            if (entity.getCustomName().equalsIgnoreCase(name)) {
                return (ArmorStand) entity;
            }
        }
        return null;
    }

    public static void vehicleMovement(Player player, PacketPlayInSteerVehicle ppisv) {
        if (player.getVehicle() == null || player.getVehicle().getCustomName() == null || !player.getVehicle().getCustomName()
                .contains("CAR_SEAT_DRIVER")) {
            return;
        }

        Collection<Entity> near = player.getWorld().getNearbyEntities(player.getLocation(),3d,3d,3d);

        ArmorStand standSeat = (ArmorStand) player.getVehicle();
        ArmorStand standSkin = null;
        for (String tag : standSeat.getScoreboardTags()) {
            //getStand(near,tag);
            if (tag.contains("CAR_SKIN")) {
                standSkin = getStand(near,tag);
            }
        }

        ((CraftArmorStand) standSeat).getHandle().setLocation(standSkin.getLocation().getX(), standSkin.getLocation().getY(), standSkin.getLocation().getZ(), standSkin.getLocation().getYaw(), standSkin.getLocation().getPitch());


        if (ppisv.b() > 0.0) {
            ((CraftArmorStand) standSeat).getHandle().setLocation(standSeat.getLocation().getX(), standSeat.getLocation().getY(), standSeat.getLocation().getZ(), standSeat.getLocation().getYaw() - 8, standSeat.getLocation().getPitch());
        } else if (ppisv.b() < 0.0) {
            ((CraftArmorStand) standSeat).getHandle().setLocation(standSeat.getLocation().getX(), standSeat.getLocation().getY(), standSeat.getLocation().getZ(), standSeat.getLocation().getYaw() + 8, standSeat.getLocation().getPitch());
        }

        //avant
        if (ppisv.c() > 0.0) {
            //p.sendMessage("> 0.0");
            standSeat.setVelocity(new Vector(standSeat.getLocation().getDirection().multiply(0.4).getX(), 0.0, standSeat.getLocation().getDirection().multiply(0.4).getZ()));
        }

        //arriere
        if (ppisv.c() < 0.0) {
            //p.sendMessage("< 0.0");
            standSeat.setVelocity(new Vector(standSeat.getLocation().getDirection().multiply(-0.4).getX(), 0.0, standSeat.getLocation().getDirection().multiply(-0.4).getZ()));
        }

        if (ppisv.c() == 0.0) {
            //p.sendMessage("== 0.0");
        }

        //p.sendMessage(String.valueOf(ppisv.c()));


        standSeat.setVelocity(new Vector(standSeat.getVelocity().getX(), -0.1, standSeat.getVelocity().getZ()));


    }
}
