package me.frostythedev.developement.npc.core;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface NPCClickAction {

    NPCClickAction NONE = (player, type) -> {};

    void click(Player player, ClickType type);
}
