package me.frostythedev.frostengine.bukkit.statistics;

import me.frostythedev.frostengine.data.storage.StorePairHashMap;

public class StatisticManager {

    private StorePairHashMap<String, TrackableStatistic> storage;

    public StatisticManager() {
        this.storage = new StorePairHashMap<>();
    }

    public TrackableStatistic getById(String id){
        return this.storage.get(id);
    }

    public boolean createStatistic(String id, TrackableStatistic statistic){
        if(!exists(id)){
            storage.put(id, statistic);
            return true;
        }
        return false;
    }

    /*public boolean create(String id, boolean string, boolean uuid, boolean number, boolean whole, boolean deci){
        if(!exists(id)){
            if(string){
                if(number){
                    if(whole){
                        TrackableStatistic<String, Integer> stat = new TrackableStatistic<>(id, true);
                        this.storage.put(id, stat);
                        return true;
                    }else if (deci){
                        TrackableStatistic<String, Double> stat = new TrackableStatistic<>(id, true);
                        this.storage.put(id, stat);
                        return true;
                    }
                }else{
                    TrackableStatistic<String, Object> stat = new TrackableStatistic<>(id, true);
                    this.storage.put(id, stat);
                    return true;
                }
            }else if (uuid){
                if(number){
                    if(whole){
                        TrackableStatistic<UUID, Integer> stat = new TrackableStatistic<>(id, true);
                        this.storage.put(id, stat);
                        return true;
                    }else if (deci){
                        TrackableStatistic<UUID, Double> stat = new TrackableStatistic<>(id, true);
                        this.storage.put(id, stat);
                        return true;
                    }
                }else{
                    TrackableStatistic<UUID, Object> stat = new TrackableStatistic<>(id, true);
                    this.storage.put(id, stat);
                    return true;
                }
            }
        }
        return false;
    }*/

    public boolean delete(String id){
        if(exists(id)){
            this.storage.remove(id);
            return true;
        }
        return false;
    }

    public boolean exists(String id){
        return this.getById(id) != null;
    }
}
