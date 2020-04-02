package me.frostythedev.examples;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.utils.scoreboard.ScoreboardBuilder;
import me.frostythedev.frostengine.bukkit.utils.scoreboard.ScoreboardManager;
import me.frostythedev.frostengine.bukkit.utils.scoreboard.ScoreboardType;
import me.frostythedev.frostengine.bukkit.utils.scoreboard.ScoreboardWrapper;
import org.bukkit.scoreboard.DisplaySlot;

public class ExampleScoreboard{

    public void scoreboard(){
        ScoreboardManager sbManager = new ScoreboardManager(FEPlugin.get(), 10);
        ScoreboardWrapper wrapper = new ScoreboardBuilder(sbManager, DisplaySlot.SIDEBAR)
                .title("Test Scoreboard")
                .entry(0, "Line1")
                .entry(1, "Line2")
                .entry(2, "Line3")
                .entry(3, "Line4")
                .objective("examples", "dummy")
                .type(ScoreboardType.NORMAL)
                .build()
                ;

      //  sbManager.assign(event.getPlayer(), wrapper);
    }
}
