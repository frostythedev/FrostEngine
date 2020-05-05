package me.frostythedev.frostengine.modules.gameapi.kits;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.bukkit.player.PlayerUtil;
import me.frostythedev.frostengine.bukkit.utils.items.ArmorSlot;
import me.frostythedev.frostengine.data.JsonConvertable;
import me.frostythedev.frostengine.modules.gameapi.kits.interfaces.Kit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class GameKit implements Kit, JsonConvertable<GameKit> {

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

    public void setId(int id) {
        this.id = id;
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

    public void getKitFromPlayer(Player player){
        Map<ArmorSlot, ItemStack> armor = Maps.newHashMap();

        for(ArmorSlot armorSlot : ArmorSlot.values()){
            if(player.getInventory().getItem(armorSlot.getInventorySlot()) != null &&
                    !player.getInventory().getItem(armorSlot.getInventorySlot()).getType().equals(Material.AIR)){

                armor.put(armorSlot, player.getInventory().getItem(armorSlot.getInventorySlot()));
            }
        }

        setArmor(armor);
        setContents(player.getInventory().getContents());
    }

    public boolean getKitFromChest(Player player, Block block){
        if(player == null) return false;
        if(!block.getType().equals(Material.CHEST)){
            Locale.error(player, "&cThe block must be a chest.");
            return false;
        }

        Chest chest = (Chest) block.getState();
        if (!(chest.getInventory() instanceof DoubleChestInventory)) {
            Locale.error(player, "&cThe block must be a double chest.");
            return false;
        }

        Map<ArmorSlot, ItemStack> armor = Maps.newHashMap();

        Inventory inventory = chest.getInventory();
        if(inventory.getItem(0) != null){
            armor.put(ArmorSlot.HELMET, inventory.getItem(0));
        }
        if(inventory.getItem(1) != null){
            armor.put(ArmorSlot.CHEST_PLATE, inventory.getItem(1));
        }
        if(inventory.getItem(2) != null){
            armor.put(ArmorSlot.LEGGINGS, inventory.getItem(2));
        }
        if(inventory.getItem(3) != null){
            armor.put(ArmorSlot.BOOTS, inventory.getItem(3));
        }
        setArmor(armor);

        if(inventory.getItem(8) != null){
           setIcon(inventory.getItem(8));
        }

        ItemStack[] contents = new ItemStack[36];
        for(int i = 0; i < 36; i++){
            if(inventory.getItem((i+18)) != null){
                contents[i] = inventory.getItem((i+18));
            }
        }

        setContents(contents);
        Locale.success(player, "&aSuccessfully parsed kit from chest.");
        return true;
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
    public void giveKit(Player player) {
        PlayerUtil.setArmor(armor, player);
        player.getInventory().setContents(contents);
        player.updateInventory();
    }

    @Override
    public JsonObject serialize() {
        JsonObject jsonObject = new JsonObject();

        int id = getId();
        String kitName = getName();
        String displayName = getDisplayName();
        String permission = getPermission();
        ItemStack icon = getIcon();

        jsonObject.addProperty("id", id);
        jsonObject.addProperty("kitName", kitName);
        jsonObject.addProperty("displayName", displayName);
        jsonObject.addProperty("permission", permission);

        String iconData = "";
        if (icon != null) {
            iconData = FEPlugin.getGson().toJson(icon);
        }
        jsonObject.addProperty("icon", iconData);

        String armorData = armorToString();
        jsonObject.addProperty("armor", armorData);

        String contentsData = contentsToString();
        jsonObject.addProperty("contents", contentsData);

        return jsonObject;
        /*jsonObject.addProperty("id", getId());
        jsonObject.addProperty("name", getName());
        jsonObject.addProperty("displayName", getDisplayName());
        jsonObject.addProperty("permission", getPermission());
        jsonObject.addProperty("icon", FEPlugin.getGson().toJson(getIcon()));
        jsonObject.addProperty("armor", armorToString());
        jsonObject.addProperty("contents", contentsToString());*//*
        return jsonObject;*/
    }

    @Override
    public GameKit deserialize(JsonElement element) {
        JsonObject jsonObject = new JsonObject();
        int id = jsonObject.get("id").getAsInt();
        String kitName = jsonObject.get("kitName").getAsString();
        String displayName = jsonObject.get("displayName").getAsString();
        String permission = jsonObject.get("permission").getAsString();
        ItemStack stack = new ItemStack(Material.AIR);

        String iconData = jsonObject.get("icon").getAsString();
        if (!iconData.equals("")) {
            stack = FEPlugin.getGson().fromJson(iconData, ItemStack.class);
        }

        Map<ArmorSlot, ItemStack> armor = GameKit.armorFromString(jsonObject.get("armor").getAsString());
        ItemStack[] contents = GameKit.contentsFromString(jsonObject.get("contents").getAsString());

        setId(id);
        setName(kitName);
        setDisplayName(displayName);
        setPermission(permission);
        setArmor(armor);
        setContents(contents);
        setIcon(stack);
        return this;
        /*int id = jsonObject.get("id").getAsInt();
        String name = jsonObject.get("name").getAsString();
        String displayName = jsonObject.get("displayName").getAsString();
        String permission = jsonObject.get("permission").getAsString();
        ItemStack icon = FEPlugin.getGson().fromJson(jsonObject.get("icon").getAsString(), ItemStack.class);
        Map<ArmorSlot, ItemStack> armor = armorFromString(jsonObject.get("armor").getAsString());
        ItemStack[] contents = contentsFromString(jsonObject.get("contents").getAsString());

        GameKit kit = new GameKit(id, name, displayName, permission, armor, contents);
        kit.setIcon(icon);*/
    }
}
