package me.frostythedev.frostengine.modules.gameapi.arenas.command;

import co.aikar.commands.annotation.*;
import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.Messages;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.GameAPI;
import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("arena|acreator|creator")
@CommandPermission("frostengine.gameapi.arenaadmin")
public class ArenaCommand {

    @Inject
    private GameAPI gameAPI;

    @HelpCommand
    public void onHelp(Player player){

    }

    @Subcommand("list")
    public void onList(CommandSender sender){

    }

    @Subcommand("assign")
    @Conditions("creator")
    public void assign(Player player, String minigameName){

        GameArena arena = gameAPI.getArenaCreatorManager().getArena(player).getArena();
        arena.setMinigameName(minigameName);

        Locale.message(player, Messages.MESSAGE_PREFIX + "You have set this arena's parent Minigame to: &e"
                + minigameName);
    }

    @Subcommand("create")
    public void create(Player player, String arenaName){
        if(!gameAPI.getArenaCreatorManager().enableCreator(player)){
            Locale.error(player, "&cYou are already in creator mode.");
            return;
        }

        gameAPI.getArenaCreatorManager().getArena(player).getArena().setArenaName(arenaName);
        Locale.message(player, Messages.MESSAGE_PREFIX + "You have set this arena's name to: &e"
                + arenaName);
    }

    @Subcommand("set name")
    @Conditions("creator")
    public void setName(Player player, String arenaName){

        GameArena arena = gameAPI.getArenaCreatorManager().getArena(player).getArena();
        arena.setArenaName(arenaName);

        Locale.message(player, Messages.MESSAGE_PREFIX + "You have set this arena's name to: &e"
                + arenaName);
    }

    @Subcommand("add spawn")
    @Conditions("creator")
    public void addSpawn(Player player){
        GameArena arena = gameAPI.getArenaCreatorManager().getArena(player).getArena();
        
        if(arena.getSpawnLocations().contains(player.getLocation())){
            Locale.error(player, "&cThis spawnpoint has already been added.");
        }else{
            arena.getSpawnLocations().add(player.getLocation());
            Locale.message(player, Messages.MESSAGE_PREFIX + "Spawnpoint has been added! Current size: &6" +
                    arena.getSpawnLocations().size());
        }
    }

    @Subcommand("remove spawn")
    @Conditions("creator")
    public void removeSpawn(Player player){
        GameArena arena = gameAPI.getArenaCreatorManager().getArena(player).getArena();


        if(arena.isRegisteredLocation(player.getLocation(), true)){
            arena.getSpawnLocations().remove(arena.getExactLocation(player.getLocation()));
            Locale.message(player, Messages.MESSAGE_PREFIX + "Spawnpoint has been removed! Current size: &6" +
                    arena.getSpawnLocations().size());

        }else{
            Locale.error(player, "&cThis spawnpoint is not apart of this arena.");
        }
    }

    @Subcommand("setlobby")
    @Conditions("creator")
    @Syntax("<true/false>")
    public void setLobby(Player player, boolean enable){
        if(gameAPI.getArenaCreatorManager().isCreator(player)){
            GameArena arena = gameAPI.getArenaCreatorManager().getArena(player).getArena();

            arena.setLobby(enable);
            Locale.message(player, Messages.MESSAGE_PREFIX + "You have set this arena's lobby state to: " +
                    "&e" + enable);
        }else{
            Locale.error(player, "&cYou are not currently a creator.");
        }
    }

    @Subcommand("setenabled")
    @Conditions("creator")
    @Syntax("<true/false>")
    public void setEnabled(Player player, boolean enable){
        GameArena arena = gameAPI.getArenaCreatorManager().getArena(player).getArena();

        arena.setEnabled(enable);
        Locale.message(player, Messages.MESSAGE_PREFIX + "You have set this arena's state to: " +
                "&e" + enable);
    }

    @Subcommand("addplaceable")
    @Conditions("creator")
    @Syntax("<id>")
    public void onAddPlace(Player player, int placeableId){

        if(placeableId <= 0) {
            Locale.error(player, "You must provide a +ve id.");
            return;
        }

        GameArena arena = gameAPI.getArenaCreatorManager().getArena(player).getArena();

        arena.getPlaceable().add(placeableId);
        Locale.message(player, Messages.MESSAGE_PREFIX + "You have made made this block " +
                "placeable: " +
                "&eMaterial: " + Material.getMaterial(placeableId).toString() + ", " +
                "&eBlock Id: " + placeableId);
    }

    @Subcommand("addbreakable")
    @Conditions("creator")
    @Syntax("<id>")
    public void onAddBreak(Player player, int placeableId){

        if(placeableId <= 0) {
            Locale.error(player, "You must provide a +ve id.");
            return;
        }

        GameArena arena = gameAPI.getArenaCreatorManager().getArena(player).getArena();

        arena.getBreakable().add(placeableId);
        Locale.message(player, Messages.MESSAGE_PREFIX + "You have made made this block " +
                "breakable: " +
                "&eMaterial: " + Material.getMaterial(placeableId).toString() + ", " +
                "&eBlock Id: " + placeableId);
    }
}
