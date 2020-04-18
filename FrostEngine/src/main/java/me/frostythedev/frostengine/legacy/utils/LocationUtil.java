package me.frostythedev.frostengine.legacy.utils;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil
{
    public static String toString(Location l)
    {
        String s = "";
        s = s + "@W:" + l.getWorld().getName();
        s = s + ";@X:" + l.getBlockX();
        s = s + ";@Y:" + l.getBlockY();
        s = s + ";@Z:" + l.getBlockZ();
        s = s + ";@P:" + l.getPitch();
        s = s + ";@YA:" + l.getYaw();

        return s;
    }

    public static Location fromString(String s)
    {
        Location l = new Location((World)Bukkit.getWorlds().get(0), 0.0D, 0.0D, 0.0D);
        String[] att = s.split(";");
        for (String attribute : att)
        {
            String[] split = attribute.split(":");
            if (split[0].equalsIgnoreCase("@W")) {
                l.setWorld(Bukkit.getWorld(split[1]));
            }
            if (split[0].equalsIgnoreCase("@X")) {
                l.setX(Double.parseDouble(split[1]));
            }
            if (split[0].equalsIgnoreCase("@Y")) {
                l.setY(Double.parseDouble(split[1]));
            }
            if (split[0].equalsIgnoreCase("@Z")) {
                l.setZ(Double.parseDouble(split[1]));
            }
            if (split[0].equalsIgnoreCase("@P")) {
                l.setPitch(Float.parseFloat(split[1]));
            }
            if (split[0].equalsIgnoreCase("@YA")) {
                l.setYaw(Float.parseFloat(split[1]));
            }
        }
        return l;
    }

    public static Location getCenter(Location loc)
    {
        return new Location(loc.getWorld(), getRelativeCords(loc.getBlockX()), getRelativeCords(loc.getBlockY()), getRelativeCords(loc.getBlockZ()));
    }

    private static double getRelativeCords(int i)
    {
        double d = i;
        d = d < 0.0D ? d - 0.5D : d + 0.5D;
        return d;
    }
}
