package me.frosthedev.frostengine.modules;

import me.frostythedev.frostengine.modules.ColleactableGadget;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class HatGadget extends ColleactableGadget {

    private Material hatMaterial;
    private byte data = 0;

    public HatGadget(int id, String name, boolean enabled) {
        super(id, name, enabled);
    }

    public Material getHatMaterial() {
        return hatMaterial;
    }

    public void setHatMaterial(Material hatMaterial) {
        this.hatMaterial = hatMaterial;
    }

    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }

    public ItemStack getHat(){
        return new ItemStack(this.getHatMaterial(),1,this.getData());
    }

    @Override
    public void activate(Player player) {
        ModuleHats.get().getHatManager().storeHat(player, this.getHat() );
        this.onEquip(player);
    }

    public abstract void onEquip(Player player);
}
