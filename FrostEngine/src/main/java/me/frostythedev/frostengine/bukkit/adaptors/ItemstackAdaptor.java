package me.frostythedev.frostengine.bukkit.adaptors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.*;
import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import me.frostythedev.frostengine.data.JsonAdaptor;
import me.frostythedev.frostengine.bukkit.utils.items.ItemUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemstackAdaptor implements JsonAdaptor<ItemStack> {
    @Override
    public JsonElement serialize(ItemStack stack, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("item_id", stack.getTypeId());
        jsonObject.addProperty("amount", stack.getAmount());
        jsonObject.addProperty("data", stack.getData().getData());

        String name = "";
        if(ItemUtil.hasName(stack)){
            name = stack.getItemMeta().getDisplayName();
        }
        jsonObject.addProperty("name", name);

        String loreLine = "";
      if(ItemUtil.hasLore(stack)) {
          for (String line : stack.getItemMeta().getLore()) {
              if (loreLine.equals("")) {
                  loreLine += line;
              } else {
                  loreLine += ";" + line;
              }
          }
      }
        jsonObject.addProperty("lore", loreLine);

        String enchantsLine = "";
       if(ItemUtil.hasEnchantments(stack)){
           for(Map.Entry<Enchantment, Integer> entry : stack.getItemMeta().getEnchants().entrySet()){
              if(enchantsLine.equals("")){
                  enchantsLine+=entry.getKey().getName() + "#" + entry.getValue();
              }else{
                  enchantsLine+=";" + entry.getKey().getName() + "#" + entry.getValue();
              }
           }
       }
        jsonObject.addProperty("enchants", enchantsLine);

        return jsonObject;
    }

    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int item_id = jsonObject.get("item_id").getAsInt();
        int amount = jsonObject.get("amount").getAsInt();
        byte data = jsonObject.get("data").getAsByte();

        String name = jsonObject.get("name").getAsString();
        List<String> lore = Lists.newArrayList();

        String s = jsonObject.get("lore").getAsString();
        if(s.length() > 0){
            if(s.contains(";")){
                String[] lines = s.split(";");
                Arrays.stream(lines).forEach(lore::add);
            }else{
                lore.add(s);
            }
        }

        Map<Enchantment, Integer> enchantments = Maps.newHashMap();

        String ss = jsonObject.get("enchants").getAsString();
        if(ss.length() > 0){
            if(ss.contains(";")){
                String[] lines = ss.split(";");
                for(String enchantData : lines){
                    Enchantment enchantment = Enchantment.getByName(enchantData.split("#")[0]);
                    int level = Integer.valueOf(enchantData.split("#")[1]);
                    enchantments.put(enchantment, level);
                }
            }else{
                Enchantment enchantment = Enchantment.getByName(ss.split("#")[0]);
                int level = Integer.valueOf(s.split("#")[1]);
                enchantments.put(enchantment, level);
            }
        }

        return new ItemBuilder(Material.getMaterial(item_id))
                .withQuantity(amount)
                .withData(data)
                .withCustomName(name)
                .withLore(lore)
                //.includeEnchantment(enchantments)
                .build();
    }
}
