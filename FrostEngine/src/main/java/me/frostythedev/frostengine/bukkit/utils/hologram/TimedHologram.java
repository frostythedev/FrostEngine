package me.frostythedev.frostengine.bukkit.utils.hologram;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class TimedHologram extends Hologram {

    private int timeInTicks;

    public TimedHologram(String name, List<String> lines, Location location, int timeInTicks) {
        super(name, lines, location);
        this.timeInTicks = timeInTicks;
    }

    @Override
    public boolean show(Player player) {
        if (super.show(player)) {
            FEPlugin.get().getThreadManager().runTaskLater(() -> destroy(player), timeInTicks);
            return true;
        } else {
            return false;
        }

    }
}
