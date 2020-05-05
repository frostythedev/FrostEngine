package me.frostythedev.frostengine.bukkit.utils.scoreboard;

import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import me.frostythedev.frostengine.bukkit.utils.StringUtil;
import me.frostythedev.frostengine.bukkit.utils.scoreboard.scrolling.ScrollingScoreboardWrapper;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ObjectiveRegisterData {
   // private static final Commons commons = Commons.getInstance();

    private String action;
    private String name;

    private DisplaySlot slot;

    private String title;

    public ObjectiveRegisterData(String name, String action) {
        this.name = name;
        this.action = action;
    }

    public ObjectiveRegisterData() {
    }

    public ObjectiveRegisterData name(String name) {
        this.name = name;
        return this;
    }

    public ObjectiveRegisterData action(String action) {
        this.action = action;
        return this;
    }

    public ObjectiveRegisterData title(String title) {
        this.title = StringUtil.colorize(title);
        return this;
    }

    public ObjectiveRegisterData slot(DisplaySlot slot) {
        this.slot = slot;
        return this;
    }

    public Objective register(ScrollingScoreboardWrapper wrapper) {
        return register(wrapper.getScoreboard());
    }

    public Objective register(Scoreboard board) {
        if (board == null) {
            LogUtils.warning("Cannot register objectives onto a null board");
            return null;
        }

        Objective objective = board.registerNewObjective(name, action);

        if (slot != null) {
            objective.setDisplaySlot(slot);
        }

        if (title != null && !title.isEmpty()) {
            objective.setDisplayName(title);
        }

        return objective;
    }

    public String getAction() {
        return action;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public DisplaySlot getSlot() {
        return slot;
    }
}
