package me.frostythedev.frostengine.data.storage;

public interface Store<T> {

    default T put(String key, T value){
        return null;
    }

    default boolean remove(String key){
        return false;
    }

    default boolean contains(String key){
        return get(key) != null;
    }

    default T get(String key){
        return null;
    }
}
