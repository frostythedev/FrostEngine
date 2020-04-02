package gameapi.

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.module.Module;
import gameapi.adaptors.GameArenaAdaptor;
import gameapi.adaptors.GameKitAdaptor;
import gameapi.arenas.GameArena;
import gameapi.kits.GameKit;

public class ModuleGameAPI extends Module {

    private static ModuleGameAPI inst;

    public static ModuleGameAPI get() {
        return inst;
    }

    //GameKit testKit;

    public ModuleGameAPI() {
        super("GameAPI", "GameAPI functions and core", "1.0.0", "frostythedev");
        inst = this;

        FEPlugin.get().getAdaptors().put(GameArena.class, new GameArenaAdaptor());
        FEPlugin.get().getAdaptors().put(GameKit.class, new GameKitAdaptor());
    }
}
