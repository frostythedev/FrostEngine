package me.frostythedev.frostengine.bukkit.utils.hologram.verisons;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class V1_88 implements HologramHandler {

    @Override
    public int spawn(Player player, String line, Location loc) {
        WorldServer s = ((CraftWorld)loc.getWorld()).getHandle();
        EntityArmorStand stand = new EntityArmorStand(s);

        stand.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
        stand.setCustomName(line);
        stand.setCustomNameVisible(true);
        stand.setGravity(false);
        stand.setInvisible(true);
        stand.setHealth((float) 13.7777);

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);

        return stand.getId();
    }

    @Override
    public void destroy(Player player, int id) {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(id);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }
}
