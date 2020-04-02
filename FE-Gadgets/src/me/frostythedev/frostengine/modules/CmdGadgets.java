package me.frostythedev.frostengine.modules;

import me.frostythedev.frostengine.bukkit.cmd.api.Command;
import me.frostythedev.frostengine.bukkit.cmd.api.SubCommand;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdGadgets extends Command {

    public CmdGadgets() {
        super("gadgets");
        setPlayerOnly(true);

        addSubCommand(new GiveSub(this));
        addSubCommand(new OpenSub(this));
    }


    class OpenSub extends SubCommand {

        public OpenSub(Command command) {
            super(command, "open");
            setPlayerOnly(true);
            setMinArgs(1);
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            Player player = (Player) sender;
            Locale.success(player, "Opened gadgets menu");
            new GUIGadgets(player).open(player);
        }
    }
    class GiveSub extends SubCommand {

        public GiveSub(Command command) {
            super(command, "give");
            setPlayerOnly(false);
            setUsage("<player> <gadgetId>");
            setMinArgs(3);
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            String playerName = args[1];
            int gadgetId = -1;

            try {
                gadgetId = Integer.parseInt(args[2]);
            }catch (NumberFormatException ignored){
                Locale.error(sender, "&cInvalid gadgetId, please try again.");
                return;
            }

            Player player = Bukkit.getPlayer(playerName);
            if(player != null && player.isOnline()){
               if( CollectableAPI.giveCollectable(player, ModuleCollectable.get().getManager().getGadget(gadgetId))){
                   Locale.success(sender, "&aSuccessfully given gadget with Id: " + gadgetId);
               }else{
                   Locale.error(sender, "&cAn error has occurred, please contact an administrator.");
               }
            }else{
                Locale.error(sender, "&cThis player either does not exist or is not currently online.");
            }
        }
    }
}
