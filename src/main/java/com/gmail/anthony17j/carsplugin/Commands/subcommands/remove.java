package com.gmail.anthony17j.carsplugin.Commands.subcommands;

import com.gmail.anthony17j.carsplugin.Commands.SubCommand;
import com.gmail.anthony17j.carsplugin.Movement.PacketHandler;
import com.gmail.anthony17j.carsplugin.Utils;
import com.gmail.anthony17j.carsplugin.Vehicle;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class remove extends SubCommand {

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getDescription() {
        return "PLACEHOLDER";
    }

    @Override
    public String getSyntax() {
        return "/cars remove";
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void perform(Player player, String[] args) {
        ArmorStand car = Utils.getClosestCar(player);
        if (car != null) {
            String id = car.getCustomName().split("_")[2];
            for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(),10d,10d,10d, (entity -> entity.getCustomName().contains(id)))) {
                entity.remove();
                Vehicle.autoStand.remove(entity.getCustomName());
                Vehicle.seatX.remove(entity.getCustomName());
                Vehicle.seatY.remove(entity.getCustomName());
                Vehicle.seatZ.remove(entity.getCustomName());
            }
            Vehicle.seatSize.remove(id);
            Vehicle.speed.remove(id);
            Vehicle.breakingSpeed.remove(id);
            Vehicle.maxSpeed.remove(id);
            Vehicle.maxSpeedBackwards.remove(id);
            Vehicle.accelerationSpeed.remove(id);
            Vehicle.rotateSpeed.remove(id);
            Vehicle.frictionSpeed.remove(id);
        }
    }
}
