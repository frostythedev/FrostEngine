package me.frostythedev.frostengine.bukkit.utils.hologram;

import me.frostythedev.frostengine.bukkit.utils.hologram.verisons.V1_88;
import me.frostythedev.frostengine.bukkit.utils.hologram.verisons.HologramHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Holograms {

    private static HologramHandler handler;

    public static void registerHandler(JavaPlugin plugin){
      /*  if(plugin.getDescription().getVersion().contains("1.8")){
            handler = new V1_88();
            Debugger.info("Holograms v1.8.3 has been enabled.");
        }*/
        handler = new V1_88();
    }

    public static HologramHandler getHandler() {
        return handler;
    }
}
