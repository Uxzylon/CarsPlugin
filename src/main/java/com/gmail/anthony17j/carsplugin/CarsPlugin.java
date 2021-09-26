package com.gmail.anthony17j.carsplugin;

import com.gmail.anthony17j.carsplugin.Commands.carsCommand;
import com.gmail.anthony17j.carsplugin.Events.carClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class CarsPlugin extends JavaPlugin {

    public static CarsPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        this.getLogger().info("Enabled!");

        getCommand("cars").setExecutor(new carsCommand());

        getServer().getPluginManager().registerEvents(new carClickEvent(), this);
    }
}