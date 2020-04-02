package me.frostythedev.frostengine.modules.achievements.tasks.players;

import me.frostythedev.frostengine.modules.achievements.api.AchievementPlayerCallback;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.core.Database;
import me.frostythedev.frostengine.data.core.DatabaseField;
import me.frostythedev.frostengine.modules.achievements.ModuleAchievement;
import me.frostythedev.frostengine.modules.achievements.player.AchievementPlayer;
import org.bukkit.entity.Player;

public class PlayerLoader implements Runnable {

    private Player player;

    public PlayerLoader(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        if(ModuleAchievement.get().getPlayerManager().isLoaded(player)){
            return;
        }else{
            if(Database.get().hasConnection()){
                AchievementPlayerCallback playerCallback = new AchievementPlayerCallback();
                Database.get().syncQuery("SELECT * FROM " + DatabaseField.ACHIEVEMENT_PLAYERS_TABLE + " WHERE uuid=?",
                        new Object[]{player.getUniqueId().toString()}, playerCallback);
                if(playerCallback.result() != null){
                    AchievementPlayer achievementPlayer = playerCallback.result();
                    ModuleAchievement.get().getPlayerManager().loadPlayer(player, achievementPlayer);
                    return;
                }else{
                    AchievementPlayer achievementPlayer = new AchievementPlayer(player);
                    Database.get().syncUpdate("INSERT INTO " + DatabaseField.ACHIEVEMENT_PLAYERS_TABLE + "(uuid,data) VALUES(?,?)",
                            new Object[]{player.getUniqueId().toString(),FEPlugin.getGson().toJson(achievementPlayer)});
                    ModuleAchievement.get().getPlayerManager().loadPlayer(player, achievementPlayer);
                }
            }else{
                AchievementPlayer achievementPlayer = new AchievementPlayer(player);
                ModuleAchievement.get().getPlayerManager().loadPlayer(player, achievementPlayer);
            }
        }
    }
}
