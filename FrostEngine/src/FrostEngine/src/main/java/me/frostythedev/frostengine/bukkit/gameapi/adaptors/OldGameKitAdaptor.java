package me.frostythedev.frostengine.bukkit.gameapi.adaptors;

import com.google.gson.*;
import me.frostythedev.frostengine.data.JsonAdaptor;
import me.frostythedev.frostengine.bukkit.gameapi.kits.GameKit;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.utils.items.ArmorSlot;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.Map;

public class OldGameKitAdaptor implements JsonAdaptor<GameKit> {

    @Override
    public GameKit deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
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

        GameKit gameKit = new GameKit(id, kitName, displayName, permission, armor, contents);
        gameKit.setIcon(stack);

        return gameKit;
    }

    @Override
    public JsonElement serialize(GameKit gameKit, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        int id = gameKit.getId();
        String kitName = gameKit.getName();
        String displayName = gameKit.getDisplayName();
        String permission = gameKit.getPermission();
        ItemStack icon = gameKit.getIcon();

        jsonObject.addProperty("id", id);
        jsonObject.addProperty("kitName", kitName);
        jsonObject.addProperty("displayName", displayName);
        jsonObject.addProperty("permission", permission);

        String iconData = "";
        if (icon != null) {
            iconData = FEPlugin.getGson().toJson(icon);
        }
        jsonObject.addProperty("icon", iconData);

        String armorData = gameKit.armorToString();
        jsonObject.addProperty("armor", armorData);

        String contentsData = gameKit.contentsToString();
        jsonObject.addProperty("contents", contentsData);

        return jsonObject;
    }
}
