package me.frostythedev.frostengine.modules.achievements.core;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.cmd.api.Command;
import me.frostythedev.frostengine.bukkit.cmd.api.SubCommand;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.bukkit.thread.Tasks;
import me.frostythedev.frostengine.config.BukkitDocument;
import me.frostythedev.frostengine.modules.achievements.ModuleAchievement;
import me.frostythedev.frostengine.modules.achievements.menu.AchievementGUI;
import me.frostythedev.frostengine.modules.achievements.tasks.achievements.AchievementSaver;
import org.apache.commons.io.FileUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Collection;

public class CmdAchievement extends Command {

    public CmdAchievement() {
        super("achievements");
        setUsage("help");

        this.addSubCommand(new CommandGUI());
        this.addSubCommand(new CommandFSave());
        this.addSubCommand(new CommandHelp());
    }

    class CommandHelp extends SubCommand {

        public CommandHelp() {
            super("help", "", "", "Show the help page");
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            sendHelp(sender);
        }
    }

    class CommandGUI extends SubCommand {

        public CommandGUI() {
            super("gui", "", "", "Opens the Achievements GUI");
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                AchievementGUI gui = new AchievementGUI();
                gui.open(player);
            }
        }
    }

    class CommandFSave extends SubCommand {

        public CommandFSave() {
            super("fsave", "", "", "Force saves all achievements");
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                Collection<File> achievementFiles = FileUtils.listFiles(new File(Achievement.SAVE_DIRECTORY), null, false);

                for (File file : achievementFiles) {
                    BukkitDocument document = BukkitDocument.of(file.getAbsolutePath());
                    document.load();

                    if (document.getString("data") != null) {
                        Achievement achievement = FEPlugin.getGson().fromJson(document.getString("data"), Achievement.class);
                        if (!ModuleAchievement.get().getManager().isAchievement(achievement.getName())) {
                            Tasks.run(new AchievementSaver(achievement));
                        }
                    }
                }

                Locale.success(player, "&aYou have force-saved all achievements from memory onto mysql.");
            }
        }
    }


}
