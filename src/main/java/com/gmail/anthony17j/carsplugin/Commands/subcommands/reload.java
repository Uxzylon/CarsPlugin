package com.gmail.anthony17j.carsplugin.Commands.subcommands;

import com.gmail.anthony17j.carsplugin.CarsPlugin;
import com.gmail.anthony17j.carsplugin.Commands.SubCommand;
import com.gmail.anthony17j.carsplugin.Movement.PacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class reload extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload config";
    }

    @Override
    public String getSyntax() {
        return "/cars reload";
    }

    @Override
    public String permission() {
        return "cars.command.reload";
    }

    @Override
    public boolean canRunConsole() {
        return true;
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void perform(Player player, String[] args) {
        CarsPlugin.plugin.reloadConfig();
        for (Player p : Bukkit.getOnlinePlayers()) {
            PacketHandler.movement(p);
        }
        if (player != null) {
            player.sendMessage(ChatColor.YELLOW + "Config reloaded!");
        }
        CarsPlugin.plugin.getLogger().info("Config reloaded!");
    }
}
