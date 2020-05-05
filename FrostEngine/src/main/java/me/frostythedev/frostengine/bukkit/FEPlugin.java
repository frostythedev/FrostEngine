package me.frostythedev.frostengine.bukkit;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.BukkitCommandManager;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.frostythedev.frostengine.bukkit.adaptors.ItemstackAdaptor;
import me.frostythedev.frostengine.bukkit.adaptors.LocationAdaptor;
import me.frostythedev.frostengine.legacy.cmds.api.Command;
import me.frostythedev.frostengine.bukkit.commands.ModuleCommand;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.module.ModuleLoader;
import me.frostythedev.frostengine.bukkit.module.Plugin;
import me.frostythedev.frostengine.bukkit.statistics.StatisticManager;
import me.frostythedev.frostengine.bukkit.statistics.TrackableStatistic;
import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import me.frostythedev.frostengine.bukkit.utils.hologram.Hologram;
import me.frostythedev.frostengine.bukkit.utils.hologram.HologramManager;
import me.frostythedev.frostengine.bukkit.utils.hologram.Holograms;
import me.frostythedev.frostengine.bukkit.utils.hologram.adaptor.HologramAdaptor;
import me.frostythedev.frostengine.data.JsonAdaptor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Map;

@Singleton
public class FEPlugin extends Plugin {

    private static FEPlugin inst;
    private static Gson gson;

//    public String defaultModuleStorageFolder = this.getDataFolder().getAbsolutePath() + File.separator + "modules";

//    private ModuleLoader moduleLoader;
    private Map<Class<?>, JsonAdaptor> adaptors;

    private HologramManager hologramManager;

    private StatisticManager statisticManager;

    private BukkitCommandManager commandManager;
    private Injector injector;

    @Inject ModuleCommand moduleCommand;

    @Override
    public void onModuleEnable() {
        super.onModuleEnable();

        inst = this;
        LogUtils.init(this);

        this.commandManager = new BukkitCommandManager(this);

        FEBinderModule sbm = new FEBinderModule(this, commandManager);
        this.injector = sbm.createInjector();
        this.injector.injectMembers(this);

        this.getCommandManager().registerCommand(moduleCommand);

        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }

        this.adaptors = Maps.newHashMap();

        this.hologramManager = new HologramManager();
        this.statisticManager = new StatisticManager();

        getAdaptors().put(ItemStack.class, new ItemstackAdaptor());
        getAdaptors().put(Location.class, new LocationAdaptor());
        getAdaptors().put(Hologram.class, new HologramAdaptor());

        setModuleLoader(new ModuleLoader(this));
        getModuleLoader().loadModules();

        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        for (Map.Entry<Class<?>, JsonAdaptor> entry : adaptors.entrySet()) {
            builder.registerTypeAdapter(entry.getKey(), entry.getValue());
        }
        gson = builder.create();

        //this.addCommand(new CmdModule());

        Holograms.registerHandler(this);

        if(getModuleLoader().isLoaded("GameAPI")){
            System.out.println("[FE] GameAPI was found!");
            Module gameAPI = getModuleLoader().getModule("GameAPI");
            if(gameAPI != null){
                System.out.println("[FE] GameAPI Module was found!");

                for(Module child : gameAPI.getChildModules()){
                    child.onCompleteLoad();
                }
            }else{
                System.out.println("[FE] GameAPI Module was NOT found!");
            }
        }else{
            System.out.println("[FE] GameAPI was NOT found!");
        }
    }

   private void registerCommands(BaseCommand... commands) {
       for(BaseCommand cmd : commands){
           this.getCommandManager().registerCommand(cmd);
       }
    }

    public static FEPlugin get() {
        return inst;
    }

    public static Gson getGson() {
        return gson;
    }

//    public ModuleLoader getModuleLoader() {
//        return moduleLoader;
//    }

    public BukkitCommandManager getCommandManager() {
        return commandManager;
    }

    public Map<Class<?>, JsonAdaptor> getAdaptors() {
        return adaptors;
    }

    public TrackableStatistic getStatistic(String id){
        return this.statisticManager.getById(id);
    }

    public HologramManager getHologramManager() {
        return hologramManager;
    }

    public StatisticManager getStatisticManager() {
        return statisticManager;
    }

    public Injector getInjector() {
        return injector;
    }

    /*public NPCManager getNpcManager() {
        return npcManager;
    }*/

}
