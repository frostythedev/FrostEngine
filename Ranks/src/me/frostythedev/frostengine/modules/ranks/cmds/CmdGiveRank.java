package me.frostythedev.frostengine.modules.ranks.cmds;

import me.frostythedev.frostengine.bukkit.cmd.Command;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.ranks.ModuleRanks;
import me.frostythedev.frostengine.modules.ranks.objects.Rank;
import me.frostythedev.frostengine.modules.ranks.objects.RankPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class CmdGiveRank extends Command {

    public CmdGiveRank() {
        super("giverank", "frostengine.admin");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(args.length <= 0){
            Locale.message(sender, Locale.from("fe_command.invalid-args"));

        }else if (args.length >= 2){
            String playerName = args[0];

            if(Bukkit.getPlayer(playerName) != null){
                RankPlayer rankPlayer = ModuleRanks.get().getPlayerManager().getPlayer(Bukkit.getPlayer(playerName));

               if(rankPlayer != null){
                   String rankName = args[1];
                   Rank rank = ModuleRanks.get().getRankManager().getRank(rankName);
                   if(rank != null){

                       if(!rankPlayer.hasRank(rank)){
                           rankPlayer.getRanks().add(rank);
                           Locale.success(sender, "You have given " + playerName + " rank: &6" + rank.getName() +
                                   " &aon server: &b" + rank.getServer() + " &a.");
                       }else{
                           Locale.error(sender, "&c" + playerName + " already has this rank.");
                       }
                   }else{
                       Locale.error(sender, "&cCould not find a rank with that name.");
                   }
               }else{
                   Locale.error(sender, "&cAn error occurred while loaded RankPlayer.class");
               }
            }else{
                Locale.error(sender, "&cCould not find a player with that name.");
            }
        }
    }
}
