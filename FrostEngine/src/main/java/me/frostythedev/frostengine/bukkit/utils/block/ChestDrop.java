package me.frostythedev.frostengine.bukkit.utils.block;

import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.utils.firework.InstantFirework;
import me.frostythedev.frostengine.bukkit.thread.Tasks;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestDrop {

    @Inject
    private FEPlugin plugin;

    private Location location;

    public ChestDrop(Location location, ItemStack[] invItems) {
        this.location = location;

        Tasks.run(() -> {

            Location loc = getLocation().add(0,10,0);
            loc.setYaw(180);
            ArmorStand armorStand = getLocation().getWorld().spawn(loc, ArmorStand.class);
            armorStand.setHelmet(new ItemStack(Material.CHEST));
            armorStand.setVisible(false);

            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    FireworkEffect effect = FireworkEffect.builder()
                            .withColor(Color.RED)
                            .withTrail()
                            .build();
                    new InstantFirework(effect, armorStand.getLocation().add(0, 1.5, 0));
                    if (armorStand.isOnGround()) {
                        armorStand.remove();
                        Location loc = armorStand.getLocation();
                        loc.getBlock().setType(Material.CHEST);
                        Chest chest = (Chest) loc.getBlock().getState();
                        chest.getInventory().setContents(invItems);
                        chest.update(true);
                        this.cancel();
                    }
                }
            };
            runnable.runTaskTimerAsynchronously(plugin, 5, 5);
        });
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
