package me.frostythedev.frostengine.modules.statistics;

import me.frostythedev.frostengine.data.storage.StorePairHashMap;

public interface Statistic {

     String getName();
    boolean isEnabled();
    StorePairHashMap<?, ?> getDefaultStorage();
}
