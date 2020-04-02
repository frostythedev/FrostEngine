package me.frostythedev.frostengine.modules.achievements.commands;

import me.frostythedev.frostengine.bukkit.cmd.api.SubCommand;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.command.CommandSender;

public class CmdCreate extends SubCommand {

    public CmdCreate() {
        super("create", "frostengine.admin");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length >= 5) {
         /*   String name = args[1];
            boolean enabled = Boolean.valueOf(args[2]);
            AchievementType type = AchievementType.valueOf(args[3]);
            List<String> description = Lists.newArrayList();

            //if (args[4].contains("\"))*/
        } else {
            Locale.message(sender, Locale.COMMAND_INVALID_ARGS);
        }
    }
}
