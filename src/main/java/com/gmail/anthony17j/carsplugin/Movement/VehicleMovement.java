package com.gmail.anthony17j.carsplugin.Movement;

import com.gmail.anthony17j.carsplugin.CarsPlugin;
import com.gmail.anthony17j.carsplugin.Vehicle;
import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

public class VehicleMovement {

    private static ArmorStand getStand(Collection<Entity> near, String name) {
        for (Entity entity : near) {
            if (entity.getCustomName().equalsIgnoreCase(name)) {
                return (ArmorStand) entity;
            }
        }
        return null;
    }

    public static void vehicleMovement(Player player, PacketPlayInSteerVehicle ppisv) {
        if (player.getVehicle() == null || player.getVehicle().getCustomName() == null) {
            return;
        }
        if (!player.getVehicle().getCustomName().contains("CAR_SEAT1") || player.getVehicle().getCustomName().replace("CAR_SEAT1_", "").equals("")) {
            return;
        }

        String id = player.getVehicle().getCustomName().replace("CAR_SEAT1_", "");

        if (Vehicle.speed.get(id) == null) {
            Vehicle.speed.put(id, 0.0);
            return;
        }

        ArmorStand standSkin = Vehicle.autoStand.get("CAR_SKIN_" + id);
        ArmorStand standDriver = (ArmorStand) player.getVehicle();

        //updateSeats
        if (!(Vehicle.seatSize.get(id) == null)) {
            for (int i=1;i<=Vehicle.seatSize.get(id);i++) {
                ArmorStand seat = Vehicle.autoStand.get("CAR_SEAT" + i + "_" + id);
                double xOffset = Vehicle.seatX.get("CAR_SEAT" + i + "_" + id);
                double yOffset = Vehicle.seatY.get("CAR_SEAT" + i + "_" + id);
                double zOffset = Vehicle.seatZ.get("CAR_SEAT" + i + "_" + id);
                Location locvp = standSkin.getLocation().clone();
                Location fbvp = locvp.add(locvp.getDirection().setY(0).normalize().multiply(xOffset));
                float zvp = (float) (fbvp.getZ() + zOffset * Math.sin(Math.toRadians(fbvp.getYaw())));
                float xvp = (float) (fbvp.getX() + zOffset * Math.cos(Math.toRadians(fbvp.getYaw())));
                Location loc = new Location(standSkin.getWorld(), xvp, standSkin.getLocation().getY() + yOffset, zvp, fbvp.getYaw(), fbvp.getPitch());
                EntityArmorStand stand = ((CraftArmorStand) seat).getHandle();
                stand.setLocation(loc.getX(), loc.getY(), loc.getZ(), fbvp.getYaw(), loc.getPitch());
            }
        }

        //updateStand
        Location loc = standSkin.getLocation();
        Location location = new Location(loc.getWorld(), loc.getX(), loc.getY() - 0.2, loc.getZ(), loc.getYaw(), loc.getPitch());
        if (location.getBlock().getType().toString().contains("AIR") || location.getBlock().getType().toString().contains("WATER")) {
            standSkin.setVelocity(new Vector(standSkin.getLocation().getDirection().multiply(Vehicle.speed.get(id)).getX(), -0.8, standSkin.getLocation().getDirection().multiply(Vehicle.speed.get(id)).getZ()));
            return;
        }
        standSkin.setVelocity(new Vector(standSkin.getLocation().getDirection().multiply(Vehicle.speed.get(id)).getX(), 0.0, standSkin.getLocation().getDirection().multiply(Vehicle.speed.get(id)).getZ()));

        //TODO updateSlab

        if (ppisv.b() > 0.0) {
            ((CraftArmorStand) standSkin).getHandle().setLocation(standSkin.getLocation().getX(), standSkin.getLocation().getY(), standSkin.getLocation().getZ(), standSkin.getLocation().getYaw() - 8, standSkin.getLocation().getPitch());
            ((CraftArmorStand) standDriver).getHandle().setLocation(standDriver.getLocation().getX(), standDriver.getLocation().getY(), standDriver.getLocation().getZ(), standDriver.getLocation().getYaw() - 8, standDriver.getLocation().getPitch());
        } else if (ppisv.b() < 0.0) {
            ((CraftArmorStand) standSkin).getHandle().setLocation(standSkin.getLocation().getX(), standSkin.getLocation().getY(), standSkin.getLocation().getZ(), standSkin.getLocation().getYaw() + 8, standSkin.getLocation().getPitch());
            ((CraftArmorStand) standDriver).getHandle().setLocation(standDriver.getLocation().getX(), standDriver.getLocation().getY(), standDriver.getLocation().getZ(), standDriver.getLocation().getYaw() + 8, standDriver.getLocation().getPitch());
        }

        //avant
        if (ppisv.c() > 0.0) {
            //p.sendMessage("> 0.0");
            //standSkin.setVelocity(new Vector(standSkin.getLocation().getDirection().multiply(0.4).getX(), 0.0, standSkin.getLocation().getDirection().multiply(0.4).getZ()));
            if (Vehicle.speed.get(id) < 0) {
                Vehicle.speed.put(id, Vehicle.speed.get(id) + Vehicle.breakingSpeed.get(id));
                return;
            }
            if (Vehicle.speed.get(id) > Vehicle.maxSpeed.get(id)) {
                return;
            }
            Vehicle.speed.put(id, Vehicle.speed.get(id) + Vehicle.accelerationSpeed.get(id));
        }

        //arriere
        if (ppisv.c() < 0.0) {
            //p.sendMessage("< 0.0");
            //standSkin.setVelocity(new Vector(standSkin.getLocation().getDirection().multiply(-0.4).getX(), 0.0, standSkin.getLocation().getDirection().multiply(-0.4).getZ()));
            if (Vehicle.speed.get(id) > 0) {
                Vehicle.speed.put(id, Vehicle.speed.get(id) - Vehicle.breakingSpeed.get(id));
                return;
            }
            if (Vehicle.speed.get(id) < -Vehicle.maxSpeedBackwards.get(id)) {
                return;
            }
            Vehicle.speed.put(id, Vehicle.speed.get(id) - Vehicle.accelerationSpeed.get(id));
        }

        if (ppisv.c() == 0.0) {
            //p.sendMessage("== 0.0");
            BigDecimal round = new BigDecimal(Vehicle.speed.get(id)).setScale(1, BigDecimal.ROUND_DOWN);
            if (Double.parseDouble(String.valueOf(round)) == 0.0) {
                Vehicle.speed.put(id, 0.0);
                return;
            }
            if (Double.parseDouble(String.valueOf(round)) > 0.01) {
                Vehicle.speed.put(id, Vehicle.speed.get(id) - Vehicle.frictionSpeed.get(id));
                return;
            }
            if (Double.parseDouble(String.valueOf(round)) < 0.01) {
                Vehicle.speed.put(id, Vehicle.speed.get(id) + Vehicle.frictionSpeed.get(id));
                return;
            }
        }

        //p.sendMessage(String.valueOf(ppisv.c()));


        //standSkin.setVelocity(new Vector(standSkin.getVelocity().getX(), -0.1, standSkin.getVelocity().getZ()));


    }
}
