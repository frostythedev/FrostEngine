package me.frostythedev.developement.npc;

import com.google.inject.Singleton;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.module.ModuleHandler;
import me.frostythedev.frostengine.bukkit.module.ModuleInfo;
import org.bukkit.plugin.java.JavaPlugin;

@Singleton
@ModuleInfo(name = "NPCLib", authors = "frostythedev" , version = "1.0.0")
public class ModuleNPC extends JavaPlugin implements Module {

    private NPCManager npcManager;

    @Override
    public void onLoad() {
        ModuleHandler.offerModule(this);
    }

    @Override
    public void enable() {
        this.npcManager = new NPCManager();
    }

    @Override
    public void disable() {


        this.npcManager = new NPCManager();
    }



    public NPCManager getNpcManager() {
        return npcManager;
    }
}
