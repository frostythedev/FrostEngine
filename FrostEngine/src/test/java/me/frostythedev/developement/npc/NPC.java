package me.frostythedev.developement.npc;

import com.google.inject.Inject;
import me.frostythedev.developement.npc.core.NPCClickAction;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class NPC {

    @Inject ModuleNPC moduleNPC;

    private String name;
    private String displayName;
    private Entity entity;
    private NPCClickAction action;
    private Location location;

    public NPC(String name, String displayName, Entity entity, NPCClickAction action, Location location) {
        this.name = name;
        this.displayName = displayName;
        this.entity = entity;
        this.action = action;
        this.location = location;
    }

    public boolean spawn(){
        if(moduleNPC.getNpcManager().isNPC(name)){
            return false;
        }

        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public NPCClickAction getAction() {
        return action;
    }

    public void setAction(NPCClickAction action) {
        this.action = action;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
