package me.frostythedev.frostengine.modules.ranks.objects;

import com.google.common.collect.Lists;
import me.frostythedev.frostengine.modules.ranks.ModuleRanks;

import java.util.List;
import java.util.UUID;

public class RankPlayer {

    private int id;
    private UUID uuid;
    private List<Rank> ranks;

    public RankPlayer(UUID uuid) {
        this.uuid = uuid;
        this.ranks = Lists.newArrayList();
    }

    public boolean hasPermission(String permission) {
        for (Rank rank : ranks) {
            if (rank.hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRank(Rank rank) {
        if (!ranks.isEmpty()) {

            if(ranks.contains(rank)){
                return true;
            }else{
                //TODO Check priority number and see if it is higher
                if (getHigestRank().inherits(rank.getName())) {
                    return true;
                }
            }
        }

        return false;
    }

    public Rank getHigestRank() {
        return ModuleRanks.get().getRankManager().getHighestRank(ranks);
    }

    ///////////////////////////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////////////////////////


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<Rank> getRanks() {
        return ranks;
    }

    public void setRanks(List<Rank> ranks) {
        this.ranks = ranks;
    }
}
