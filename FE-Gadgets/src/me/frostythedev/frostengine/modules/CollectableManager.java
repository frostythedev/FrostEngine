package me.frostythedev.frostengine.modules;

import me.frostythedev.frostengine.data.storage.StorePairHashMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CollectableManager {

    private StorePairHashMap<String, ColleactableGadget> gadgets;
    private Map<Integer, String> gadgetIds;

    public CollectableManager() {
        this.gadgets = new StorePairHashMap<>();
        this.gadgetIds = new HashMap<>();
    }

    public void loadGadget(ColleactableGadget gadget){
        this.gadgets.put(gadget.getName(), gadget);
        this.gadgetIds.put(gadget.getId(), gadget.getName());
    }

    public boolean unloadGadget(String name){
        return this.gadgets.remove(name);
    }

    public ColleactableGadget getGadget(String name){
        return this.gadgets.get(name);
    }

    public ColleactableGadget getGadget(int id){
        return this.gadgets.get(this.gadgetIds.get(id));
    }

    public boolean isEnabled(String name){
        return this.getGadget(name) != null && this.getGadget(name).isEnabled();
    }

    public Collection<ColleactableGadget> getAllGadgets(){
        return this.gadgets.getStorage().values();
    }
}

