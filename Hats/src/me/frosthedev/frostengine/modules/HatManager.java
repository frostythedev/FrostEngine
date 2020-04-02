package me.frosthedev.frostengine.modules;

import me.frostythedev.frostengine.bukkit.metadata.Metadata;
import me.frostythedev.frostengine.data.StorePairHashMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.UUID;

public class HatManager {

    private final StorePairHashMap<String, HatGadget> hats;
    private final StorePairHashMap<UUID, ItemStack> storage;

    public HatManager() {
        this.hats = new StorePairHashMap<>();
        this.storage = new StorePairHashMap<>();
    }

    public boolean hasHat(Player player){
        return this.storage.contains(player.getUniqueId());
    }

    public ItemStack getHat(Player player){
        return hasHat(player) ? this.storage.get(player.getUniqueId()) : new ItemStack(Material.AIR);
    }

    public boolean storeHat(Player player, ItemStack stack){
        if(!hasHat(player)){

            ItemStack hat = player.getInventory().getHelmet();
            if(hat == null || hat.getType().equals(Material.AIR)) hat = new ItemStack(Material.AIR);

            this.storage.put(player.getUniqueId(), hat);
            player.getInventory().setHelmet(stack);
            Metadata.applyTrue(player, "Hat");
            return true;
        }
        return true;
    }

    public void removeHat(Player player){
        if(hasHat(player)){
            player.getInventory().setHelmet(getHat(player));
            Metadata.applyFalse(player, "Hat");
            this.storage.remove(player.getUniqueId());
        }
    }

    public void loadHat(String name, HatGadget hat){
        this.hats.put(name, hat);
    }

    public void unloadHat(String name){
        if(this.isHat(name)){
            this.hats.remove(name);
        }
    }

    public HatGadget getHat(String name){
        return this.hats.get(name);
    }

    public boolean isHat(String name){
        return this.getHat(name) != null;
    }

    public Collection<HatGadget> getHats(){
        return this.hats.getStorage().values();
    }
}
