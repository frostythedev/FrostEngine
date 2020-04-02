package me.frostythedev.frostengine.modules.ranks.managers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.core.Database;
import me.frostythedev.frostengine.modules.ranks.callback.RankLoadCallback;
import me.frostythedev.frostengine.modules.ranks.callback.SingleRankLoadCallback;
import me.frostythedev.frostengine.modules.ranks.objects.Rank;

import java.util.List;
import java.util.Map;

public class RankManager {

    private Map<String, Rank> ranks;

    public RankManager() {
        this.ranks = Maps.newHashMap();
    }

    public boolean loadRank(Rank rank) {
        return getRank(rank.getName()) == null && ranks.put(rank.getName(), rank) != null;
    }

    public boolean loadRankFromDB(String name) {
        if (getRank(name) != null) {
            return false;
        }

        SingleRankLoadCallback singleRankLoadCallback = new SingleRankLoadCallback();
        Database.get().syncQuery("SELECT * FROM fe_ranks WHERE name=?", new Object[]{name}, singleRankLoadCallback);
        if (singleRankLoadCallback.result() != null) {
            return loadRank((Rank) singleRankLoadCallback.result());
        }
        return false;
    }

    public void saveRank(Rank rank) {
        if (getRank(rank.getName()) != null) {
            String rankData = FEPlugin.getGson().toJson(rank);
            Database.get().asyncUpdate("UPDATE fe_ranks SET data=? WHERE name=?", new Object[]{rankData, rank.getName()});
        } else {
            String rankData = FEPlugin.getGson().toJson(rank);
            Database.get().asyncUpdate("INSERT INTO fe_ranks (name,data), VALUES (?,?)", new Object[]{rank.getName(), rankData});
        }
    }

    public void loadRanks() {
        RankLoadCallback callback = new RankLoadCallback();
        Database.get().syncQuery("SELECT * FROM fe_ranks;", null, callback);
        if (callback.result() != null) {
            if (callback.result() != null) {
                this.ranks = (Map<String, Rank>) callback.result();
            }
        }else{
            Rank defaultRank = new Rank(0, "default", "global", "&7", "&8", 0, Lists.newArrayList(), Lists.newArrayList());
            saveRank(defaultRank);
            loadRank(defaultRank);
        }
    }

    public void saveRanks() {
        getRanks().values().forEach(this::saveRank);
    }

    public Rank getRank(String name) {
        if (ranks.containsKey(name)) {
            return ranks.get(name);
        }
        return null;
    }

    public Rank getHighestRank(List<Rank> rankList) {
        int highest = 0;
        Rank high = null;

        for (Rank rank : rankList) {
            if (rank.getPriority() > highest) {
                highest = rank.getPriority();
                high = rank;
            }
        }

        return high;
    }

    public Map<String, Rank> getRanks() {
        return ranks;
    }
}
