package me.frosthedev.frostengine.modules.types;

import me.frosthedev.frostengine.modules.HatGadget;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class HatIron extends HatGadget {

    public HatIron() {
        super(1, "Iron Hat", true);
        this.setHatMaterial(Material.IRON_BLOCK);
        this.setParts("IronHat");
    }

    @Override
    public void onEquip(Player player) {
    }
}
