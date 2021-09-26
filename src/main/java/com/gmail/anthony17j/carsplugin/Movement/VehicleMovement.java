package com.gmail.anthony17j.carsplugin.Movement;

import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


public class VehicleMovement {

    public static void vehicleMovement(Player p, PacketPlayInSteerVehicle ppisv) {
        if (p.getVehicle() == null || p.getVehicle().getCustomName() == null) {
            return;
        }


        ArmorStand standSeat = (ArmorStand) p.getVehicle();


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
