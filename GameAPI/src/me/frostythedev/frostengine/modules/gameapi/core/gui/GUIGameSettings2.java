package me.frostythedev.frostengine.modules.gameapi.core.gui;

import fr.minuskube.inv.ClickableButton;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.MultiStateItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import me.frostythedev.frostengine.modules.gameapi.ModuleGameAPI;
import me.frostythedev.frostengine.modules.gameapi.core.GameSettings;
import me.frostythedev.frostengine.modules.gameapi.core.Setting;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GUIGameSettings2 {

    private SmartInventory settingsInv;

    public GUIGameSettings2(Minigame minigame) {
        settingsInv = SmartInventory.builder()
                .id("gui_" + minigame.getName().toLowerCase())
                .size(6, 9)
                .title("Game Settings: " + minigame.getName())
                .closeable(true)
                .provider(new SettingsProvider(minigame))
                .build()
        ;
    }

    public SmartInventory getSettingsGUI() {
        return this.settingsInv;
    }

    private class SettingsProvider implements InventoryProvider {

        private Minigame mgProvider;
        private ClickableButton[] buttons;

        private final int[][] available_slots = {
                {2, 1}, {2, 2}, {2, 3}, {2, 4}, {2, 5}, {2, 6}, {2, 7},
                {3, 1}, {3, 2}, {3, 3}, {3, 4}, {3, 5}, {3, 6}, {3, 7},
                {4, 1}, {4, 2}, {4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7},
        };

        public SettingsProvider(Minigame mgProvider) {
            this.mgProvider = mgProvider;
            buttons = new ClickableButton[14];
            initButtons();
        }

        public void initButtons() {

            int counter = 0;
            if(mgProvider == null){
                System.out.println("MGProvider is null");
            }

            if(mgProvider.getSettingManager() == null){
                System.out.println("MGProvider Setting Manager is null");
            }

            if(mgProvider.getSettingManager().getAll() == null){
                System.out.println("MGProvider Settings Collection is null");
            }

            if(mgProvider.getSettingManager().getAll().isEmpty()){
                System.out.println("MGProvider Settings Collection is empty");
            }

            for (Setting s : mgProvider.getSettingManager().getAll()) {
                ClickableButton cb1 = ClickableButton.of(s.getCompleteIcon(), event -> {
                    if (s.isEnabled()) {
                        s.setEnabled(false);
                    } else {
                        s.setEnabled(true);
                    }
                    Locale.messagef(event.getWhoClicked(), "&2&l>> &a%s has been toggled to: %b", s.getName(), s.isEnabled());
                }, s.getCompleteIconOpposite(), event -> {
                    if (s.isEnabled()) {
                        s.setEnabled(false);
                    } else {
                        s.setEnabled(true);
                    }
                    Locale.messagef(event.getWhoClicked(), "&2&l>> &a%s has been toggled to: %b", s.getName(), s.isEnabled());
                });
                buttons[counter] = cb1;
                counter++;
            }

               /* notify = ClickableButton.of(new ItemBuilder(GameSettings.ENABLED)
                                .withCustomName("&aNotify")
                                .withLore("&7Should players be notified when",
                                        "&7specific game events occur?")
                                .build(), event -> {
                            mgProvider.getGameSettings().setNotify(false);
                            Locale.success(event.getWhoClicked(), "Notify has been turned off");
                        }
                ,
                        new ItemBuilder(GameSettings.DISABLED)
                                .withCustomName("&cNotify")
                                .withLore("&7Should players be notified when",
                                        "&7specific game events occur?")
                                .build(), event -> {
                            mgProvider.getGameSettings().setNotify(true);
                            Locale.success(event.getWhoClicked(), "Notify has been turned on!");
                        } );

            }

            {

                chat = ClickableButton.of(new ItemBuilder(GameSettings.ENABLED)
                                .withCustomName("&aChat")
                                .withLore("&7Should players be allowed to",
                                        "&7talk in chat?")
                                .build(), event -> {
                            mgProvider.getGameSettings().setChat(false);
                            Locale.success(event.getWhoClicked(), "Chat has been turned off");
                        }
                        ,
                        new ItemBuilder(GameSettings.DISABLED)
                                .withCustomName("&cChat")
                                .withLore("&7Should players be allowed to",
                                        "&7talk in chat?")
                                .build(), event -> {
                            mgProvider.getGameSettings().setChat(true);
                            Locale.success(event.getWhoClicked(), "Chat has been turned on!");
                        });*/

                /*chat = MultiStateItem.empty();
                chat.addDefaultState(new ItemBuilder(GameSettings.ENABLED)
                        .withCustomName("&aChat")
                        .withLore("&7Should players be allowed to",
                                "&7talk in chat?")
                        .build(), event -> {
                    mgProvider.getGameSettings().setChat(false);
                    Locale.success(event.getWhoClicked(), "Chat has been turned off");
                });

                chat.addState(1, new ItemBuilder(GameSettings.DISABLED)
                        .withCustomName("&cChat")
                        .withLore("&7Should players be allowed to",
                                "&7talk in chat?")
                        .build(), event -> {
                    mgProvider.getGameSettings().setChat(true);
                    Locale.success(event.getWhoClicked(), "Chat has been turned on!");
                });*/
        }


        @Override
        public void init(Player player, InventoryContents invC) {
            //invC.property("update-ticks", 0);

            invC.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1)));

            for (int i = 0; i < buttons.length; i++) {
                invC.set(available_slots[i][0], available_slots[i][1], buttons[i]);
            }
        }

        @Override
        public void update(Player player, InventoryContents contents) {
            /*int state = contents.property("update-ticks");
            contents.setProperty("update-ticks", state+1);
            if(state % 20 != 0){
                return;
            }*/

            for (int[] available_slot : available_slots) {
                if (contents.get(available_slot[0], available_slot[1]).isPresent()) {
                    contents.set(available_slot[0], available_slot[1],
                            contents.get(available_slot[0], available_slot[1]).get());
                }

            }

           /* if(contents.get(2,2).isPresent()){
                contents.set(2,2, contents.get(2,2).get());
            }
            if(contents.get(2,3).isPresent()){
                contents.set(2,3, contents.get(2,3).get());
            }*/
        }
    }
}
