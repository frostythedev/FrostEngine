package me.frostythedev.developement.command;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class SubCommand {

    private String name;
    private List<String> alias;
    private String permission;
    private String description;
    private String usage;

    public SubCommand(String name, String permission, String description, String usage) {
       this(name, new ArrayList<>(), permission, description, usage);
    }

    private SubCommand(String name, List<String> alias, String permission, String description, String usage) {
        this.name = name;
        this.alias = alias;
        this.permission = permission;
        this.description = description;
        this.usage = usage;
    }

    protected void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission(permission)) {
            Locale.error(sender, "&cYou are not allowed to do this!");
            return;
        }

        onExecute(sender, args);
    }

    public void onExecute(CommandSender sender, String[] args){}

    public boolean containsAlias(String name) {
        return alias.contains(name);
    }

    public boolean hasAlias() {
        return !alias.isEmpty();
    }

    public String getName() {
        return name;
    }

    public List<String> getAlias() {
        return alias;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }
}
