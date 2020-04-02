package me.frostythedev.modules;

import me.frostythedev.frostengine.bukkit.debug.Debugger;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.module.ModuleAPI;
import me.frostythedev.frostengine.modules.achievements.ModuleAchievement;

public class ModuleFriends extends Module {

    private static ModuleFriends inst;

    public static ModuleFriends get() {
        return inst;
    }

    public ModuleFriends() {
        super("Friends", "Friends addon", "1.0.0", "frostythedev");
    }

    private ModuleAchievement moduleAchievement;

    @Override
    public void onModuleEnable() {
        super.onModuleEnable();

        inst = this;
       if(!ModuleAPI.isLoaded("Achievements") || !ModuleAPI.isLoaded("Statistics")){
           Debugger.debug("Must have Achievements and Statistics module loaded for Friends module to work properly.");
           ModuleAPI.disableModule(getModuleName());
       }else{
         moduleAchievement = ModuleAchievement.get();
           if(moduleAchievement.getManager().isAchievement("Socially Active")){
               new FriendsAchievement().save();
               moduleAchievement.getManager().loadAchievement("Socially Active", new FriendsAchievement());
           }
       }
    }

    @Override
    public void onModuleDisable() {
        super.onModuleDisable();
    }
}
