package me.frostythedev.frostengine.bukkit.utils.items.customitems;

import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.utils.ux.HUDUtils;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.utils.items.ItemUtil;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class CustomItemListener implements Listener {

    public static String INTERACTION_DELAY_META = "interactiondelay";

    @Inject
    FEPlugin plugin;

    public  void updateSpecialItemCooldown(Player player) {
        player.setMetadata(INTERACTION_DELAY_META, new FixedMetadataValue(plugin, System.currentTimeMillis()));
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();


        for (CustomItem customItem : CustomItemManager.getInstance().getCustomItems()) {
            if (!customItem.getRegisteredActions().isEmpty() && customItem.getTargetNBT() != null) {
                ItemStack hand = player.getItemInHand();
                if (hand != null) {
                    NBTBase nbt = ItemUtil.getTag(hand, customItem.getTargetNBT());
                    if (nbt != null) {

                        if (!customItem.getRegisteredActions().contains(event.getAction())) {
                            event.setCancelled(true);
                            return;
                        }

                        if (customItem.isUseCooldown()) {
                            if (isUsingTooFast(player)) {
                                event.setCancelled(true);
                                return;
                            }
                            updateSpecialItemCooldown(player);
                        }

                        if (customItem.handleInteraction(player, hand, nbt, event)) {
                            event.setCancelled(true);
                            return;
                        } else {
                            break;
                        }
                    }

                }
            }
        }
    }

    private boolean isUsingTooFast(Player player) {
        if (player.hasMetadata(INTERACTION_DELAY_META)) {

            long unix = player.getMetadata(INTERACTION_DELAY_META).get(0).asLong();
            long diff = System.currentTimeMillis() - unix;
            if (diff < 1500) {
                player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 0);
                HUDUtils.sendActionBar(player, "§cWait before using that again: §f" + (1500 - diff) + "ms");
                return true;
            }

        }
        return false;
    }
}
