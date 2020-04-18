package me.frostythedev.frostengine.modules.gameapi;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.modules.gameapi.arenas.adaptor.GameArenaAdaptor;
import me.frostythedev.frostengine.modules.gameapi.arenas.command.ArenaCmd;
import me.frostythedev.frostengine.modules.gameapi.arenas.creator.ArenaCreatorManager;
import me.frostythedev.frostengine.modules.gameapi.cmds.CMDMinigame;
import me.frostythedev.frostengine.modules.gameapi.kits.GameKit;
import me.frostythedev.frostengine.modules.gameapi.kits.adaptor.GameKitAdaptor;

public class ModuleGameAPI extends Module {

    public ModuleGameAPI() {
        super("GameAPI", "An API framework used for making games!",
                "1.0.0", "frostythedev");
    }

    private static ModuleGameAPI instance;
    private ArenaCreatorManager arenaCreatorManager;
    private MinigameManager minigameManager;

    @Override
    public void onModuleEnable() {
        super.onModuleEnable();

        instance = this;

        this.arenaCreatorManager = new ArenaCreatorManager();
        this.minigameManager = new MinigameManager();

        FEPlugin.get().getAdaptors().put(GameArena.class, new GameArenaAdaptor());
        //System.out.println("[GAMEAPI] Added GSON for GameArena.class");
        FEPlugin.get().getAdaptors().put(GameKit.class, new GameKitAdaptor());

        this.addCommand(new ArenaCmd());
        this.addCommand(new CMDMinigame());

       //System.out.println("MODULEGAMEAPI is loaded!");
    }

    @Override
    public void onModuleDisable() {
        super.onModuleDisable();
    }

    public ArenaCreatorManager getArenaCreatorManager() {
        return arenaCreatorManager;
    }

    public MinigameManager getMinigameManager() {
        return minigameManager;
    }

    public static ModuleGameAPI get(){
        return instance;
    }
}
