package me.frostythedev.frostengine.modules.gameapi;

import co.aikar.commands.BukkitCommandIssuer;
import co.aikar.commands.ConditionFailedException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.modules.gameapi.arenas.adaptor.GameArenaAdaptor;
import me.frostythedev.frostengine.modules.gameapi.arenas.command.ArenaCmd;
import me.frostythedev.frostengine.modules.gameapi.arenas.creator.ArenaCreatorManager;
import me.frostythedev.frostengine.modules.gameapi.cmds.CMDMinigame;
import me.frostythedev.frostengine.modules.gameapi.cmds.MinigameCommand;
import me.frostythedev.frostengine.modules.gameapi.kits.GameKit;
import me.frostythedev.frostengine.modules.gameapi.kits.adaptor.GameKitAdaptor;
import org.bukkit.entity.Player;

@Singleton
public class ModuleGameAPI extends Module {

    public ModuleGameAPI() {
        super("GameAPI", "An API framework used for making games!",
                "1.0.0", "frostythedev");
    }

    private static ModuleGameAPI instance;
    private ArenaCreatorManager arenaCreatorManager;
    private MinigameManager minigameManager;

    @Inject
    private FEPlugin plugin;

    @Inject
    MinigameCommand minigameCommand;

    @Override
    public void onModuleEnable() {
        super.onModuleEnable();

        instance = this;

        //Experimental, might not work
        plugin.getInjector().injectMembers(this);
        plugin.getCommandManager().registerCommand(minigameCommand);

        this.arenaCreatorManager = new ArenaCreatorManager();
        this.minigameManager = new MinigameManager();

        FEPlugin.get().getAdaptors().put(GameArena.class, new GameArenaAdaptor());
        FEPlugin.get().getAdaptors().put(GameKit.class, new GameKitAdaptor());

        //this.addCommand(new ArenaCmd());
        //this.addCommand(new CMDMinigame());

        plugin.getCommandManager().getCommandConditions().addCondition("creator", (context) -> {
            BukkitCommandIssuer issuer = context.getIssuer();
            if (!issuer.isPlayer()) {
                throw new ConditionFailedException("Target must be a player");
            }
            if(!getArenaCreatorManager().isCreator((Player) issuer)) {
                throw new ConditionFailedException("Player must be creator mode");
            }
        });

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
