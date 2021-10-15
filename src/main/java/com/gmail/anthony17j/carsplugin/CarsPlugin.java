package com.gmail.anthony17j.carsplugin;

import com.gmail.anthony17j.carsplugin.Commands.carsCommand;
import com.gmail.anthony17j.carsplugin.Events.carClickEvent;
import com.gmail.anthony17j.carsplugin.Events.playerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class CarsPlugin extends JavaPlugin {

    public static CarsPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        this.getLogger().info("Enabled!");

        this.getConfig().options().copyDefaults(true);
        this.getConfig().options().header("CarsPlugin config file");
        this.getConfig().addDefault("CarsList.Test.CustomModelData", 4);
        this.getConfig().addDefault("CarsList.Test.Seats.1.X", -1);
        this.getConfig().addDefault("CarsList.Test.Seats.1.Y", -0.5);
        this.getConfig().addDefault("CarsList.Test.Seats.1.Z", 0);
        this.getConfig().addDefault("CarsList.Test.Seats.2.X", -1);
        this.getConfig().addDefault("CarsList.Test.Seats.2.Y", -0.3);
        this.getConfig().addDefault("CarsList.Test.Seats.2.Z", 1);
        this.getConfig().addDefault("CarsList.Test.Seats.3.X", -1);
        this.getConfig().addDefault("CarsList.Test.Seats.3.Y", -0.3);
        this.getConfig().addDefault("CarsList.Test.Seats.3.Z", -1);
        this.getConfig().addDefault("CarsList.Test.breakingSpeed", 0.03);
        this.getConfig().addDefault("CarsList.Test.maxSpeed", 0.7);
        this.getConfig().addDefault("CarsList.Test.maxSpeedBackwards", 0.35);
        this.getConfig().addDefault("CarsList.Test.accelerationSpeed", 0.015);
        this.getConfig().addDefault("CarsList.Test.rotateSpeed", 8);
        this.getConfig().addDefault("CarsList.Test.frictionSpeed", 0.005);
        this.saveConfig();

        getCommand("cars").setExecutor(new carsCommand());

        getServer().getPluginManager().registerEvents(new carClickEvent(), this);
        getServer().getPluginManager().registerEvents(new playerJoinEvent(), this);
    }
}