package me.frostythedev.developement.npc;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

public class NPCManager {

    private Map<String, NPC> npcs;

    public NPCManager() {
        this.npcs = Maps.newHashMap();
    }

    public NPC getNPC(String name){
        if(npcs.containsKey(name)){
            return npcs.get(name);
        }
        return null;
    }

    public boolean isNPC(String name){
        return getNPC(name) != null;
    }

    public Map<String, NPC> getNpcs() {
        return npcs;
    }

    public Collection<NPC> getNPCs(){
        return getNpcs().values();
    }
}
