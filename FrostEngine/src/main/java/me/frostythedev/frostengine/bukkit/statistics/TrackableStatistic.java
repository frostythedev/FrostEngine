package me.frostythedev.frostengine.bukkit.statistics;

import me.frostythedev.frostengine.data.storage.StorePairHashMap;

import java.util.Map;

public class TrackableStatistic<A, B> {

    private StorePairHashMap<A, B> storage;

    public TrackableStatistic() {
        this.storage = new StorePairHashMap<>();
    }

    public StorePairHashMap<A, B> getDefaultStorage() {
        return this.storage;
    }

    public B getStored(A a) {
        return this.storage.get(a);
    }

    public void modify(A a, B b) {
        this.storage.put(a, b);
    }

    public String getAllData(String divider, String breaker, boolean format) {
        String data = "";
        if (!getDefaultStorage().getStorage().isEmpty()) {
            int count = 0;
            for (Map.Entry<A, B> entry : getDefaultStorage().getStorage().entrySet()) {
                if (count == getDefaultStorage().getStorage().size() - 1) {
                    data += entry.getKey().toString() + divider + entry.getValue().toString();
                } else {
                    if (format) {
                        data += entry.getKey().toString() + divider + entry.getValue().toString() + breaker + "\n";
                    } else {
                        data += entry.getKey().toString() + divider + entry.getValue().toString() + breaker;
                    }
                }
                count++;
            }
        }

        return data;
    }

    public String getAllData() {
        return this.getAllData(";", "#", false);
    }

    /*
    public void getDataFromString(String str){
        Validate.notNull(str, "String cannot be null");
        Validate.notEmpty(str, "String cannot be empty");
        Validate.isTrue(str.contains("#") || str.contains(";"), "Cannot read data in this toColors, must contain [#] or [;]");

        if(str.contains("#")){
            String[] parts = str.split("#");

            for(String s : parts){
                String[] data = s.split(";");

               this.storage.put()
            }

        }
    }
     */
}
