package me.frostythedev.frostengine.legacy.cmds.api;

import org.bukkit.command.CommandSender;

public abstract class SubCommand implements Comparable<SubCommand>{

    private Command command;
    private String name;
    private String usage;
    private String description;
    private String permission = "";
    private String[] aliases;
    private int minArgs;

    private boolean playerOnly;

    public SubCommand(Command command, String name) {
        this(name, command.getPermission(), "", "");
    }

    public SubCommand(String name, String permission) {
        this(name, permission, "", "");
    }

    public SubCommand(String name, String permission, String usage, String description) {
        this(null, name, permission, usage, description, null);
    }

    public SubCommand(Command command, String name, String permission, String usage, String description, String[] aliases) {
        this.command = command;
        this.name = name;
        this.permission = permission;
        this.usage = usage;
        this.description = description;
        this.aliases = aliases;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public String[] getAliases() {
        return aliases;
    }

    public boolean hasAlias(){
        return getAliases() != null && getAliases().length != 0;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    public boolean isPlayerOnly() {
        return playerOnly;
    }

    public void setPlayerOnly(boolean playerOnly) {
        this.playerOnly = playerOnly;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public void setMinArgs(int minArgs) {
        this.minArgs = minArgs;
    }

    public abstract void run(CommandSender sender, String[] args);

    @Override
    public int compareTo(SubCommand o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }
}
