package me.frostythedev.hivecraft.hub;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.plugin.java.JavaPlugin;

public class HubPlugin extends JavaPlugin {

    private static HubPlugin inst;

    public static final String PREFIX = Locale.toColors("&f&l<&e&lHive&f&lCraft> &r");

    @Override
    public void onEnable(){
        inst = this;
    }

    public static HubPlugin get() {
        return inst;
    }
}
