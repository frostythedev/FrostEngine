package gameapi.kits;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.item.ArmorSlot;
import me.frostythedev.frostengine.bukkit.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class GameKit implements Kit {

    private int id;
    private String name;
    private String displayName;
    private String permission;
    private ItemStack icon;

    private Map<ArmorSlot, ItemStack> armor;
    private ItemStack[] contents;

    public GameKit(int id, String name, String displayName) {
        this(id, name, displayName, "", Maps.newHashMap(), new ItemStack[0]);
    }

    public GameKit(int id, String name, String displayName, String permission) {
        this(id, name, displayName, permission, Maps.newHashMap(), new ItemStack[0]);
    }

    public GameKit(int id, String name, String displayName, String permission, ItemStack[] contents) {
        this(id, name, displayName, permission, Maps.newHashMap(), contents);
    }

    public GameKit(int id, String name, String displayName, String permission, Map<ArmorSlot, ItemStack> armor, ItemStack[] contents) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.permission = permission;
        this.armor = armor;
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public Map<ArmorSlot, ItemStack> getArmor() {
        return armor;
    }

    public void setArmor(Map<ArmorSlot, ItemStack> armor) {
        this.armor = armor;
    }

    public ItemStack[] getContents() {
        return contents;
    }

    public void setContents(ItemStack[] contents) {
        this.contents = contents;
    }

    public String armorToString() {
        String mapData = "";
        if (armor != null && !armor.isEmpty()) {
            for (Map.Entry<ArmorSlot, ItemStack> entry : armor.entrySet()) {
                if (mapData.equals("")) {
                    mapData += entry.getKey().toString() + ";" + FEPlugin.getGson().toJson(entry.getValue());
                } else {
                    mapData += "#" + entry.getKey().toString() + ";" + FEPlugin.getGson().toJson(entry.getValue());
                }
            }
        }
        return mapData;
    }

    public String contentsToString() {
        String contentsData = "";
        if (contents.length > 0) {
            for (ItemStack stack : contents) {
                String stackData = FEPlugin.getGson().toJson(stack);
                if (contentsData.equals("")) {
                    contentsData += stackData;
                } else {
                    contentsData += ";" + stackData;
                }
            }
        }
        return contentsData;
    }

    public static ItemStack[] contentsFromString(String data) {
        ItemStack[] contents;

        if (!data.equals("")) {
            if (data.contains(";")) {
                contents = new ItemStack[data.split(";").length];
            } else {
                contents = new ItemStack[1];
                ItemStack stack = FEPlugin.getGson().fromJson(data, ItemStack.class);
                contents[0] = stack;
                return contents;
            }

            String[] array = data.split(";");
            int index = 0;
            for (String str : array) {
                ItemStack stack = FEPlugin.getGson().fromJson(str, ItemStack.class);
                contents[index] = stack;
                index++;
            }
            return contents;

        } else {
            return new ItemStack[0];
        }
    }

    public static Map<ArmorSlot, ItemStack> armorFromString(String data) {
        Map<ArmorSlot, ItemStack> armor = Maps.newHashMap();

        if (data.contains("#")) {
            String[] parts = data.split("#");
            for (String str : parts) {
                String[] array = str.split(";");
                ArmorSlot slot = ArmorSlot.valueOf(array[0]);
                ItemStack stack = FEPlugin.getGson().fromJson(array[1], ItemStack.class);
                armor.put(slot, stack);
            }
            return armor;
        } else {
            String[] array = data.split(";");
            ArmorSlot slot = ArmorSlot.valueOf(array[0]);
            ItemStack stack = FEPlugin.getGson().fromJson(array[1], ItemStack.class);
            armor.put(slot, stack);
            return armor;
        }
    }

    @Override
    public void save() {

    }

    @Override
    public void giveKit(Player player) {
        Players.setArmor(armor, player);
        player.getInventory().setContents(contents);
        player.updateInventory();
    }
}
