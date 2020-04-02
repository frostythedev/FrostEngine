package gameapi.teams;

import com.google.common.collect.Sets;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class GameTeam implements Team {

    private String name;
    private String displayName;
    private ChatColor nameColor;
    private ChatColor chatColor;

    private Set<UUID> players;

    public GameTeam(String name, String displayName) {
       this(name, displayName, ChatColor.GREEN, ChatColor.WHITE, Sets.newHashSet());
    }

    public GameTeam(String name, String displayName, ChatColor nameColor, ChatColor chatColor) {
        this(name, displayName, nameColor, chatColor, Sets.newHashSet());
    }

    public GameTeam(String name, String displayName, ChatColor nameColor, ChatColor chatColor, Set<UUID> players) {
        this.name = name;
        this.displayName = displayName;
        this.nameColor = nameColor;
        this.chatColor = chatColor;
        this.players = players;
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
    public Set<UUID> getPlayers() {
        return players;
    }
}
