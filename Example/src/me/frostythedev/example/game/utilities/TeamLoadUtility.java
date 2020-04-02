package me.frostythedev.example.game.utilities;

import me.frostythedev.example.game.DeathmatchGame;
import me.frostythedev.frostengine.bukkit.gameapi.core.utilities.MinigameUtility;
import me.frostythedev.frostengine.bukkit.gameapi.exception.TeamAlreadyLoadedException;
import me.frostythedev.frostengine.bukkit.gameapi.teams.GameTeam;
import net.md_5.bungee.api.ChatColor;

public class TeamLoadUtility extends MinigameUtility<DeathmatchGame> {

    public TeamLoadUtility(DeathmatchGame game) {
        super(game);
    }

    @Override
    public void start() {
        try {
            getMinigame().getTeamManager().loadTeam(new GameTeam("Red", "&c[RED]", ChatColor.RED, ChatColor.GRAY));
            getMinigame().getTeamManager().loadTeam(new GameTeam("Blue", "&9[Blue]", ChatColor.BLUE, ChatColor.GRAY));
            getMinigame().getTeamManager().loadTeam(new GameTeam("Spectator", "&7[SPEC]", ChatColor.GRAY, ChatColor.DARK_GRAY));
        } catch (TeamAlreadyLoadedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void end() {

    }
}
