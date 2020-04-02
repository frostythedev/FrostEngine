package me.frostythedev.hivecraft.hub;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private static final FileConfiguration config = HubPlugin.get().getConfig();

    public static Location SPAWN_LOCATION = FEPlugin.getGson().fromJson(config.getString("spawn-location"), Location.class);
}
