package me.frostythedev.frostengine.modules.achievements;

import me.frostythedev.frostengine.modules.achievements.adaptor.AchievementAdaptor;
import me.frostythedev.frostengine.modules.achievements.core.Achievement;
import me.frostythedev.frostengine.modules.achievements.core.CmdAchievement;
import me.frostythedev.frostengine.modules.achievements.manager.AchievementManager;
import me.frostythedev.frostengine.modules.achievements.player.AchievementPlayer;
import me.frostythedev.frostengine.modules.achievements.tasks.achievements.AchievementLoader;
import me.frostythedev.frostengine.modules.achievements.tasks.players.PlayerLoader;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.event.api.AbstractListener;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.thread.Tasks;
import me.frostythedev.frostengine.data.core.Database;
import me.frostythedev.frostengine.data.core.DatabaseField;
import me.frostythedev.frostengine.modules.achievements.adaptor.AchievementPlayerAdaptor;
import me.frostythedev.frostengine.modules.achievements.player.PlayerManager;
import me.frostythedev.frostengine.modules.achievements.tasks.players.PlayerSaver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ModuleAchievement extends Module {

    private static ModuleAchievement inst;

    public static ModuleAchievement get() {
        return inst;
    }

    public ModuleAchievement() {
        super("Achievements", "Tracking achievements and functions", "1.0.0", "frostythedev");
        inst = this;
    }

    private AchievementManager am;
    private PlayerManager playerManager;

    @Override
    public void onModuleEnable() {
        this.am = new AchievementManager();
        this.playerManager = new PlayerManager();

        FEPlugin.get().getAdaptors().put(Achievement.class, new AchievementAdaptor());
        FEPlugin.get().getAdaptors().put(AchievementPlayer.class, new AchievementPlayerAdaptor());

        saveDefaultConfig();

        if(Database.get().hasConnection()){
            Database.get().createTable(DatabaseField.ACHIEVEMENT_TABLE, DatabaseField.KEY_DATA_VALUES);
            Database.get().createTable(DatabaseField.ACHIEVEMENT_PLAYERS_TABLE, DatabaseField.ACHIEVEMENT_PLAYERS_VALUES);

            Tasks.runAsync(new AchievementLoader());

            this.addListener(new AbstractListener() {
                @EventHandler
                public void onJoin(PlayerJoinEvent event) {
                    Tasks.run(new PlayerLoader(event.getPlayer()));
                }

                @EventHandler
                public void onQuit(PlayerQuitEvent event) {
                    if (playerManager.isLoaded(event.getPlayer())) {
                        Tasks.runAsync(new PlayerSaver(playerManager.getPlayer(event.getPlayer())));
                    }
                }
            });

            this.addCommand(new CmdAchievement());

            for (Player ps : Bukkit.getOnlinePlayers()) {
                playerManager.loadPlayer(ps, new AchievementPlayer(ps));
            }
        }else{
            onModuleDisable();
        }
    }

    @Override
    public void onModuleDisable() {
        this.am = null;
    }

    public AchievementManager getManager() {
        return am;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
