package com.gmail.anthony17j.carsplugin.Commands.subcommands;

import com.gmail.anthony17j.carsplugin.CarsPlugin;
import com.gmail.anthony17j.carsplugin.Commands.SubCommand;
import com.gmail.anthony17j.carsplugin.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class place extends SubCommand {

    private static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

    @Override
    public String getName() {
        return "place";
    }

    @Override
    public String getDescription() {
        return "Place a car";
    }

    @Override
    public String getSyntax() {
        return "/cars place <car>";
    }

    @Override
    public String permission() {
        return "cars.command.place";
    }

    @Override
    public boolean canRunConsole() {
        return false;
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2) {
            return new ArrayList<>(CarsPlugin.plugin.getConfig().getConfigurationSection("CarsList").getKeys(false));
        }
        return Collections.emptyList();
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length > 1) {
            if (CarsPlugin.plugin.getConfig().getConfigurationSection("CarsList") != null) {
                Set<String> CarsList = CarsPlugin.plugin.getConfig().getConfigurationSection("CarsList").getKeys(false);
                if (CarsList.contains(args[1])) {
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

                    ItemStack item2 = new ItemStack(Material.CARROT_ON_A_STICK);
                    ItemMeta meta2 = item2.getItemMeta();
                    meta2.setCustomModelData(47);
                    item2.setItemMeta(meta2);

                    String id = generateLicencePlate();

                    Location location = new Location(player.getWorld(),locationX,locationY,locationZ,yaw,0);

                    ArmorStand standSkin = (ArmorStand) player.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                    standSkin.setVisible(false);
                    standSkin.setCustomName("CAR_SKIN_" + id);
                    standSkin.getEquipment().setHelmet(item);
                    Utils.setSlotsDisabled(standSkin, true);

                    if (CarsPlugin.plugin.getConfig().getConfigurationSection("CarsList." + args[1] + ".Seats") != null) {
                        int nbrSeats = CarsPlugin.plugin.getConfig().getConfigurationSection("CarsList." + args[1] + ".Seats").getKeys(false).size();
                        for (int i = 1; i<= nbrSeats; i++) {

                            double xOffset = CarsPlugin.plugin.getConfig().getDouble("CarsList." + args[1] + ".Seats." + i + ".X");
                            double yOffset = CarsPlugin.plugin.getConfig().getDouble("CarsList." + args[1] + ".Seats." + i + ".Y");
                            double zOffset = CarsPlugin.plugin.getConfig().getDouble("CarsList." + args[1] + ".Seats." + i + ".Z");
                            Location locvp = location.clone();
                            Location fbvp = locvp.add(locvp.getDirection().setY(0).normalize().multiply(xOffset));
                            float zvp = (float) (fbvp.getZ() + zOffset * Math.sin(Math.toRadians(fbvp.getYaw())));
                            float xvp = (float) (fbvp.getX() + zOffset * Math.cos(Math.toRadians(fbvp.getYaw())));
                            Location loc = new Location(standSkin.getWorld(), xvp, location.getY() + yOffset, zvp, fbvp.getYaw(), fbvp.getPitch());

                            ArmorStand standSeatLoop = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

                            if (i == 1) {
                                standSeatLoop.getPersistentDataContainer().set(new NamespacedKey(CarsPlugin.plugin, "cars.seatSize"), PersistentDataType.INTEGER, nbrSeats);

                                for (int y=1;y<=nbrSeats;y++) {
                                    standSeatLoop.getPersistentDataContainer().set(new NamespacedKey(CarsPlugin.plugin, "cars.seat" + y + ".X"), PersistentDataType.DOUBLE,
                                            CarsPlugin.plugin.getConfig().getDouble("CarsList." + args[1] + ".Seats." + y + ".X"));
                                    standSeatLoop.getPersistentDataContainer().set(new NamespacedKey(CarsPlugin.plugin, "cars.seat" + y + ".Y"), PersistentDataType.DOUBLE,
                                            CarsPlugin.plugin.getConfig().getDouble("CarsList." + args[1] + ".Seats." + y + ".Y"));
                                    standSeatLoop.getPersistentDataContainer().set(new NamespacedKey(CarsPlugin.plugin, "cars.seat" + y + ".Z"), PersistentDataType.DOUBLE,
                                            CarsPlugin.plugin.getConfig().getDouble("CarsList." + args[1] + ".Seats." + y + ".Z"));
                                }

                                standSeatLoop.getPersistentDataContainer().set(new NamespacedKey(CarsPlugin.plugin, "cars.breakingSpeed"), PersistentDataType.DOUBLE,
                                        CarsPlugin.plugin.getConfig().getDouble("CarsList." + args[1] + ".breakingSpeed"));
                                standSeatLoop.getPersistentDataContainer().set(new NamespacedKey(CarsPlugin.plugin, "cars.maxSpeed"), PersistentDataType.DOUBLE,
                                        CarsPlugin.plugin.getConfig().getDouble("CarsList." + args[1] + ".maxSpeed"));
                                standSeatLoop.getPersistentDataContainer().set(new NamespacedKey(CarsPlugin.plugin, "cars.maxSpeedBackwards"), PersistentDataType.DOUBLE,
                                        CarsPlugin.plugin.getConfig().getDouble("CarsList." + args[1] + ".maxSpeedBackwards"));
                                standSeatLoop.getPersistentDataContainer().set(new NamespacedKey(CarsPlugin.plugin, "cars.accelerationSpeed"), PersistentDataType.DOUBLE,
                                        CarsPlugin.plugin.getConfig().getDouble("CarsList." + args[1] + ".accelerationSpeed"));
                                standSeatLoop.getPersistentDataContainer().set(new NamespacedKey(CarsPlugin.plugin, "cars.rotateSpeed"), PersistentDataType.DOUBLE,
                                        CarsPlugin.plugin.getConfig().getDouble("CarsList." + args[1] + ".rotateSpeed"));
                                standSeatLoop.getPersistentDataContainer().set(new NamespacedKey(CarsPlugin.plugin, "cars.frictionSpeed"), PersistentDataType.DOUBLE,
                                        CarsPlugin.plugin.getConfig().getDouble("CarsList." + args[1] + ".frictionSpeed"));
                            }

                            standSeatLoop.getPersistentDataContainer().set(new NamespacedKey(CarsPlugin.plugin, "cars.name"), PersistentDataType.STRING, args[1]);

                            standSeatLoop.setVisible(false);
                            standSeatLoop.setGravity(false);
                            standSeatLoop.setCustomName("CAR_SEAT" + i + "_" + id);
                            standSeatLoop.getEquipment().setHelmet(item2);
                            Utils.setSlotsDisabled(standSeatLoop, true);
                            //player.sendMessage(standSeatLoop.getCustomName());
                            //Entity villager = player.getWorld().spawnEntity(location, EntityType.VILLAGER);
                            //standSeatLoop.addPassenger(villager);
                        }
                    }
                    player.sendMessage(ChatColor.YELLOW + "Car Placed! id=" + id);
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "Vous devez donner un nom de voiture!");
        }
    }

    public static String generateLicencePlate() {
        String plate = String.format("%s-%s-%s", RandomStringUtils.random(2, true, false), RandomStringUtils.random(2, true, false), RandomStringUtils.random(2, true, false));
        return plate.toUpperCase();
    }
}
