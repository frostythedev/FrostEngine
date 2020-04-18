package me.frostythedev.frostengine.bukkit.module;

import me.frostythedev.frostengine.bukkit.FEPlugin;

public class ModuleAPI {

    public static boolean isLoaded(String name){
        return FEPlugin.get().getModuleLoader().isLoaded(name.toLowerCase());
    }

    public static Module getModule(String name){
        if(isLoaded(name)){
            return FEPlugin.get().getModuleLoader().getModule(name.toLowerCase());
        }
        return null;
    }

    public static Module enableModule(String name){
        return FEPlugin.get().getModuleLoader().enableModule(name.toLowerCase());
    }

    public static boolean disableModule(String name){
        return FEPlugin.get().getModuleLoader().disableModule(name.toLowerCase());
    }
}
