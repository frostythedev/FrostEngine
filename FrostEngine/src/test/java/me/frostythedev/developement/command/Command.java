package me.frostythedev.developement.command;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class Command implements CommandExecutor {

    private String name;
    private String permission;
    private String description;

    private Map<String, SubCommand> subCommandMap;
    private Map<Integer, Arg> argMap;

    private boolean playerOnly = false;

    public Command(String name, String permission, String description) {
        this.name = name;
        this.permission = permission;
        this.description = description;
        this.subCommandMap = Maps.newHashMap();
        this.argMap = Maps.newHashMap();
    }

    public void addArg(int index, Arg arg) {
        if (!argMap.containsKey(index)) {
            argMap.put(index, arg);
        }
    }

    public void removeArg(int index) {
        if (argMap.containsKey(index)) {
            argMap.remove(index);
        }
    }

    public void changeArg(int index, Arg arg) {
        if (argMap.containsKey(index)) {
            argMap.replace(index, arg);
        }
    }

    public Arg getArg(int index){
        if(argMap.containsKey(index)){
           return argMap.get(index);
        }
        return null;
    }

    public boolean checkArgs(String[] args){
        if(argMap.isEmpty()){
            return true;
        }else{


            return false;
        }
    }

    public boolean registerSubCommand(SubCommand subCommand) {
        if (getSubCommand(subCommand.getName()) != null) {
            return false;
        } else {
            subCommandMap.put(subCommand.getName(), subCommand);
            return true;
        }
    }

    public boolean unregisterSubCommand(String name) {
        if (getSubCommand(name) != null) {
            subCommandMap.remove(name);
            return true;
        } else {
            return false;
        }
    }

    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission(permission)) {
            Locale.error(sender, "You are not allowed to do this!");
            return;
        }

        if (args.length == 0) {
            sendHelp(sender);
        } else {
            if (getSubCommand(args[0]) != null) {
                getSubCommand(args[0]).run(sender, args);
            } else {
                Locale.error(sender, "Invalid command!");
            }
        }
    }

    public void sendHelp(CommandSender sender) {
        String label = "&e/" + name + "&a";
        String format;
        Locale.message(sender, "&6>> Command Help: " + label);
        Locale.blankLine(sender);
        Locale.message(sender, "&7&l<> - Required | [] - Optional");
        Locale.blankLine(sender);

        if (subCommandMap.isEmpty()) {
            Locale.error(sender, "This command does not have any sub types.");
        } else {
            for (Map.Entry<String, SubCommand> map : subCommandMap.entrySet()) {
                String key = map.getKey();
                SubCommand subCommand = map.getValue();
                if (subCommand.hasAlias()) {
                    if (sender.hasPermission(subCommand.getPermission())) {
                        String args = key + String.join("|", subCommand.getAlias());
                        format = label + " [" + args + "] " + subCommand.getUsage() + " - " + subCommand.getDescription();
                        Locale.message(sender, format);
                    }
                } else {
                    if (sender.hasPermission(subCommand.getPermission())) {
                        format = label + " " + key + " " + subCommand.getUsage() + " - " + subCommand.getDescription();
                        Locale.message(sender, format);
                    }
                }
            }
        }
    }

    public SubCommand getSubCommand(String name) {
        if ((subCommandMap.containsKey(name))) {
            return subCommandMap.get(name);
        }
        return null;
    }

    @Deprecated
    public SubCommand getSubCommandWithAlias(String name) {
        for (SubCommand subCommand : subCommandMap.values()) {
            if (subCommand.getName().equalsIgnoreCase(name) || subCommand.containsAlias(name)) {
                return subCommand;
            }
        }
        return null;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase(getName())) {
            if (isPlayerOnly()) {
                if (!(commandSender instanceof Player)) {
                    Locale.error(commandSender, "Only players can do this.");
                    return false;
                }
            }
            run(commandSender, strings);
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, SubCommand> getSubCommandMap() {
        return subCommandMap;
    }

    public void setSubCommandMap(Map<String, SubCommand> subCommandMap) {
        this.subCommandMap = subCommandMap;
    }

    public boolean isPlayerOnly() {
        return playerOnly;
    }

    public void setPlayerOnly(boolean playerOnly) {
        this.playerOnly = playerOnly;
    }
}
