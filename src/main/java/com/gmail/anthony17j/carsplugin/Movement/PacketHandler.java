package com.gmail.anthony17j.carsplugin.Movement;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

public class PacketHandler {
    public static void movement(Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
                super.channelRead(channelHandlerContext, packet);
                if (packet instanceof net.minecraft.network.protocol.game.PacketPlayInSteerVehicle) {
                    net.minecraft.network.protocol.game.PacketPlayInSteerVehicle ppisv = (net.minecraft.network.protocol.game.PacketPlayInSteerVehicle) packet;
                    VehicleMovement.vehicleMovement(player, ppisv);
                }
            }
        };
        ChannelPipeline pipeline = ((org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer) player).getHandle().b.a.k.pipeline();
        try {
            pipeline.remove(player.getName());
        } catch (NoSuchElementException ignored) {}
        try {
            pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
        } catch (NoSuchElementException ignored) {}
    }
}
