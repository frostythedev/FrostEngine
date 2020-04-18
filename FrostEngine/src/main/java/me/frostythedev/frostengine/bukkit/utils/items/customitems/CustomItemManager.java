package me.frostythedev.frostengine.bukkit.utils.items.customitems;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

public class CustomItemManager {

    private static CustomItemManager instance;

    public static CustomItemManager getInstance() {
        if(instance == null){
            instance = new CustomItemManager();
        }
        return instance;
    }

    private Map<String, CustomItem> customItems;

    private CustomItemManager() {
        //no instance
        customItems = Maps.newHashMap();
    }

    public CustomItem getItem(String name){
        return customItems.getOrDefault(name, null);
    }

    public Collection<CustomItem> getCustomItems() {
        return customItems.values();
    }
}
