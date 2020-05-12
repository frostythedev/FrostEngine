package me.frostythedev.example.game.utilities;

import com.google.inject.Inject;
import me.frostythedev.example.game.DeathmatchGame;
import me.frostythedev.frostengine.modules.gameapi.core.utilities.MinigameUtility;
import me.frostythedev.frostengine.data.core.Database;

public class MapLoadUtility extends MinigameUtility<DeathmatchGame>{

    public MapLoadUtility(DeathmatchGame minigame) {
        super(minigame);
    }

    @Inject Database database;

    @Override
    public void start() {
        if(database.hasConnection()){
          getMinigame().getArenaManager().loadArenas();
        }
    }

    @Override
    public void end() {

    }
}
