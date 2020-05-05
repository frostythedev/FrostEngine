package me.frostythedev.example.game.utilities;

import me.frostythedev.example.game.DeathmatchGame;
import me.frostythedev.frostengine.modules.gameapi.core.utilities.MinigameUtility;
import me.frostythedev.frostengine.data.core.Database;

public class MapLoadUtility extends MinigameUtility<DeathmatchGame>{

    public MapLoadUtility(DeathmatchGame minigame) {
        super(minigame);
    }

    @Override
    public void start() {
        if(Database.get().hasConnection()){
          getMinigame().getArenaManager().loadArenas();
        }
    }

    @Override
    public void end() {

    }
}
