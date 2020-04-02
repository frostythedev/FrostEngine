package me.frostythedev.frostengine.data.storage;

import org.apache.commons.lang.Validate;

import java.util.HashMap;

public class StorePairHashMap<A, B> implements StorePair<A, B> {

    private HashMap<A, B> storage;

    public StorePairHashMap() {
        this.storage = new HashMap<>();
    }

    @Override
    public B put(A key, B value){
        Validate.notNull(key);
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
    public B get(A key){
        Validate.notNull(key, "Key cannot be empty or null");

        return storage.get(key) != null ? storage.get(key) : null;
    }

    @Override
    public boolean remove(A key){
        Validate.notNull(key, "Key cannot be empty or null");

        Validate.isTrue(storage.containsKey(key), "Key could not be found in this store");

        return this.storage.remove(key) != null;
    }

    public HashMap<A, B> getStorage() {
        return storage;
    }

}
