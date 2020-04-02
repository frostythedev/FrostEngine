package me.frosthedev.frostengine.modules;

import me.frosthedev.frostengine.modules.types.HatDiamond;
import me.frosthedev.frostengine.modules.types.HatGold;
import me.frosthedev.frostengine.modules.types.HatIron;
import me.frostythedev.frostengine.bukkit.event.api.AbstractListener;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.module.ModuleAPI;
import me.frostythedev.frostengine.bukkit.module.Modules;
import me.frostythedev.frostengine.modules.ModuleCollectable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;

public class ModuleHats extends Module {

    private ModuleCollectable moduleCollectable;
    private HatManager hm;

    public ModuleHats() {
        super("Hats", "Fun hats cosmetic plugin", "1.0.0", "frostythedev");
    }

    private static ModuleHats inst;

    public static ModuleHats get() {
        return inst;
    }

    @Override
    public void onModuleEnable() {
        inst = this;

        if(ModuleAPI.isLoaded(Modules.COLLECTABLES)){
            this.moduleCollectable = (ModuleCollectable) ModuleAPI.getModule(Modules.COLLECTABLES);
        }else{
            ModuleAPI.disableModule(getModuleName());
        }

        this.hm = new HatManager();

        this.hm.loadHat("Diamond Hat", new HatDiamond());
        this.hm.loadHat("Gold Hat", new HatGold());
        this.hm.loadHat("Iron Hat", new HatIron());

        this.addListener(new AbstractListener() {

            @EventHandler
            public void onQuit(PlayerQuitEvent event){
                Player player = event.getPlayer();

                if(hm.hasHat(player)){
                    hm.removeHat(player);
                }
            }

            @EventHandler
            public void onClick(InventoryClickEvent event){
                if(event.getWhoClicked() instanceof Player){
                    Player player = (Player) event.getWhoClicked();

                    if(event.getSlotType().equals(InventoryType.SlotType.ARMOR)){
                        if(event.getSlot() == 103){
                            if(hm.hasHat(player)){
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        });

        this.addCommand(new CmdHat());
        this.addCommand(new CmdGiveHat());

    }

    @Override
    public void onModuleDisable() {
        super.onModuleDisable();
    }

    public ModuleCollectable getModuleCollectable() {
        return moduleCollectable;
    }

    public HatManager getHatManager() {
        return hm;
    }
}
