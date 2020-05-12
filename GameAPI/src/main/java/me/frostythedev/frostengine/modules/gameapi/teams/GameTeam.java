package me.frostythedev.frostengine.modules.gameapi.teams;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GameTeam implements Team {

    private String name;
    private String displayName;
    private ChatColor nameColor;
    private ChatColor chatColor;

    private List<UUID> players;

    public GameTeam(String name, String displayName) {
       this(name, displayName, ChatColor.GREEN, ChatColor.WHITE, Lists.newArrayList());
    }

    public GameTeam(String name, String displayName, ChatColor nameColor, ChatColor chatColor) {
        this(name, displayName, nameColor, chatColor, Lists.newArrayList());
    }

    public GameTeam(String name, String displayName, ChatColor nameColor, ChatColor chatColor, List<UUID> players) {
        this.name = name;
        this.displayName = displayName;
        this.nameColor = nameColor;
        this.chatColor = chatColor;
        this.players = players;
    }

    public void teamChat(String message){
        if(!players.isEmpty()){
            for(UUID pUUid : this.players){
                Player ps = Bukkit.getPlayer(pUUid);

                if(ps != null && ps.isOnline()){
                    ps.sendMessage(Locale.toColors(message));
                    ps = null;
                }
            }
        }
    }

    public int getSize(){
        return players.size();
    }

    @Override
    public boolean addPlayer(Player player) {
        return !containsPlayer(player) && players.add(player.getUniqueId());
    }

    @Override
    public boolean removePlayer(Player player) {
        return containsPlayer(player) && players.remove(player.getUniqueId());
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public ChatColor getNameColor() {
        return nameColor;
    }

    public void setNameColor(ChatColor nameColor) {
        this.nameColor = nameColor;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public void setChatColor(ChatColor chatColor) {
        this.chatColor = chatColor;
    }

    @Override
    public List<UUID> getPlayers() {

        for(UUID uuid : this.players){
            Player p = Bukkit.getPlayer(uuid);
            if(p == null || !p.isOnline()){
                removePlayer(p);
            }
           p = null;
        }
        // Validate uuids of all players in the set before returning
        return players;
    }
}
