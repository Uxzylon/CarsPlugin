package com.gmail.anthony17j.carsplugin.Commands.subcommands;

import com.gmail.anthony17j.carsplugin.Commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class place extends SubCommand {

    private static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

    @Override
    public String getName() {
        return "place";
    }

    @Override
    public String getDescription() {
        return "PLACEHOLDER";
    }

    @Override
    public String getSyntax() {
        return "/cars place";
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void perform(Player player, String[] args) {
        double locationX = player.getLocation().getBlockX();
        int locationY = player.getLocation().getBlockY();
        double locationZ = player.getLocation().getBlockZ();
        int yaw;
        switch (axis[Math.round(player.getLocation().getYaw() / 90f) & 0x3].getOppositeFace()) {
            case NORTH:
                yaw = 180;
                break;
            case EAST:
                yaw = -90;
                break;
            case WEST:
                yaw = 90;
                break;
            default:
                yaw = 0;
                break;
        }

        ItemStack item = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(4);
        item.setItemMeta(meta);

        Location location = new Location(player.getWorld(),locationX,locationY,locationZ,yaw,0);
        Location location2 = new Location(player.getWorld(),locationX,locationY+1,locationZ,yaw,0);

        /*ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setCustomName("CARS_SKIN");
        stand.getEquipment().setHelmet(item);*/

        /*ArmorStand stand2 = (ArmorStand) player.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setCustomName("CARS_MAIN");*/

        ArmorStand stand3 = (ArmorStand) player.getWorld().spawnEntity(location2, EntityType.ARMOR_STAND);
        stand3.setVisible(false);
        //stand3.setGravity(false);
        stand3.setCustomName("CARS_SEAT");
        stand3.getEquipment().setHelmet(item);


        player.sendMessage(ChatColor.YELLOW + "Car Placed!");
    }
}
