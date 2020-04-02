package me.frostythedev.frostengine.legacy.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;

public class ItemBuilder
{
    private final ItemStack ITEM_STACK;

    public ItemBuilder(Material mat)
    {
        this.ITEM_STACK = new ItemStack(mat);
    }

    public ItemBuilder(ItemStack item)
    {
        this.ITEM_STACK = item;
    }

    public ItemBuilder withAmount(int amount)
    {
        this.ITEM_STACK.setAmount(amount);
        return this;
    }

    public ItemBuilder withName(String name)
    {
        ItemMeta meta = this.ITEM_STACK.getItemMeta();
        meta.setDisplayName(ColorUtils.toColors(name));
        this.ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemBuilder withLore(String name)
    {
        ItemMeta meta = this.ITEM_STACK.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList();
        }
        lore.add(ColorUtils.toColors(name));
        meta.setLore(lore);
        this.ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemBuilder withDurability(int durability)
    {
        this.ITEM_STACK.setDurability((short)durability);
        return this;
    }

    public ItemBuilder withData(int data)
    {
        this.ITEM_STACK.setData(new MaterialData(this.ITEM_STACK.getType(), (byte)data));
        return this;
    }

    public ItemBuilder withEnchantment(Enchantment enchantment, int level)
    {
        this.ITEM_STACK.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder withEnchantment(Enchantment enchantment)
    {
        this.ITEM_STACK.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemBuilder withType(Material material)
    {
        this.ITEM_STACK.setType(material);
        return this;
    }

    public ItemBuilder clearLore()
    {
        ItemMeta meta = this.ITEM_STACK.getItemMeta();
        meta.setLore(new ArrayList());
        this.ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemBuilder clearEnchantments()
    {
        for (Enchantment enchantment : this.ITEM_STACK.getEnchantments().keySet()) {
            this.ITEM_STACK.removeEnchantment(enchantment);
        }
        return this;
    }

    public ItemBuilder withColor(Color color)
    {
        Material type = this.ITEM_STACK.getType();
        if ((type == Material.LEATHER_BOOTS) || (type == Material.LEATHER_CHESTPLATE) || (type == Material.LEATHER_HELMET) || (type == Material.LEATHER_LEGGINGS))
        {
            LeatherArmorMeta meta = (LeatherArmorMeta)this.ITEM_STACK.getItemMeta();
            meta.setColor(color);
            this.ITEM_STACK.setItemMeta(meta);
            return this;
        }
        throw new IllegalArgumentException("withColor is only applicable for leather armor!");
    }

    public ItemStack build()
    {
        return this.ITEM_STACK;
    }
}
