package me.frostythedev.frostengine.bukkit.utils.ux;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * Created by Devon on 10/7/16.
 */
public class HUDUtils {

    public static void sendActionBar(Player player, String message) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(new ChatComponentText(message), (byte) 2));
    }

    public static void sendTitle(Player player, int fadeIn, int stay, int fadeOut, String title, String subtitle) {
        if (player == null)
            return;

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut);
        connection.sendPacket(packetPlayOutTimes);

        if (subtitle != null) {
            PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, new ChatComponentText(subtitle));
            connection.sendPacket(packetPlayOutSubTitle);
        }

        if (title != null) {
            PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, new ChatComponentText(title));
            connection.sendPacket(packetPlayOutTitle);
        }
    }


    public static void sendTabTitle(Player player, String header, String footer) {
        if (header == null)
            header = "";
        if (footer == null)
            footer = "";

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        IChatBaseComponent tabHead = new ChatComponentText(header);
        IChatBaseComponent tabFoot = new ChatComponentText(footer);
        PacketPlayOutPlayerListHeaderFooter headFootPacket = new PacketPlayOutPlayerListHeaderFooter(tabHead);

        try {
            Field field = headFootPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headFootPacket, tabFoot);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.sendPacket(headFootPacket);
        }
    }

}