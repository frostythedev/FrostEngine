package me.frostythedev.developement.command;

import org.bukkit.command.CommandSender;

public class ExampleCmd extends Command {

    public ExampleCmd() {
        super("example", "examples.exam", "Example command");
        setPlayerOnly(true);

        registerSubCommand(new One());
    }

    class One extends SubCommand {

        public One() {
            super("one", "", "One to you too", "[two/three/four]");
        }

        @Override
        public void onExecute(CommandSender sender, String[] args) {

        }
    }
}
