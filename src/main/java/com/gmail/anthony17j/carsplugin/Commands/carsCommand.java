package com.gmail.anthony17j.carsplugin.Commands;

import com.gmail.anthony17j.carsplugin.Commands.subcommands.*;
import com.gmail.anthony17j.carsplugin.CarsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public class carsCommand implements TabExecutor {

    private ArrayList<SubCommand> subCommands = new ArrayList<>();
    public carsCommand() {
        subCommands.add(new place());
        subCommands.add(new remove());
        subCommands.add(new reload());
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    public void help(Player player) {
        player.sendMessage(ChatColor.YELLOW + "============" + ChatColor.GREEN + " CarsPlugin " + ChatColor.YELLOW + "============");
        for (int i=0; i < getSubCommands().size(); i++) {
            player.sendMessage(getSubCommands().get(i).getSyntax() + " - " + ChatColor.GRAY + getSubCommands().get(i).getDescription());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof  Player) {
            Player player = (Player) sender;
            int y = -1;
            if (player.hasPermission("cars.command")) {
                if (args.length > 0) {
                    for (int i=0; i < getSubCommands().size(); i++) {
                        if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                            y = i;
                        }
                    }
                    if (y != -1) {
                        getSubCommands().get(y).perform(player, args);
                    } else {
                        help(player);
                    }
                } else {
                    help(player);
                }
            } else {
                player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission!");
            }
        } else {
            CarsPlugin.plugin.getLogger().info("Player Only!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            ArrayList<String> subCommandsArguments = new ArrayList<>();
            for (int i=0; i < getSubCommands().size(); i++) {
                subCommandsArguments.add(getSubCommands().get(i).getName());
            }
            return subCommandsArguments;
        } else if (args.length >= 2) {
            for (int i=0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    return getSubCommands().get(i).getSubcommandArguments((Player) sender, args);
                }
            }
        }
        return null;
    }
}
