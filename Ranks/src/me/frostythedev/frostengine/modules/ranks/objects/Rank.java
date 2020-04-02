package me.frostythedev.frostengine.modules.ranks.objects;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.modules.ranks.ModuleRanks;
import me.frostythedev.frostengine.modules.ranks.RankConstants;
import org.bukkit.entity.Player;

import java.util.List;

public class Rank {

    private int id;
    private String name;
    private String server;
    private String prefix;
    private String suffix;
    private int priority;

    private List<String> permissions;
    private List<String> inheritance;

    public Rank(int id, String name, String server, String prefix, String suffix, int priority, List<String> permissions, List<String> inheritance) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.prefix = prefix;
        this.suffix = suffix;
        this.priority = priority;
        this.permissions = permissions;
        this.inheritance = inheritance;
    }

    ///////////////////////////////////////////////
    // MADE METHODS
    ///////////////////////////////////////////////

    public String chat(Player player, String format) {
        String chat = RankConstants.CHAT_FORMAT;
        if(ModuleRanks.get().getConfig().getString("group-format." + name) != null){
            chat = ModuleRanks.get().getConfig().getString("group-format." + name);
        }else{
            if(FEPlugin.get().getConfig().getString("Chat.format." + name) != null){
                chat = FEPlugin.get().getConfig().getString("Chat.format." + name);
            }
        }
        chat.replace("%prefix%", getPrefix());
        chat.replace("%player%", player.getName());
        chat.replace("%suffix%", getSuffix());
        chat.replace("%message%", format);
        return chat;
    }

    public boolean hasPermission(String permission) {
        if (!inheritance.isEmpty()) {
            for (String rankName : inheritance) {
                Rank rank = ModuleRanks.get().getRankManager().getRank(rankName);
                if (rank != null) {
                    if (rank.hasPermission(permission)) {
                        return true;
                    }
                }
            }
        }
        return permissions.contains(permission);
    }

    public boolean inherits(String rank) {
        return inheritance.contains(rank);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getInheritance() {
        return inheritance;
    }

    public void setInheritance(List<String> inheritance) {
        this.inheritance = inheritance;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
