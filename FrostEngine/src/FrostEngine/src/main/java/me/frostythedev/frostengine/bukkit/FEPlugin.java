package me.frostythedev.frostengine.bukkit;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.frostythedev.frostengine.bukkit.adaptors.ItemstackAdaptor;
import me.frostythedev.frostengine.bukkit.cmd.api.Command;
import me.frostythedev.frostengine.bukkit.cmd.types.CmdModule;
import me.frostythedev.frostengine.bukkit.debug.Debugger;
import me.frostythedev.frostengine.bukkit.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.bukkit.gameapi.arenas.adaptor.GameArenaAdaptor;
import me.frostythedev.frostengine.bukkit.gameapi.arenas.command.ArenaCmd;
import me.frostythedev.frostengine.bukkit.gameapi.cmds.CMDMinigame;
import me.frostythedev.frostengine.bukkit.gameapi.kits.GameKit;
import me.frostythedev.frostengine.bukkit.gameapi.kits.adaptor.GameKitAdaptor;
import me.frostythedev.frostengine.bukkit.utils.hologram.Hologram;
import me.frostythedev.frostengine.bukkit.utils.hologram.Holograms;
import me.frostythedev.frostengine.bukkit.utils.hologram.adaptor.HologramAdaptor;
import me.frostythedev.frostengine.bukkit.module.ModuleLoader;
import me.frostythedev.frostengine.bukkit.module.Plugin;
import me.frostythedev.frostengine.bukkit.statistics.StatisticManager;
import me.frostythedev.frostengine.bukkit.statistics.TrackableStatistic;
import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import me.frostythedev.frostengine.data.JsonAdaptor;
import me.frostythedev.frostengine.legacy.gameapi.maps.GameMap;
import me.frostythedev.frostengine.legacy.gameapi.maps.adaptor.GameMapAdaptor;
import me.frostythedev.frostengine.bukkit.adaptors.LocationAdaptor;
import me.frostythedev.frostengine.bukkit.gameapi.arenas.creator.ArenaCreatorManager;
import me.frostythedev.frostengine.bukkit.utils.hologram.HologramManager;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Map;

public class FEPlugin extends Plugin {

    private static FEPlugin inst;
    private static Gson gson;

    public String defaultModuleStorageFolder = this.getDataFolder().getAbsolutePath() + File.separator + "modules";

    private ModuleLoader moduleLoader;
    private Map<Class<?>, JsonAdaptor> adaptors;

    private HologramManager hologramManager;

    private StatisticManager statisticManager;

    private ArenaCreatorManager arenaCreatorManager;

    @Override
    public void onModuleEnable() {
        super.onModuleEnable();

        inst = this;

        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }

        Debugger.init(this);

        this.adaptors = Maps.newHashMap();

        this.hologramManager = new HologramManager();
        this.statisticManager = new StatisticManager();
        this.arenaCreatorManager = new ArenaCreatorManager();

        getAdaptors().put(ItemStack.class, new ItemstackAdaptor());
        getAdaptors().put(Location.class, new LocationAdaptor());
        getAdaptors().put(Hologram.class, new HologramAdaptor());
        getAdaptors().put(GameArena.class, new GameArenaAdaptor());
        getAdaptors().put(GameKit.class, new GameKitAdaptor());
        getAdaptors().put(GameMap.class, new GameMapAdaptor());

        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
//        builder.setPrettyPrinting();
        for (Map.Entry<Class<?>, JsonAdaptor> entry : adaptors.entrySet()) {
            builder.registerTypeAdapter(entry.getKey(), entry.getValue());
        }
        gson = builder.create();

        this.moduleLoader = new ModuleLoader(this);
        moduleLoader.loadModules();

        LogUtils.init(this);


        this.addCommand(new CmdModule());
        this.addCommand(new ArenaCmd());
        this.addCommand(new CMDMinigame());

        Holograms.registerHandler(this);

        /*this.addListener(new AbstractListener() {
            @EventHandler
            public void onChat(AsyncPlayerChatEvent event) {
                if (event.getMessage().startsWith("zombonpc")) {
                    Tasks.runOneTickLater(() -> {
                        EntityTypes.spawnEntity(new CustomZombie( event.getPlayer().getWorld()), event.getPlayer().getLocation());
                    });
                }
            }
        });*/

        /*this.addListener(new AbstractListener() {
            @EventHandler
            public void onBlock(BlockBreakEvent event){
                if(event.getBlock().getType().equals(Material.SIGN_POST)
                        || event.getBlock().getType().equals(Material.WALL_SIGN)){

                    Sign sign = (Sign) event.getBlock().getState();
                }
            }
        });*/
    }

    public TrackableStatistic getStatistic(String id){
        return this.statisticManager.getById(id);
    }

   private void registerCommands(Command... commands) {
       for(Command cmd : commands){
           this.getServer().getPluginCommand(cmd.getName()).setExecutor(cmd);
       }
    }

    public static FEPlugin get() {
        return inst;
    }

    public static Gson getGson() {
        return gson;
    }

    public ModuleLoader getModuleLoader() {
        return moduleLoader;
    }

    public Map<Class<?>, JsonAdaptor> getAdaptors() {
        return adaptors;
    }

    public HologramManager getHologramManager() {
        return hologramManager;
    }

    public ArenaCreatorManager getArenaCreatorManager() {
        return arenaCreatorManager;
    }

    /*public NPCManager getNpcManager() {
        return npcManager;
    }*/

    public StatisticManager getStatisticManager() {
        return statisticManager;
    }
}
