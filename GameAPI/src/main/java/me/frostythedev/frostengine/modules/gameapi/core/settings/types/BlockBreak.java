package me.frostythedev.frostengine.modules.gameapi.core.settings.types;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import me.frostythedev.frostengine.modules.gameapi.core.Setting;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak extends Setting<BlockBreakEvent> {

    public BlockBreak(Game minigame, String name, boolean enabled) {
        super(minigame, name, enabled);
        setIcon(new ItemBuilder(Material.WOOD).build());
    }

    @EventHandler
    public void onAction(BlockBreakEvent event) {
        if (!isEnabled()) {


            //Checks to ensure event respects qualities of a minigame, and disallows spectators
            if(getMinigame().getGameArena() != null){
                if(getMinigame().getGameArena().isBreakable(event.getBlock())){

                    if(getMinigame().getTeamManager().hasTeam(event.getPlayer())){
                        if(getMinigame().getTeamManager().getPlayerTeam(event.getPlayer()).get()
                                .getName().equalsIgnoreCase("Spectator")){
                            event.setCancelled(true);
                        }
                    }

                }else{
                    event.setCancelled(true);
                }
            }else{
                event.setCancelled(true);
            }

            if(event.isCancelled()) {
                getMinigame().getSettingManager().get("Notify").ifPresent(
                        s -> {
                            if (s.isEnabled()) {
                                Locale.messagef(event.getPlayer(), "&4&l>> &cYou are not allowed to %s!", getName().toLowerCase());
                            }
                        }
                );
            }
        }
    }
}
