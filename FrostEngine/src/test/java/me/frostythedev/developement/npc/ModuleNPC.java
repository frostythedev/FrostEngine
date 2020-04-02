package me.frostythedev.developement.npc;

import me.frostythedev.frostengine.bukkit.module.Module;

public class ModuleNPC extends Module {

    public ModuleNPC(String moduleName) {
        super(moduleName);
    }

    private static ModuleNPC inst;

    public static ModuleNPC get() {
        return inst;
    }

    private NPCManager npcManager;

    @Override
    public void onModuleEnable() {
        super.onModuleEnable();

        inst = this;

        this.npcManager = new NPCManager();
    }

    public NPCManager getNpcManager() {
        return npcManager;
    }
}
