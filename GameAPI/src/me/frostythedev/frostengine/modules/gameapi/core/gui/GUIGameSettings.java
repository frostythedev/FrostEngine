package me.frostythedev.frostengine.modules.gameapi.core.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import me.frostythedev.frostengine.modules.gameapi.core.GameSettings;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GUIGameSettings {

    public SmartInventory getSettingsGUI(Minigame minigame){
        return SmartInventory.builder()
                .id("gui_"  + minigame.getName().toLowerCase())
                .size(6,9)
                .title("Game Settings: " + minigame.getName())
                .closeable(true)
                .provider(null)
                .build()
                ;
    }

    public class SettingsProvider implements InventoryProvider {

        private Minigame mgProvider;

        public SettingsProvider(Minigame mgProvider) {
            this.mgProvider = mgProvider;
        }

        @Override
        public void init(Player player, InventoryContents invC) {
                invC.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE,1)));

                boolean multiPage = false;


                invC.set(3, 3,ClickableItem.of((mgProvider.getGameSettings().isNotify() ?

                        new ItemBuilder(GameSettings.ENABLED)
                                .withCustomName("&aNotify")
                                .withLore("&7Should players be notified when",
                                        "specific game events occur?")
                        .build()

                        : new ItemBuilder(GameSettings.DISABLED)
                        .withCustomName("&cNotify")
                        .withLore("&7Should players be notified when",
                                "specific game events occur?")
                        .build()


                ), e -> {

                    if(e.isLeftClick()){
                        if(mgProvider.getGameSettings().isNotify()){
                            mgProvider.getGameSettings().setNotify(false);

                        }else if(!mgProvider.getGameSettings().isNotify()){
                            mgProvider.getGameSettings().setNotify(true);
                        }
                    }
                }));
        }

        @Override
        public void update(Player player, InventoryContents invC) {
            invC.set(3, 3,ClickableItem.of((mgProvider.getGameSettings().isNotify() ?

                    new ItemBuilder(GameSettings.ENABLED)
                            .withCustomName("&aNotify")
                            .withLore("&7Should players be notified when",
                                    "specific game events occur?")
                            .build()

                    : new ItemBuilder(GameSettings.DISABLED)
                    .withCustomName("&cNotify")
                    .withLore("&7Should players be notified when",
                            "specific game events occur?")
                    .build()


            ), e -> {

                if(e.isLeftClick()){
                    if(mgProvider.getGameSettings().isNotify()){
                        mgProvider.getGameSettings().setNotify(false);

                    }else if(!mgProvider.getGameSettings().isNotify()){
                        mgProvider.getGameSettings().setNotify(true);
                    }
                }
            }));
        }
    }
}
