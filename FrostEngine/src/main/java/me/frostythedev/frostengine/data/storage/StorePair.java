package me.frostythedev.frostengine.data.storage;

public interface StorePair<A,B> {

    default B put(A key, B value){
        return null;
    }

    default boolean remove(A key){
        return false;
    }

    default boolean contains(A key){
        return get(key) != null;
    }

    default B get(A key){
        return null;
    }
}
