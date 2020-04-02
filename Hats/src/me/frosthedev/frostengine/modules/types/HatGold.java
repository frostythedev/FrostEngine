package me.frosthedev.frostengine.modules.types;

import me.frosthedev.frostengine.modules.HatGadget;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class HatGold extends HatGadget {

    public HatGold() {
        super(1, "Gold Hat", true);
        this.setHatMaterial(Material.GOLD_BLOCK);
        this.setParts("GoldHat");
    }

    @Override
    public void onEquip(Player player) {
    }
}
