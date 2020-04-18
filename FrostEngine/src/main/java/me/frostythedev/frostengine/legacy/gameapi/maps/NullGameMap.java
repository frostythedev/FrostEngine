package me.frostythedev.frostengine.legacy.gameapi.maps;

import com.google.gson.JsonElement;

public class NullGameMap extends GameMap {

    public NullGameMap() {
        super(-1, "", "", false, false, null, null);
    }

    @Override
    public GameMap deserialize(JsonElement element) {
        return super.deserialize(element);
    }
}
