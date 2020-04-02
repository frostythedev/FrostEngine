package me.frostythedev.frostengine.modules.ranks;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.thread.Tasks;
import me.frostythedev.frostengine.data.core.Database;
import me.frostythedev.frostengine.modules.ranks.adaptor.RankAdaptor;
import me.frostythedev.frostengine.modules.ranks.cmds.CmdGiveRank;
import me.frostythedev.frostengine.modules.ranks.listeners.PlayerListener;
import me.frostythedev.frostengine.modules.ranks.managers.RankManager;
import me.frostythedev.frostengine.modules.ranks.cmds.CmdTakeRank;
import me.frostythedev.frostengine.modules.ranks.managers.PlayerManager;
import me.frostythedev.frostengine.modules.ranks.objects.Rank;
import me.frostythedev.frostengine.modules.ranks.objects.RankPlayer;

public class ModuleRanks extends Module {

    private static ModuleRanks inst;

    private RankManager rankManager;
    private PlayerManager playerManager;

    private String serverName = "global";

    public ModuleRanks() {
        super("Ranks");

        inst = this;
    }

    @Override
    public void onModuleEnable() {

        this.saveDefaultConfig();

        if(this.getConfig().getString("server-name") != null){
            this.getConfig().set("server-name", "global");
            this.saveConfig();
            this.serverName = this.getConfig().getString("server-name");
        }

        this.rankManager = new RankManager();
        this.playerManager = new PlayerManager();

        FEPlugin.get().getAdaptors().put(RankPlayer.class, new RankAdaptor());
        FEPlugin.get().getAdaptors().put(Rank.class, new RankAdaptor());

        if (Database.get().hasConnection()) {
            Database.get().createTable("fe_ranks", "id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(36), data VARCHAR(9999)");
            Database.get().createTable("fe_players", "id INT PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(36), data VARCHAR(9999)");

            Tasks.run(() -> {
                getRankManager().loadRanks();
            });

            this.addListener(new PlayerListener());

            this.addCommand(new CmdGiveRank());
            this.addCommand(new CmdTakeRank());
        } else {
            onModuleDisable();
        }
    }

    @Override
    public void onModuleDisable() {
        getRankManager().saveRanks();
        getPlayerManager().saveAllPlayers();
    }

    public static ModuleRanks get() {
        return inst;
    }

    public String getServerName() {
        return serverName;
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
