package me.frosthedev.frostengine.modules.types;

import me.frosthedev.frostengine.modules.HatGadget;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class HatDiamond extends HatGadget {

    public HatDiamond() {
        super(1, "Diamond Hat", true);
        this.setHatMaterial(Material.DIAMOND_BLOCK);
        this.setParts("DiamondHat");
    }

    @Override
    public void onEquip(Player player) {
    }
}
