package me.frostythedev.frostengine.modules.chat;

import me.frostythedev.frostengine.bukkit.event.api.AbstractListener;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.module.ModuleAPI;
import me.frostythedev.frostengine.modules.ranks.ModuleRanks;
import me.frostythedev.frostengine.modules.ranks.objects.RankPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Programmed by Tevin on 8/5/2016.
 */
public class ModuleChat extends Module {

    private static ModuleChat inst;

    public static ModuleChat get() {
        return inst;
    }

    public ModuleChat() {
        super("Chat", "Chat functionality", "1.0.0", "frostythedev");
    }

    @Override
    public void onModuleEnable() {
        inst = this;

        this.saveDefaultConfig();

        this.addListener(new AbstractListener() {

            @EventHandler
            public void onChat(AsyncPlayerChatEvent event) {
                Player player = event.getPlayer();

                if(ModuleAPI.isLoaded("Ranks")){

                    ModuleRanks moduleRanks = (ModuleRanks) ModuleAPI.getModule("Ranks");

                    if(moduleRanks.getPlayerManager().isLoaded(player)){
                        RankPlayer rankPlayer = moduleRanks.getPlayerManager().getPlayer(player);

                        event.setFormat(rankPlayer.getHigestRank().chat(player, event.getMessage()));
                    }
                }
            }
        });


    }

}
