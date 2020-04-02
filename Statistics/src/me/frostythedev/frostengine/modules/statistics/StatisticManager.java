package me.frostythedev.frostengine.modules.statistics;

import me.frostythedev.frostengine.data.storage.StorePairHashMap;

import java.util.UUID;

public class StatisticManager {

    private StorePairHashMap<String, Statistic> storage;

    public StatisticManager() {
        this.storage = new StorePairHashMap<>();
    }

    public Statistic getByName(String name){
        return this.storage.get(name);
    }

    public boolean create(String name, boolean string, boolean uuid, boolean number, boolean whole, boolean deci){
        if(!exists(name)){
            if(string){
                if(number){
                    if(whole){
                        TrackableStatistic<String, Integer> stat = new TrackableStatistic<>(name, true);
                        this.storage.put(name, stat);
                        return true;
                    }else if (deci){
                        TrackableStatistic<String, Double> stat = new TrackableStatistic<>(name, true);
                        this.storage.put(name, stat);
                        return true;
                    }
                }else{
                    TrackableStatistic<String, Object> stat = new TrackableStatistic<>(name, true);
                    this.storage.put(name, stat);
                    return true;
                }
            }else if (uuid){
                if(number){
                    if(whole){
                        TrackableStatistic<UUID, Integer> stat = new TrackableStatistic<>(name, true);
                        this.storage.put(name, stat);
                        return true;
                    }else if (deci){
                        TrackableStatistic<UUID, Double> stat = new TrackableStatistic<>(name, true);
                        this.storage.put(name, stat);
                        return true;
                    }
                }else{
                    TrackableStatistic<UUID, Object> stat = new TrackableStatistic<>(name, true);
                    this.storage.put(name, stat);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean delete(String name){
        if(exists(name)){
            this.storage.remove(name);
            return true;
        }
        return false;
    }

    public boolean exists(String name){
        return this.getByName(name) != null;
    }
}
