package com.gmail.anthony17j.carsplugin.Commands.subcommands;

import com.gmail.anthony17j.carsplugin.Commands.SubCommand;
import com.gmail.anthony17j.carsplugin.Movement.PacketHandler;
import org.bukkit.Location;
import org.bukkit.block.Block;
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
        PacketHandler.movement(player);
    }
}
