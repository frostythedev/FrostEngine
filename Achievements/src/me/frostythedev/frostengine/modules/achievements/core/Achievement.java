package me.frostythedev.frostengine.modules.achievements.core;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.config.BukkitDocument;
import me.frostythedev.frostengine.modules.achievements.ModuleAchievement;
import me.frostythedev.frostengine.modules.achievements.player.AchievementPlayer;
import me.frostythedev.frostengine.util.Trackable;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Achievement implements Trackable {

    public static final String SAVE_DIRECTORY = ModuleAchievement.get().getModuleDirectory() + "/saved";

    private int id;
    private String name;
    private boolean enabled;

    private String achievementType;
    private List<String> description;

    public Achievement(int id, String name, boolean enabled, String achievementType, List<String> description) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.achievementType = achievementType;
        this.description = description;
    }

    public Achievement(int id, String name, boolean enabled, String achievementType, String... description) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.achievementType = achievementType;
        this.description = Arrays.asList(description);
    }

    @Override
    public String toString() {
        return FEPlugin.getGson().toJson(this);
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getAchievementType() {
        return achievementType;
    }

    public void setAchievementType(String achievementType) {
        this.achievementType = achievementType;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public String getSaveDirectory() {
        return ModuleAchievement.get().getModuleDirectory() + "/saved/" + getName() + ".yml";
    }

    public boolean give(Player player) {
        if (isEnabled()) {
            if (ModuleAchievement.get().getPlayerManager().isLoaded(player)) {
                AchievementPlayer ap = ModuleAchievement.get().getPlayerManager().getPlayer(player);

                if (ap.hasUnlocked(getName())) {
                    return false;
                } else {
                    ap.unlock(this, true);
                    if (ap.hasUnlocked(getName())) {
                        sendUnlockedMessage(player);
                        player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);
                        return true;
                    }
                }

                return false;
            } else {
                ModuleAchievement.get().getPlayerManager().loadPlayer(player, new AchievementPlayer(player));
                return give(player);
            }
        }else{
            return false;
        }
    }

    public void save() {
        BukkitDocument document = BukkitDocument.of(getSaveDirectory());
        document.load();
        document.set("data", toString());
        document.save();
    }

    public void sendUnlockedMessage(Player player) {
        Locale.message(player, "&a&k%&a>> Achievement Unlocked: &6" + getName() + " &a<<&k%");
    }
}
