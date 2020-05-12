package me.frostythedev.frostengine.modules.gameapi.teams;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.modules.gameapi.exception.TeamAlreadyLoadedException;
import org.bukkit.entity.Player;

import java.util.*;

public class GameTeamManager {

    private Map<String, GameTeam> teams;
    private Map<UUID, String> playerTeams;

    public GameTeamManager() {
        this.teams = Maps.newHashMap();
        this.playerTeams = Maps.newHashMap();
    }

    public boolean hasTeam(Player player) {
        return getPlayerTeam(player).isPresent();
    }

    public void loadTeam(String name, GameTeam team) throws TeamAlreadyLoadedException {
        if (teams.containsKey(name)) {
            throw new TeamAlreadyLoadedException(name);
        } else {
            teams.put(name, team);
        }
    }

    public boolean createTeam(String teamName){
        if(getTeam(teamName).isPresent()) return false;
       return this.teams.put(teamName, new GameTeam(teamName, "Players")) != null;
    }

    public Optional<List<UUID>> getPlayersOfTeam(String teamName){
//        Optional<GameTeam> team = getTeam(teamName);
        return getTeam(teamName).map(GameTeam::getPlayers);


        /*

        LEGACY
        if(getTeam(teamName) != null){
            ArrayList<Player> players = new ArrayList<>();
            for(UUID uuid : getTeam(teamName).getPlayers()){

                Player p = Bukkit.getPlayer(uuid);
                if(p == null || !p.isOnline()){
                    getTeam(teamName).removePlayer(p);
                }
                players.add(p);


            }
            return Optional.of(players);
        }*/
    }

    public Optional<ArrayList<UUID>> getAllPlayers(){
        if(getTeams().size() == 0) return Optional.empty();

        ArrayList<UUID> allPlayers = new ArrayList<>();

        for(String team : getTeams().keySet()){
            getPlayersOfTeam(team).ifPresent(allPlayers::addAll);
        }
        return Optional.of(allPlayers);
    }

    public void loadTeam(GameTeam team) throws TeamAlreadyLoadedException {
        this.loadTeam(team.getName(), team);
    }

    public boolean setTeam(Player player, GameTeam team) {

        if(clearTeam(player)){
            if (team.addPlayer(player)) {
                return playerTeams.replace(player.getUniqueId(), team.getName()) != null;
            }
        }
        return false;

        /*if (getPlayerTeam(player) != null) {
            Team oldTeam = getPlayerTeam(player);

            if (oldTeam.containsPlayer(player)) {
                if (oldTeam.removePlayer(player)) {

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
        }*/
    }

    public boolean setTeam(Player player, String teamName){
        if(!getTeam(teamName).isPresent()) return false;
        return setTeam(player, getTeam(teamName).get());
    }

    public boolean clearTeam(Player player) {
        if(getPlayerTeam(player).isPresent()){
            playerTeams.remove(player.getUniqueId());
            return getPlayerTeam(player).get().removePlayer(player);
        }

        return true;
    }

    public Optional<GameTeam> getPlayerTeam(Player player) {
        if (playerTeams.containsKey(player.getUniqueId())) {
            return getTeam(playerTeams.get(player.getUniqueId()));
        }
        return Optional.empty();
    }

    public Optional<GameTeam> getTeam(String name) {
        if (teams.containsKey(name)) {
            return Optional.of(teams.get(name));
        }
        return Optional.empty();
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

    public Map<UUID, String> getPlayerTeams() {
        return playerTeams;
    }
}
