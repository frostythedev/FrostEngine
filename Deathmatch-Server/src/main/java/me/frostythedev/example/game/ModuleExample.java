package me.frostythedev.example.game;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.modules.gameapi.ModuleGameAPI;

public class ModuleExample extends Module {


    private static ModuleExample inst;

    public ModuleExample() {
        super("Deathmatch");
        setParentName("GameAPI");
    }
    private DeathmatchGame game;

    @Override
    public void onModuleEnable() {
        super.onModuleEnable();

        inst = this;

        if(FEPlugin.get().getModuleLoader().isLoaded("GameAPI")){

           // System.out.println("ModuleGameAPI is loaded");

            ModuleGameAPI.get().getChildModules().add(this);

            this.game = new DeathmatchGame();
            this.game.onMinigameEnable();
            ModuleGameAPI.get().getMinigameManager().loadMinigame(this.game);
        }else{
            //System.out.println("ModuleGameAPI is NOT loaded");
        }

       /* if(!FEPlugin.get().getModuleLoader().isLoaded("GameAPI")){
            System.out.println("GameAPI is not loaded, try to load...");

            if(FEPlugin.get().getModuleLoader().loadExternModule(ModuleGameAPI.class) != null){
                System.out.println("Minigame Example is not laoded!");

            }else{
                System.out.println("ModuleGameAPI could not be loaded");
            }
        }else{
            System.out.println("GameAPI IS loaded.");
        }*/
    }

    @Override
    public void onCompleteLoad() {
        this.game.loadManagers();
        //System.out.println("[EXAMPLE] Loaded managers!");
    }

    @Override
    public void onModuleDisable() {
        super.onModuleDisable();
    }


    public DeathmatchGame getGame() {
        return game;
    }

    public static ModuleExample get() {
        return inst;
    }

}
