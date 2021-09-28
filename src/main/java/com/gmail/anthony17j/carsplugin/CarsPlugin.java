package com.gmail.anthony17j.carsplugin;

import com.gmail.anthony17j.carsplugin.Commands.carsCommand;
import com.gmail.anthony17j.carsplugin.Events.carClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class CarsPlugin extends JavaPlugin {

    public static CarsPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        this.getLogger().info("Enabled!");

        this.getConfig().options().copyDefaults(true);
        this.getConfig().options().header("CarsPlugin config file");
        this.getConfig().addDefault("CarsList.Test.CustomModelData", 4);
        this.getConfig().addDefault("CarsList.Test.Seats.1.X", 0);
        this.getConfig().addDefault("CarsList.Test.Seats.1.Y", 1);
        this.getConfig().addDefault("CarsList.Test.Seats.1.Z", 0);
        this.getConfig().addDefault("CarsList.Test.Seats.2.X", 0);
        this.getConfig().addDefault("CarsList.Test.Seats.2.Y", 0);
        this.getConfig().addDefault("CarsList.Test.Seats.2.Z", 1);
        this.getConfig().addDefault("CarsList.Test.Seats.3.X", 1);
        this.getConfig().addDefault("CarsList.Test.Seats.3.Y", 0);
        this.getConfig().addDefault("CarsList.Test.Seats.3.Z", 0);
        this.saveConfig();

        getCommand("cars").setExecutor(new carsCommand());

        getServer().getPluginManager().registerEvents(new carClickEvent(), this);
    }
}