package me.frostythedev.example.game;

import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.module.ModuleHandler;
import me.frostythedev.frostengine.bukkit.module.ModuleInfo;
import me.frostythedev.frostengine.modules.gameapi.GameAPI;
import org.bukkit.plugin.java.JavaPlugin;

@ModuleInfo(name = "Deathmatch", authors = "frostythedev", version = "1.0.0")
public class ModuleExample extends JavaPlugin implements Module {

    private DeathmatchGame game;

    @Inject
    GameAPI gameAPI;

    @Override
    public void onLoad() {
        ModuleHandler.offerModule(this);
    }

    @Override
    public void enable() {
        this.game = new DeathmatchGame();
        this.game.onMinigameEnable();
        gameAPI.getMinigameManager().loadMinigame(this.game);
        this.game.loadManagers();
    }

    @Override
    public void disable() {

    }

    public DeathmatchGame getGame() {
        return game;
    }

}
