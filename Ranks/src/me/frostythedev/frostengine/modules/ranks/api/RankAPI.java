package me.frostythedev.frostengine.modules.ranks.api;

import com.google.common.collect.Lists;
import me.frostythedev.frostengine.modules.ranks.ModuleRanks;
import me.frostythedev.frostengine.modules.ranks.objects.Rank;
import me.frostythedev.frostengine.modules.ranks.objects.RankPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class RankAPI {

    @Deprecated
    public static RankPlayer getRankPlayer(Player player) {
        if (ModuleRanks.get().getPlayerManager().isLoaded(player)) {
            return ModuleRanks.get().getPlayerManager().getPlayer(player);
        }
        return null;
    }

    public static boolean isRankHigher(Rank rank1, Rank rank2) {
        return rank1.getPriority() > rank2.getPriority();
    }

    public static String getServerName() {
        return ModuleRanks.get().getServerName();
    }

    public static List<Rank> getRanksForServer(String server) {
        List<Rank> ranks = Lists.newArrayList();

        for (Rank rank : ModuleRanks.get().getRankManager().getRanks().values()) {
            if (rank.getServer().equals(server) || rank.getServer().equals("global")) {
                ranks.add(rank);
            }
        }

        return ranks;
    }

    public static Rank getRankInList(List<Rank> ranks, String name) {
        for (Rank rank : ranks) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }
}
