package gameapi.teams;

import com.google.common.collect.Maps;
import gameapi.exception.TeamAlreadyLoadedException;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameTeamManager {

    private Map<String, GameTeam> teams;
    private Map<UUID, GameTeam> playerTeams;

    public GameTeamManager() {
        this.teams = Maps.newHashMap();
        this.playerTeams = Maps.newHashMap();
    }

    public boolean hasTeam(Player player) {
        return getPlayerTeam(player) != null;
    }

    public void loadTeam(String name, GameTeam team) throws TeamAlreadyLoadedException {
        if (teams.containsKey(name)) {
            throw new TeamAlreadyLoadedException(name);
        } else {
            teams.put(name, team);
        }
    }

    public void loadTeam(GameTeam team) throws TeamAlreadyLoadedException {
        this.loadTeam(team.getName(), team);
    }

    public boolean setTeam(Player player, GameTeam team) {
        if (getPlayerTeam(player) != null) {
            Team oldTeam = getPlayerTeam(player);

            if (oldTeam.containsPlayer(player)) {
                if (oldTeam.removePlayer(player)) {
                    if (team.addPlayer(player)) {
                        playerTeams.replace(player.getUniqueId(), team);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                playerTeams.remove(player.getUniqueId());
                return setTeam(player, team);
            }
        } else {
            if (team.addPlayer(player)) {
                playerTeams.put(player.getUniqueId(), team);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean clearTeam(Player player) {
        if (getPlayerTeam(player) != null) {
            Team oldTeam = getPlayerTeam(player);
            if (oldTeam.containsPlayer(player)) {
                return oldTeam.removePlayer(player);
            }
        }
        return true;
    }

    public GameTeam getPlayerTeam(Player player) {
        if (playerTeams.containsKey(player.getUniqueId())) {
            return playerTeams.get(player.getUniqueId());
        }
        return null;
    }

    public GameTeam getTeam(String name) {
        if (teams.containsKey(name)) {
            return teams.get(name);
        }
        return null;
    }

    public GameTeam getSmallestTeam() {
        int size = 9999;
        GameTeam smallest = null;

        for (GameTeam team : getTeamsCollect()) {
            if (team.getSize() < size) {
                size = team.getSize();
                smallest = team;
            }
        }
        return smallest;
    }

    public GameTeam getSmallestTeam(List<String> teamsToExclude) {
        int size = 9999;
        GameTeam smallest = null;

        for (GameTeam team : getTeamsCollect()) {
            if (!teamsToExclude.contains(team.getName())) {
                if (team.getSize() < size) {
                    size = team.getSize();
                    smallest = team;
                }
            }
        }
        return smallest;
    }

    public Map<String, GameTeam> getTeams() {
        return teams;
    }

    public Collection<GameTeam> getTeamsCollect() {
        return teams.values();
    }

    public Map<UUID, GameTeam> getPlayerTeams() {
        return playerTeams;
    }
}
