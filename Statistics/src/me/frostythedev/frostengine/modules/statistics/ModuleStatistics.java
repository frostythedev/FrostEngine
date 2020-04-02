package me.frostythedev.frostengine.modules.statistics;

import me.frostythedev.frostengine.bukkit.module.Module;

public class ModuleStatistics extends Module {

    public ModuleStatistics() {
        super("Statistics", "tracks me.frostythedev.frostengine.statistics", "1.0.0", "frostythedev");
    }

    private StatisticManager sm;

    @Override
    public void onModuleEnable() {
        this.sm = new StatisticManager();
    }

    public Statistic getStatistic(String name){
        return this.getManager().getByName(name);
    }

    public StatisticManager getManager() {
        return sm;
    }
}
