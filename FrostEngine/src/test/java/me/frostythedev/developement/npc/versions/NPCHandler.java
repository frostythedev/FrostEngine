package me.frostythedev.developement.npc.versions;

import me.frostythedev.developement.npc.core.NPCClickAction;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface NPCHandler {

    void spawn(String name, String displayName, Entity entity, NPCClickAction clickAction, Location location);

    void destroy(String name);
}
