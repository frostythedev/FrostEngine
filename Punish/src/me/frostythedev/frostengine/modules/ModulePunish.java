package me.frostythedev.frostengine.modules;

import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.data.core.Database;
import me.frostythedev.frostengine.data.core.DatabaseField;

public class ModulePunish extends Module {

    private static ModulePunish inst;

    public static ModulePunish get() {
        return inst;
    }

    public ModulePunish() {
        super("punishments", "Punishments core and implementation", "1.0.0", "frostythedev");
        inst = this;

        Database.get().createTable(DatabaseField.PUNISHMENT_TABLE.getValue(), DatabaseField.PUNISHMENT_VALUES.getValue());
        Database.get().createTable(DatabaseField.PUNISHMENT_PLAYER_TABLE.getValue(), DatabaseField.ACHIEVEMENT_PLAYERS_VALUES.getValue());

    }
}
