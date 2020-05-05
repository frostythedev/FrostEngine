package me.frostythedev.frostengine.modules.gameapi.arenas;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import me.frostythedev.frostengine.bukkit.impl.Constants;

public class NullGameArena extends GameArena {

    public NullGameArena() {
        super(Constants.ID, null, Constants.UNDEFINED, Constants.UNDEFINED,
                Constants.UNDEFINED, Lists.newArrayList(),
                false, false, Sets.newHashSet(), Sets.newHashSet());
    }

    @Override
    public GameArena deserialize(JsonElement element) {
        return super.deserialize(element);
    }
}
