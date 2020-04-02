package me.frostythedev.frostengine.bukkit.gameapi.arenas.command;

import me.frostythedev.frostengine.bukkit.Messages;
import me.frostythedev.frostengine.bukkit.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.cmd.api.Command;
import me.frostythedev.frostengine.bukkit.cmd.api.SubCommand;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.data.core.Database;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCmd extends Command {

    public ArenaCmd() {
        super("arena");

        addSubCommand(new Create(this));
        addSubCommand(new SetName(this));
        addSubCommand(new AddSpawn(this));
        addSubCommand(new RemoveSpawn(this));
        addSubCommand(new SetLobby(this));
        addSubCommand(new SetEnabled(this));
        addSubCommand(new AddBreakable(this));
        addSubCommand(new AddPlaceable(this));
        addSubCommand(new Save(this));
    }


    private class Create extends SubCommand {

        public Create(ArenaCmd cmd) {
            super(cmd, "create");
            setPlayerOnly(true);
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            Player player = (Player) sender;
           if(!FEPlugin.get().getArenaCreatorManager().enableCreator(player)){
               Locale.error(player, "&cYou are already in creator mode.");
           }
        }
    }

    private class SetName extends SubCommand {

        public SetName(ArenaCmd cmd) {
            super(cmd, "setname");
            setPlayerOnly(true);
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            Player player = (Player) sender;
            if(args.length >= 1){
                String arenaName = args[1];

                if(FEPlugin.get().getArenaCreatorManager().isCreator(player)){
                    GameArena arena = FEPlugin.get().getArenaCreatorManager().getArena(player).getArena();
                    arena.setArenaName(arenaName);

                    Locale.message(player, Messages.MESSAGE_PREFIX + "You have set this arena's name to: &e"
                            + arenaName);
                }else{
                    Locale.error(player, "&cYou are not currently a creator.");
                }
            }else{
                Locale.error(player, "&cInvalid args.");
            }
        }
    }

    private class AddSpawn extends SubCommand {

        public AddSpawn(ArenaCmd cmd) {
            super(cmd, "addspawn");
            setPlayerOnly(true);
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            Player player = (Player) sender;
            if(FEPlugin.get().getArenaCreatorManager().isCreator(player)){
               GameArena arena = FEPlugin.get().getArenaCreatorManager().getArena(player).getArena();


                if(arena.getSpawnLocations().contains(player.getLocation())){
                    Locale.error(player, "&cThis spawnpoint has already been added.");
                }else{
                    arena.getSpawnLocations().add(player.getLocation());
                    Locale.message(player, Messages.MESSAGE_PREFIX + "Spawnpoint has been added! Current size: &6" +
                    arena.getSpawnLocations().size());
                }
            }else{
                Locale.error(player, "&cYou are not currently a creator.");
            }
        }
    }

    private class RemoveSpawn extends SubCommand {

        public RemoveSpawn(ArenaCmd cmd) {
            super(cmd, "removespawn");
            setPlayerOnly(true);
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            Player player = (Player) sender;
            if(FEPlugin.get().getArenaCreatorManager().isCreator(player)){
               GameArena arena = FEPlugin.get().getArenaCreatorManager().getArena(player).getArena();


                if(arena.isRegisteredLocation(player.getLocation(), true)){
                    arena.getSpawnLocations().remove(arena.getExactLocation(player.getLocation()));
                    Locale.message(player, Messages.MESSAGE_PREFIX + "Spawnpoint has been removed! Current size: &6" +
                            arena.getSpawnLocations().size());

                }else{
                    Locale.error(player, "&cThis spawnpoint is not apart of this arena.");
                }
            }else{
                Locale.error(player, "&cYou are not currently a creator.");
            }
        }
    }

    private class SetLobby extends SubCommand {

        public SetLobby(ArenaCmd cmd) {
            super(cmd, "setlobby");
            setPlayerOnly(true);
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            Player player = (Player) sender;

            if(args.length >= 1){
                Boolean lobby = Boolean.parseBoolean(args[1]);

                if(FEPlugin.get().getArenaCreatorManager().isCreator(player)){
                   GameArena arena = FEPlugin.get().getArenaCreatorManager().getArena(player).getArena();

                    arena.setLobby(lobby);
                    Locale.message(player, Messages.MESSAGE_PREFIX + "You have set this arena's lobby state to: " +
                            "&e" + lobby);
                }else{
                    Locale.error(player, "&cYou are not currently a creator.");
                }
            }
        }
    }

    private class SetEnabled extends SubCommand {

        public SetEnabled(ArenaCmd cmd) {
            super(cmd, "setenabled");
            setPlayerOnly(true);
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            Player player = (Player) sender;

            if(args.length >= 1){
                Boolean enable = Boolean.parseBoolean(args[1]);

                if(FEPlugin.get().getArenaCreatorManager().isCreator(player)){
                   GameArena arena = FEPlugin.get().getArenaCreatorManager().getArena(player).getArena();

                    arena.setEnabled(enable);
                    Locale.message(player, Messages.MESSAGE_PREFIX + "You have set this arena's state to: " +
                            "&e" + enable);
                }else{
                    Locale.error(player, "&cYou are not currently a creator.");
                }
            }
        }
    }

    private class AddBreakable extends SubCommand {

        public AddBreakable(ArenaCmd cmd) {
            super(cmd, "addbreakable");
            setPlayerOnly(true);
            setUsage("<blockid>");
            setMinArgs(2);
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            Player player = (Player) sender;

            if(args.length >= 1){
                int id;

                if(FEPlugin.get().getArenaCreatorManager().isCreator(player)){
                   GameArena arena = FEPlugin.get().getArenaCreatorManager().getArena(player).getArena();
                    try{
                        id = Integer.parseInt(args[1]);
                        if(arena.getBreakable().contains(id)){
                            Locale.error(player, "&cThis block is already not breakable.");
                        }else{
                            arena.getBreakable().add(id);
                            Locale.message(player, Messages.MESSAGE_PREFIX + "You have made made this block " +
                                    "breakable: " +
                                    "&eMaterial: " + Material.getMaterial(id).toString() + ", " +
                                    "&eBlock Id: " + id);
                        }
                    }catch (NumberFormatException ignored){
                        Locale.error(player, "&cInteger required, found: '" + args[1] + "'.");
                    }
                }else{
                    Locale.error(player, "&cYou are not currently a creator.");
                }
            }
        }
    }

    private class AddPlaceable extends SubCommand {

        public AddPlaceable(ArenaCmd cmd) {
            super(cmd, "addplaceable");
            setPlayerOnly(true);
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            Player player = (Player) sender;

            if(args.length >= 1){
                int id;

                if(FEPlugin.get().getArenaCreatorManager().isCreator(player)){
                    GameArena arena = FEPlugin.get().getArenaCreatorManager().getArena(player).getArena();
                    try{
                        id = Integer.parseInt(args[1]);
                        if(arena.getPlaceable().contains(id)){
                            Locale.error(player, "&cThis block is already placeable.");
                        }else{
                            arena.getPlaceable().add(id);
                            Locale.message(player, Messages.MESSAGE_PREFIX + "You have made made this block " +
                                    "placeable: " +
                                    "&eMaterial: " + Material.getMaterial(id).toString() + ", " +
                                    "&eBlock Id: " + id);
                        }
                    }catch (NumberFormatException ignored){
                        Locale.error(player, "&cInteger required, found: '" + args[1] + "'.");
                    }
                }else{
                    Locale.error(player, "&cYou are not currently a creator.");
                }
            }
        }
    }

    private class Save extends SubCommand {

        public Save(ArenaCmd cmd) {
            super(cmd, "save");
            setPlayerOnly(true);
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            Player player = (Player) sender;

            if(FEPlugin.get().getArenaCreatorManager().isCreator(player)){
                GameArena arena = FEPlugin.get().getArenaCreatorManager().getArena(player).getArena();

                if(arena.isUseable()){
                    Database.get().syncUpdate("INSERT INTO fe_arenas(data) VALUES (?)",
                            new Object[]{FEPlugin.getGson().toJson(arena, GameArena.class)});
                    Locale.message(player, "The arenas has been saved.");

                }else{
                    Locale.error(player, "&cThis arena is missing mandatory fields. Please double check and " +
                            "make sure that your arena has a NAME and valid SPAWN-POINTS.");
                }
            }else{
                Locale.error(player, "&cYou are not currently a creator.");
            }
        }
    }



    private class List extends SubCommand {

        public List(ArenaCmd cmd) {
            super(cmd, "list");
            setPlayerOnly(true);
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            Player player = (Player) sender;


        }
    }
}
