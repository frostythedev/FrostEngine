package me.frostythedev.frostengine.data.storage;

import org.apache.commons.lang.Validate;

import java.util.HashMap;

public class StoreHashMap<T> implements Store<T> {

    private HashMap<String, T> storage;

    public StoreHashMap() {
        this.storage = new HashMap<>();
    }

    @Override
    public T put(String key, T value){
        Validate.notNull(value);
        if(storage.containsKey(key)){
            storage.replace(key, value);
            return value;
        }else{
            storage.put(key, value);
            return value;
        }
    }

    @Override
    public T get(String key){
        Validate.notEmpty(key, "Key cannot be empty or null");
        return storage.get(key);
    }

    @Override
    public boolean remove(String key){
        Validate.notEmpty(key, "Key cannot be empty or null");
        Validate.isTrue(storage.containsKey(key), "Key could not be found in this store");

        return this.storage.remove(key) != null;
    }

    public HashMap<String, T> getStorage() {
        return storage;
    }
}
