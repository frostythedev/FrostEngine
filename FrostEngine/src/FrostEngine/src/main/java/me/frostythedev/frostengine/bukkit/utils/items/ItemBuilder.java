package me.frostythedev.frostengine.bukkit.utils.items;

import com.google.common.collect.ImmutableList;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.frostythedev.frostengine.bukkit.utils.StringUtil;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;
import org.bukkit.potion.Potion;

import java.lang.reflect.Field;
import java.util.*;


public class ItemBuilder {

    private Material material = null;
    private int amount = 1;
    private short data = 0;
    private String customName = null;
    private List<String> lore = null;
    private String headOwner = null;
    private Color leatherColor = null;
    private PotionMeta potionMeta = null;
    private boolean glowing = false;
    private List<ItemFlag> itemFlags = new ArrayList<>();
    private Map<Enchantment, Integer> enchantments = new HashMap<>();
//    private Map<EnumCustomEnchant, Integer> customEnchantments = new HashMap<>();
    private Map<String, NBTBase> nbtTags = new HashMap<>();
    private String skinURL = null;

    public ItemBuilder(ItemStack item) {
        this.material = item.getType();
        this.amount = item.getAmount();

        if (ItemUtil.hasName(item)) {
            this.customName = ItemUtil.getItemName(item);
        }
        if (ItemUtil.hasLore(item)) {
            this.lore = item.getItemMeta().getLore();
        }
        if (ItemUtil.hasData(item)) {
            this.data = item.getDurability();
        }
        if (ItemUtil.hasEnchants(item)) {
            this.enchantments = item.getEnchantments();
        }
        if (ItemUtil.hasHeadOwner(item)) {
            this.headOwner = ((SkullMeta) item.getItemMeta()).getOwner();
        }

        if (ItemUtil.hasLeatherColor(item)) {
            LeatherArmorMeta m = (LeatherArmorMeta) item.getItemMeta();
            this.leatherColor = m.getColor();
        }

        NBTTagCompound base = new NBTTagCompound();
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        if (nmsStack != null) {
            nmsStack.save(base);

            if (base.hasKey("tag")) {
                NBTTagCompound tagCompound = base.getCompound("tag");
                for (String t : tagCompound.c()) {
                    if (!t.equalsIgnoreCase("display") && !t.equalsIgnoreCase("ench")) {
                        this.nbtTags.put(t, tagCompound.get(t));
                    }
                }
            }
        }
    }

    public ItemBuilder(Potion potion) {
        this(potion.toItemStack(1));
    }

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder(DyeColor dye) {
        Dye dyeItem = new Dye();
        dyeItem.setColor(dye);
        ItemStack is = dyeItem.toItemStack();
        this.material = is.getType();
        this.data = (byte) is.getDurability();
    }

    public ItemBuilder arenaItem() {
        this.nbtTags.put("arena-items", new NBTTagByte((byte) 0));
        return this;
    }

    public ItemBuilder basedOn(PotionMeta meta) {
        this.potionMeta = meta;
        return this;
    }

    public ItemBuilder asColor(Color color) {
        this.leatherColor = color;
        return this;
    }

    public ItemBuilder withLore(String... lore) {
        this.lore = ImmutableList.copyOf(lore);
        return this;
    }

    public ItemBuilder withLore(Iterable<String> lore) {
        this.lore = ImmutableList.copyOf(lore);
        return this;
    }

    public ItemBuilder ofType(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder makeItGlow() {
        this.glowing = true;
        return this;
    }

    public ItemBuilder withSkinURL(String skinURL) {
        this.skinURL = skinURL;
        return this;
    }

    /*public ItemBuilder includeCustomEnchantment(EnumCustomEnchant enumCustomEnchant, int level) {
        if (enumCustomEnchant == null) {
            return this;
        }
        if (customEnchantments == null) {
            customEnchantments = new HashMap<>();
        }
        customEnchantments.put(enumCustomEnchant, level);
        return this;
    }*/

    public ItemBuilder includeEnchantment(Enchantment enchant, int level) {
        if (enchant == null)
            return this;
        if (enchantments == null)
            enchantments = new HashMap<>();
        enchantments.put(enchant, level);
        return this;
    }

   /* public ItemBuilder includeCustomEnchantments(Map<EnumCustomEnchant, Integer> customEnchants) {
        if (this.customEnchantments == null) {
            this.customEnchantments = customEnchants;
        } else {
            for (Map.Entry entry : customEnchants.entrySet()) {
                this.customEnchantments.put((EnumCustomEnchant) entry.getKey(), (int) entry.getValue());
            }
        }
        return this;
    }*/

    public ItemBuilder includeEnchantments(Map<Enchantment, Integer> enchants) {
        if (enchantments == null)
            enchantments = enchants;
        else {
            for (Map.Entry<Enchantment, Integer> set : enchants.entrySet())
                enchantments.put(set.getKey(), set.getValue());
        }
        return this;
    }

    @Deprecated
    public ItemBuilder asHeadOwner(String name) {
        this.headOwner = name;
        return this;
    }


    public ItemBuilder withLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder withCustomName(String customName) {
        this.customName = customName;
        return this;
    }

    public ItemBuilder dyed(DyeColor color) {
        this.data = color.getData();
        return this;
    }

    public ItemBuilder withData(short data) {
        this.data = data;
        return this;
    }

    public ItemBuilder withData(byte data) {
        this.data = data;
        return this;
    }

    public ItemBuilder withQuantity(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder flagWith(ItemFlag flag) {
        if (!this.itemFlags.contains(flag)) {
            this.itemFlags.add(flag);
        }
        return this;
    }

    public ItemBuilder withNBTTag(String name, NBTBase tag) {
        this.nbtTags.put(name, tag);
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(this.material, this.amount, this.data);
        if (this.customName != null || this.lore != null || this.headOwner != null || this.leatherColor != null || this.potionMeta != null) {
            ItemMeta meta = item.getItemMeta();
            if (this.potionMeta != null && this.material == Material.POTION) {
                meta = this.potionMeta;
            }
            if (headOwner != null && this.material == Material.SKULL_ITEM && (meta instanceof SkullMeta)) {
                SkullMeta m = (SkullMeta) meta;
                m.setOwner(headOwner);
                meta = m;
            }

            if (this.skinURL != null && !this.skinURL.isEmpty() && this.material.equals(Material.SKULL_ITEM) && (meta instanceof SkullMeta)) {
                SkullMeta m = (SkullMeta) meta;
                GameProfile profile = new GameProfile(UUID.randomUUID(), null);
                byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", this.skinURL).getBytes());
                profile.getProperties().put("textures", new Property("textures", new String(encodedData)));

                try {
                    Field profileField = m.getClass().getDeclaredField("profile");
                    profileField.setAccessible(true);
                    profileField.set(m, profile);
                } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                meta = m;
            }

            if (this.leatherColor != null && meta instanceof LeatherArmorMeta) {
                LeatherArmorMeta m = (LeatherArmorMeta) meta;
                m.setColor(this.leatherColor);
                meta = m;
            }

            if (this.customName != null) {
                meta.setDisplayName(StringUtil.formatColorCodes(this.customName));
            }
            if (this.lore != null) {
                meta.setLore(StringUtil.formatColorCodes(this.lore));
            }
            if (!this.itemFlags.isEmpty()) {
                for (ItemFlag flag : this.itemFlags) {
                    meta.addItemFlags(flag);
                }
            }

            item.setItemMeta(meta);
        }

        if (this.enchantments != null) {
            for (Map.Entry<Enchantment, Integer> entry : this.enchantments.entrySet()) {
                item.addUnsafeEnchantment(entry.getKey(), entry.getValue());
            }
        }
        /*if (this.customEnchantments != null) {
            for (Map.Entry entry : this.customEnchantments.entrySet()) {
                EnumCustomEnchant enchant = (EnumCustomEnchant) entry.getKey();
                int level = (int) entry.getValue();
                Enchantment enchantment = null;
                for (Enchantment e : Enchantment.values()) {
                    if (e == null || e.getName() == null)
                        continue;
                    if (e.getName().equalsIgnoreCase(enchant.getName())) {
                        enchantment = e;
                        break;
                    }
                }
                if (enchantment != null) {
                    items = CustomEnchantManager.addEnchantIgnoreSlot(items, enchantment, level);
                }
            }
        }*/

        if (item.getEnchantments().isEmpty() && this.glowing) {
            ItemUtil.addGlow(item);
        }

        if (!this.nbtTags.isEmpty()) {
            ItemUtil.addTags(item, nbtTags);
        }

        return item;
    }

}