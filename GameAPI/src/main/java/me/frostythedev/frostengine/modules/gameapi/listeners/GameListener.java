package me.frostythedev.frostengine.modules.gameapi.listeners;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import me.frostythedev.frostengine.modules.gameapi.gamestate.core.StateAction;
import me.frostythedev.frostengine.modules.gameapi.teams.GameTeam;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListener implements Listener {

    private Game minigame;

    public GameListener(Game minigame) {
        this.minigame = minigame;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(minigame.getGameState() != null){
            if (minigame.getGameState().isJoinable()) {
                event.setJoinMessage(null);
                minigame.handle(event.getPlayer(), StateAction.JOIN);
            }
        }else{
            System.out.println("GAMESTATE IS NULL");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        minigame.getGameState().handle(event.getPlayer(), StateAction.LEAVE);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
       // System.out.println("Chat call in: " + getClass().getName());
        if (!minigame.getTeamManager().hasTeam(event.getPlayer())) {
            Locale.error(event.getPlayer(), "&cYou are not apart of a team, please contact an administrator if the problem persist.");
            event.setCancelled(true);
        } else {

            GameTeam team = minigame.getTeamManager().getPlayerTeam(event.getPlayer()).get();
            String format = team.getDisplayName() + "&r " + team.getNameColor() + event.getPlayer().getName() + "&r&7: " +
                    team.getChatColor() + event.getMessage();

            if(!minigame.isTeamChat()){
                event.setFormat(Locale.toColors(format));
            }else{
                event.setCancelled(true);
                team.teamChat(format);
            }

        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        minigame.killPlayer(event.getEntity());
    }
}
