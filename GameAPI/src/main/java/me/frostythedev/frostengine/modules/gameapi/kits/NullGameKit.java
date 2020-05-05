package me.frostythedev.frostengine.modules.gameapi.kits;

import com.google.gson.JsonElement;

public class NullGameKit extends GameKit{

    public NullGameKit() {
        super(-1, "", "");
    }

    @Override
    public GameKit deserialize(JsonElement element) {
        return super.deserialize(element);
    }
}
