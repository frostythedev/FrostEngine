package me.frostythedev.versus;

import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.ArenaBaseMinigame;
import me.frostythedev.versus.executors.VersusEndExecutor;
import me.frostythedev.versus.executors.VersusStartExecutor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class VersusGame extends ArenaBaseMinigame {

    @Inject
    VersusPlugin plugin;

    UUID player1, player2;

    public VersusGame(UUID player1, UUID player2) {
        super("1v1", "Players fight one another to the death", 2, 2);

        setName(player1.toString() + "_1v1s");

        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void setup() {
        this.setGameStartExecutor(new VersusStartExecutor());
        this.setGameEndExecutor(new VersusEndExecutor());
        this.switchState(0);
    }

    @Override
    public void broadcast(String message) {
        Locale.message(message, getPlayer1(), getPlayer2());
    }

    @Override
    public boolean addPlayer(Player player) {
        return this.player1.equals(player.getUniqueId()) ||
                this.player2.equals(player.getUniqueId());

        /*
        More Robust System that can ben used generally

        GameTeamManager teamManager = getTeamManager();
        if(teamManager.getTeams().size() != 2) return false;

        if(!teamManager.getTeam(player.getUniqueId().toString()).isPresent()){
            GameTeam team = teamManager.getTeam(player.getUniqueId().toString()).get();

            if(team.addPlayer(player)){
                return true;
            }
        }

        if(teamManager.getTeam(player.getUniqueId().toString()).isPresent()){
            return teamManager.getTeam(player.getUniqueId().toString()).get().addPlayer(player);
        }
        return false;*/
    }

    @Override
    public boolean removePlayer(Player player) {

        if(this.player1 != null && this.player1.equals(player.getUniqueId())) {
            this.player1 = null;
            return true;

        }else if(this.player2 != null && this.player2.equals(player.getUniqueId())) {
            this.player2 = null;
            return true;
        }

        return false;
    }

    @Override
    public void killPlayer(Player player) {

        this.switchState(3);

        StringBuilder winner = new StringBuilder();

        if(this.player1.equals(player.getUniqueId())) {
            winner.append(getPlayer2().getName());
        }else if(this.player2.equals(player.getUniqueId())) {
            winner.append(getPlayer1().getName());
        }

        broadcast("&e&l>> The winner is " + winner.toString() + "!");

        /*GameTeamManager teamManager = getTeamManager();
        if (teamManager.getTeam(plugin.getConfig().getString(Constants.DEFAULT_SPEC_TEAM_NAME_PATH)).isPresent()) {

            if (teamManager.setTeam(player, plugin.getConfig().getString(Constants.DEFAULT_SPEC_TEAM_NAME_PATH))) {
                getGameEndExecutor().endGame(this);
                // Not sure what I wanna do with this yet
            }
        }*/
    }

    public Player getPlayer1() {
        return Bukkit.getPlayer(this.player1);
    }

    public Player getPlayer2() {
        return Bukkit.getPlayer(this.player2);
    }

    public void setPlayer1(UUID player1) {
        this.player1 = player1;
    }

    public void setPlayer2(UUID player2) {
        this.player2 = player2;
    }
}
