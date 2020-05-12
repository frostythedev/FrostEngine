package me.frostythedev.frostengine.modules.gameapi;

import co.aikar.commands.BukkitCommandIssuer;
import co.aikar.commands.ConditionFailedException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.module.ModuleHandler;
import me.frostythedev.frostengine.bukkit.module.ModuleInfo;
import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.modules.gameapi.arenas.adaptor.GameArenaAdaptor;
import me.frostythedev.frostengine.modules.gameapi.arenas.creator.ArenaCreatorManager;
import me.frostythedev.frostengine.modules.gameapi.cmds.MinigameCommand;
import me.frostythedev.frostengine.modules.gameapi.kits.GameKit;
import me.frostythedev.frostengine.modules.gameapi.kits.adaptor.GameKitAdaptor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Singleton
@ModuleInfo(name = "GameAPI", authors = "frostythedev", version = "1.0.0")
public class GameAPI extends JavaPlugin implements Module {

    private static GameAPI instance;
    private ArenaCreatorManager arenaCreatorManager;

    @Inject
    private MinigameManager minigameManager;

    @Inject
    private FEPlugin plugin;

    @Inject MinigameCommand minigameCommand;

    @Override
    public void onLoad() {
        ModuleHandler.offerModule(this); // always do this first!
    }


    @Override
    public void enable() {
        instance = this;

        //Experimental, might not work
        /*GameAPIWrapper wrapper = new GameAPIWrapper(this);
        this.injector = wrapper.createInjector();
        this.injector.injectMembers(this);

        if(Bukkit.getPluginManager().getPlugin("FrostEngine").isEnabled()){
            System.out.println("FROSTENGINE IS ENABLED");
            plugin = (FEPlugin) Bukkit.getPluginManager().getPlugin("FrostEngine");
        }else{
            System.out.println("FROSTENGINE IS NOT ENABLED");
        }


        if(plugin == null){
            LogUtils.warning("FEPLUGIN injection is equal to null");
            System.out.println("Injection is null");
            return;
        }else{
            System.out.println("Injection is NOT null");
        }*/

        plugin.getCommandManager().registerCommand(minigameCommand);

        this.arenaCreatorManager = new ArenaCreatorManager();
        this.minigameManager = new MinigameManager();

        plugin.getAdaptors().put(GameArena.class, new GameArenaAdaptor());
        plugin.getAdaptors().put(GameKit.class, new GameKitAdaptor());

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
    public void disable() {

    }

    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            this.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public ArenaCreatorManager getArenaCreatorManager() {
        return arenaCreatorManager;
    }

    public MinigameManager getMinigameManager() {
        return minigameManager;
    }

    public static GameAPI get(){
        return instance;
    }
}
