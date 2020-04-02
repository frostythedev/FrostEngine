package me.frostythedev.frostengine.modules.ranks.api;

import com.google.common.collect.Lists;
import me.frostythedev.frostengine.bukkit.thread.Tasks;
import me.frostythedev.frostengine.data.core.Database;
import me.frostythedev.frostengine.modules.ranks.ModuleRanks;
import me.frostythedev.frostengine.modules.ranks.objects.Rank;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RankFunctions {

    public static boolean createRank(int id, String name, String server, String prefix, String suffix,int priority,
                                     List<String> permission, List<String> inheritance) {
        if (ModuleRanks.get().getRankManager().getRank(name) != null) {
            return false;
        } else {

            Rank rank = new Rank(id, name, server, prefix, suffix, priority, permission, inheritance);
            ModuleRanks.get().getRankManager().saveRank(rank);
            Tasks.runAsync(() -> ModuleRanks.get().getRankManager().loadRanks());
            return true;
        }

    }

    public static boolean createRank(int id, String name, String server, String prefix, int priority, String suffix){
        return createRank(id,name,server,prefix,suffix,priority, Lists.newArrayList(), Lists.newArrayList());
    }

    public static boolean deleteRank(String name) {
        if (ModuleRanks.get().getRankManager().getRank(name) == null) {
            return false;
        } else {
            String query = "DELETE FROM fe_ranks WHERE name = ?";
            Database.get().syncUpdate(query, new Object[]{name});
            ModuleRanks.get().getRankManager().getRanks().remove(name);
            return true;
        }
    }

    public static boolean deleteRank(Rank rank) {
        return deleteRank(rank.getName());
    }

    public static void listRankInfo(CommandSender sender, Rank rank) {

    }
}
