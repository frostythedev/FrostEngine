package me.frostythedev.frostengine.bukkit.cmd.api;

import org.bukkit.command.CommandSender;

public class CommandHelp extends SubCommand {

    private final Command command;

    public CommandHelp(Command command) {
        super("help", "", "", "");
        this.command = command;
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        this.command.sendHelp(sender);
    }
}
