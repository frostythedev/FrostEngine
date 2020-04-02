package me.frostythedev.frostengine.bukkit.utils.items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.bukkit.potion.PotionEffectType.*;

public class ItemUtil {

    public static final PotionEffectType[] POSITIVE_POTIONS = {
            SPEED, FAST_DIGGING, INCREASE_DAMAGE, HEAL, JUMP,
            REGENERATION, DAMAGE_RESISTANCE, FIRE_RESISTANCE, WATER_BREATHING,
            INVISIBILITY, NIGHT_VISION, HEALTH_BOOST, ABSORPTION, SATURATION
    };

    public static Field stackHandle;


    /**
     * Refreshes the display lore of an items to represent the proper amount of soulbound lives.
     *
     * @param itemStack the itemstack to update
     * @return the updated itemStack
     */
    public static ItemStack updateSoulprotectionLore(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore;
        if (meta.getLore() != null && !meta.getLore().isEmpty()) {
            lore = meta.getLore();
        } else {
            lore = new ArrayList<>();
        }

        lore.removeIf(line -> line.startsWith("§d§lSoulbound"));

        if (isSoulProtected(itemStack)) {
            int charges = getSoulProtectedCharges(itemStack);
            if (charges > 0) {
                lore.add("§d§lSoulbound: §f§l" + charges + " " + (charges == 1 ? "Life" : "Lives"));
            }
        }
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Determines if an items is invalid or duplicated
     * @param itemStack is the items to check
     * @return true if the items should be removed from player inventories
     */
    public static boolean isInvalidItem(ItemStack itemStack) {
        if (itemStack != null && itemStack.getType().equals(Material.WOOD_SWORD) && itemStack.getEnchantmentLevel(Enchantment.DAMAGE_ALL) >= 500) {
            if (ItemUtil.getTag(itemStack, "kosword") != null && ItemUtil.getTag(itemStack, "koswordnew") == null) {
                return true;
            }
        }

        if (itemStack != null &&
                (itemStack.getAmount() <= 0 || itemStack.getType() == Material.BEDROCK || itemStack.getType() == Material.BOOK_AND_QUILL || ItemUtil.getTag(itemStack, "mw-items") != null ||
                        ItemUtil.getTag(itemStack, "arena-items") != null ||
                        (itemStack.getType().equals(Material.ANVIL) || itemStack.getType().equals(Material.BREWING_STAND_ITEM) || itemStack.getType().equals(Material.BARRIER)))) {
            return true;
        }
        return false;
    }

    /**
     * Determines if an items has a custom name
     * @param item is the itemStack to check
     * @return true if the items has a custom items name
     */
    public static boolean hasName(ItemStack item) {
        return hasMetaData(item) && item.getItemMeta().getDisplayName() != null;
    }

    /**
     * Determines if an items has soulbound lives
     * @param itemStack the itemstack to check
     * @return true if an items has soulbound lives
     */
    public static boolean isSoulProtected(ItemStack itemStack) {
        return getTag(itemStack, "soulprotected") != null;
    }

    /**
     * Removes the NBT tag from an items giving it soulbound lives
     * @param itemStack the itemstack to remove the protection from
     * @return the itemstack without the NBT
     */
    public static ItemStack removeSoulProtection(ItemStack itemStack) {
        if (isSoulProtected(itemStack)) {
            removeTag(itemStack, "soulprotected");
        }
        return itemStack;
    }

    /**
     * Gets the amount of soulbound lives on an items
     * @param itemStack the itemstack to check
     * @return the amount of remaining lives, or 0 if not present
     */
    public static int getSoulProtectedCharges(ItemStack itemStack) {
        if (isSoulProtected(itemStack)) {
            NBTTagInt i = (NBTTagInt) getTag(itemStack, "soulprotected");
            assert i != null;
            return i.d();
        }
        return 0;
    }

    /**
     * Updates the NBT on an items to reflect the given amount of soulbound lives. Call {@link #updateSoulprotectionLore(ItemStack)} to update the lore
     * @param itemStack the itemstack to set the lives of
     * @param amount the amount of lives
     * @return the newly protected itemStack
     */
    public static ItemStack setSoulprotectedCharges(ItemStack itemStack, int amount) {
        return addTag(itemStack, "soulprotected", new NBTTagInt(amount));
    }

    /**
     * Determines if there's any items meta present
     * @param item the items to check
     * @return true if there's ItemMeta defined
     */
    public static boolean hasMetaData(ItemStack item) {
        return item != null && item.getItemMeta() != null;
    }

    /**
     * Determines if an items has lore
     * @param item the items to check
     * @return true if there's lore
     */
    public static boolean hasLore(ItemStack item) {
        return hasMetaData(item) && item.getItemMeta().getLore() != null;
    }

    /**
     * Determines if an items has any specific data
     * @param item the items to check
     * @return true if they items's durability is not 0
     */
    public static boolean hasData(ItemStack item) {
        return item.getData() != null && item.getDurability() != 0;
    }

    /**
     * Determines if an items is enchanted, including {@link com.turqmelon.SkyblockAbilities.utils.EnchantGlow}
     * @param item the items to check
     * @return if an items has at least 1 enchantment
     */
    public static boolean hasEnchants(ItemStack item) {
        return item.getEnchantments() != null && !item.getEnchantments().isEmpty();
    }

    /**
     * Determines if a skull items has a head owner defined
     * @param item the items to check
     * @return true if a head owner is defined
     */
    public static boolean hasHeadOwner(ItemStack item) {
        return hasMetaData(item) && item.getItemMeta() instanceof SkullMeta && ((SkullMeta) item.getItemMeta()).getOwner() != null && !((SkullMeta) item.getItemMeta()).getOwner().equals("");
    }

    /**
     * Determines if leather armor has a color defined
     * @param item the items to check
     * @return true if there's a color set
     */
    public static boolean hasLeatherColor(ItemStack item) {
        return hasMetaData(item) && item.getItemMeta() instanceof LeatherArmorMeta;
    }

    /**
     * Determines if a potion effect is good
     * @param type the PotionEffectType to check
     * @return true if it's a good potion
     */
    public static boolean isPositive(PotionEffectType type) {
        return Arrays.asList(POSITIVE_POTIONS).contains(type);
    }

    /**
     * Returns a more player-known name for a {@link PotionEffectType}
     * @param type the potion effect to get the name of
     * @return the friendly name
     */
    public static String getPotionName(PotionEffectType type) {
        if (type.getName().equals(PotionEffectType.FAST_DIGGING.getName())) {
            return "Haste";
        } else if (type.getName().equals(PotionEffectType.SLOW_DIGGING.getName())) {
            return "Mining Fatigue";
        } else if (type.getName().equals(PotionEffectType.INCREASE_DAMAGE.getName())) {
            return "Strength";
        } else if (type.getName().equals(PotionEffectType.DAMAGE_RESISTANCE.getName())) {
            return "Resistance";
        } else if (type.getName().equals(PotionEffectType.SPEED.getName())) {
            return "Swiftness";
        }
        return WordUtils.capitalizeFully(type.getName().replace("_", " "));
    }

    /**
     * Returns a more player-known name for an {@link EnchantmentTarget}
     * @param target the enchant target to get the name of
     * @return the friendly name
     */
    public static String getFriendlyEnchantmentTargetName(EnchantmentTarget target) {
        if (target == null) {
            return "Any Item";
        }
        switch (target) {
            case ALL:
                return "Any Item";
            case ARMOR:
                return "Armor";
            case ARMOR_FEET:
                return "Boots";
            case ARMOR_HEAD:
                return "Helmet";
            case ARMOR_LEGS:
                return "Leggings";
            case ARMOR_TORSO:
                return "Chestplate";
            case BOW:
                return "Bow";
            case FISHING_ROD:
                return "Fishing";
            case TOOL:
                return "Tool";
            case WEAPON:
                return "Weapon";
        }
        return "???";
    }

    /**
     * Creates a skull from a URL
     * @param urlToFormat the url
     * @return the newly created skull
     */
    public static ItemStack createSkull(String urlToFormat) {
        String url = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv" + urlToFormat;
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        if (url.isEmpty()) {
            return head;
        }
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    /**
     * Adds {@link com.turqmelon.SkyblockAbilities.utils.EnchantGlow} to an items
     * @param item the newly glowing items
     */
    public static void addGlow(ItemStack item) {
        item.addUnsafeEnchantment(Enchantment.getByName("Glow"), 1);
    }

    /**
     * Gets a player-frendly name for the provided {@link Enchantment}
     * @param enchantment the enchantment to get the name of
     * @return the friendly name
     */
    public static String getEnchantmentName(Enchantment enchantment) {

        /*if (CustomEnchantManager.isCustomEnchant(enchantment)) {
            return CustomEnchants.customEnchants.get(enchantment.getName().toLowerCase()).getLoreName();
        }*/

        if (enchantment.getName().equalsIgnoreCase(Enchantment.ARROW_DAMAGE.getName())) {
            return "Power";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.ARROW_FIRE.getName())) {
            return "Flame";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.ARROW_INFINITE.getName())) {
            return "Infinity";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.DAMAGE_ALL.getName())) {
            return "Sharpness";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.DAMAGE_ARTHROPODS.getName())) {
            return "Bane of Arthropods";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.DAMAGE_UNDEAD.getName())) {
            return "Smite";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.ARROW_KNOCKBACK.getName())) {
            return "Punch";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.DEPTH_STRIDER.getName())) {
            return "Depth Strider";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.DIG_SPEED.getName())) {
            return "Efficiency";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.DURABILITY.getName())) {
            return "Unbreaking";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.FIRE_ASPECT.getName())) {
            return "Fire Aspect";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.KNOCKBACK.getName())) {
            return "Knockback";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.LOOT_BONUS_BLOCKS.getName())) {
            return "Fortune";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.LOOT_BONUS_MOBS.getName())) {
            return "Looting";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.LUCK.getName())) {
            return "Luck of the Seas";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.LURE.getName())) {
            return "Lure";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.OXYGEN.getName())) {
            return "Respiration";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.PROTECTION_ENVIRONMENTAL.getName())) {
            return "Protection";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.PROTECTION_EXPLOSIONS.getName())) {
            return "Blast Resistance";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.PROTECTION_FALL.getName())) {
            return "Feather Falling";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.PROTECTION_FIRE.getName())) {
            return "Fire Resistance";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.PROTECTION_PROJECTILE.getName())) {
            return "Projectile Protection";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.SILK_TOUCH.getName())) {
            return "Silk Touch";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.THORNS.getName())) {
            return "Thorns";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantment.WATER_WORKER.getName())) {
            return "Aqua Affinity";
        }

        return WordUtils.capitalizeFully(enchantment.getName().replace("_", " "));
    }

    /**
     * Returns a friendly name for the provided material
     * @param material the material to get the name of
     * @return the friendly name
     */
    public static String getItemName(Material material) {
        return getItemName(new ItemStack(material));
    }

    /**
     * Returns a friendly name for the provided ItemStack. If it {@link #hasName(ItemStack)} then it'll return that
     * @param item the items to get the name of
     * @return the friendly name
     */
    public static String getItemName(ItemStack item) {

        if (hasName(item)) {
            return WordUtils.capitalize(item.getItemMeta().getDisplayName());
        }

        if (item.getType() == Material.INK_SACK) {
            switch (item.getDurability()) {
                case 0:
                    return "Ink Sac";
                case 1:
                    return "Red Dye";
                case 2:
                    return "Cactus Green";
                case 3:
                    return "Cocoa Beans";
                case 4:
                    return "Lapis Lazuli";
                case 5:
                    return "Purple Dye";
                case 6:
                    return "Cyan Dye";
                case 7:
                    return "Light Gray Dye";
                case 8:
                    return "Gray Dye";
                case 9:
                    return "Pink Dye";
                case 10:
                    return "Lime Green Dye";
                case 11:
                    return "Yellow Dye";
                case 12:
                    return "Aqua Dye";
                case 13:
                    return "Magenta Dye";
                case 14:
                    return "Orange Dye";
                case 15:
                    return "Bonemeal";
            }
        }

        //TODO Arrays.asList(derp).get(items.getDurability);
        else if (item.getType() == Material.STONE) {
            switch (item.getDurability()) {
                case 0:
                    return "Stone";
                case 1:
                    return "Granite";
                case 2:
                    return "Polished Granite";
                case 3:
                    return "Diorite";
                case 4:
                    return "Polished Diorite";
                case 5:
                    return "Andesite";
                case 6:
                    return "Polished Andesite";
            }
        } else if (item.getType() == Material.MONSTER_EGG) {

            EntityType eggType = null;
            for (EntityType type : EntityType.values()) {
                if (type.getTypeId() == item.getDurability()) {
                    eggType = type;
                    break;
                }
            }

            if (eggType != null) {
                StringBuilder s = new StringBuilder();
                String[] name = eggType.name().split("_");
                for (String n : name) {
                    if (!s.toString().isEmpty()) {
                        s.append(" ");
                    }
                    s.append(WordUtils.capitalizeFully(n));
                }
                return "Spawn " + s.toString();
            } else {
                return "Spawn ???";
            }

        } else if (item.getType() == Material.DIRT) {
            switch (item.getDurability()) {
                case 0:
                    return "Dirt";
                case 1:
                    return "Coarse Dirt";
                case 2:
                    return "Podzol";
            }
        } else if (item.getType() == Material.WOOD) {
            switch (item.getDurability()) {
                case 0:
                    return "Oak Wood Planks";
                case 1:
                    return "Spruce Wood Planks";
                case 2:
                    return "Birch Wood Planks";
                case 3:
                    return "Jungle Wood Planks";
                case 4:
                    return "Acacia Wood Planks";
                case 5:
                    return "Dark Oak Wood Planks";
            }
        } else if (item.getType() == Material.SAND) {
            switch (item.getDurability()) {
                case 0:
                    return "Sand";
                case 1:
                    return "Red Sand";
            }
        } else if (item.getType() == Material.LOG) {
            switch (item.getDurability()) {
                case 0:
                    return "Oak Wood";
                case 1:
                    return "Spruce Wood";
                case 2:
                    return "Birch Wood";
                case 3:
                    return "Jungle Wood";
            }
        } else if (item.getType() == Material.LOG_2) {
            switch (item.getDurability()) {
                case 0:
                    return "Acacia Wood";
                case 1:
                    return "Dark Oak Wood";
            }
        } else if (item.getType() == Material.SPONGE) {
            switch (item.getDurability()) {
                case 0:
                    return "Sponge";
                case 1:
                    return "Wet Sponge";
            }
        } else if (item.getType() == Material.SANDSTONE) {
            switch (item.getDurability()) {
                case 0:
                    return "Sandstone";
                case 1:
                    return "Chiseled Sandstone";
                case 2:
                    return "Smooth Sandstone";
            }
        } else if (item.getType() == Material.WOOL) {
            switch (item.getDurability()) {
                case 0:
                    return "Wool";
                case 1:
                    return "Orange Wool";
                case 2:
                    return "Magenta Wool";
                case 3:
                    return "Light Blue Wool";
                case 4:
                    return "Yellow Wool";
                case 5:
                    return "Lime Wool";
                case 6:
                    return "Pink Wool";
                case 7:
                    return "Gray Wool";
                case 8:
                    return "Light Gray Wool";
                case 9:
                    return "Cyan Wool";
                case 10:
                    return "Purple Wool";
                case 11:
                    return "Blue Wool";
                case 12:
                    return "Brown Wool";
                case 13:
                    return "Green Wool";
                case 14:
                    return "Red Wool";
                case 15:
                    return "Black Wool";
            }
        } else if (item.getType() == Material.INK_SACK) {
            switch (item.getDurability()) {
                case 0:
                    return "Ink Sac";
                case 1:
                    return "Rose Red";
                case 2:
                    return "Cactus Green";
                case 3:
                    return "Cocoa Beans";
                case 4:
                    return "Lapis Lazuli";
                case 5:
                    return "Purple Dye";
                case 6:
                    return "Cyan Dye";
                case 7:
                    return "Light Gray Dye";
                case 8:
                    return "Gray Dye";
                case 9:
                    return "Pink Dye";
                case 10:
                    return "Lime Dye";
                case 11:
                    return "Dandelion Yellow";
                case 12:
                    return "Light Blue Dye";
                case 13:
                    return "Magenta Dye";
                case 14:
                    return "Orange Dye";
                case 15:
                    return "Bone Meal";
            }
        } else if (item.getType() == Material.STAINED_GLASS) {
            switch (item.getDurability()) {
                case 0:
                    return "White Stained Glass";
                case 1:
                    return "Orange Stained Glass";
                case 2:
                    return "Magenta Stained Glass";
                case 3:
                    return "Light Blue Stained Glass";
                case 4:
                    return "Yellow Stained Glass";
                case 5:
                    return "Lime Stained Glass";
                case 6:
                    return "Pink Stained Glass";
                case 7:
                    return "Gray Stained Glass";
                case 8:
                    return "Light Gray Stained Glass";
                case 9:
                    return "Cyan Stained Glass";
                case 10:
                    return "Purple Stained Glass";
                case 11:
                    return "Blue Stained Glass";
                case 12:
                    return "Brown Stained Glass";
                case 13:
                    return "Green Stained Glass";
                case 14:
                    return "Red Stained Glass";
                case 15:
                    return "Black Stained Glass";
            }
        } else if (item.getType() == Material.SMOOTH_BRICK) {
            switch (item.getDurability()) {
                case 0:
                    return "Stone Bricks";
                case 1:
                    return "Mossy Stone Bricks";
                case 2:
                    return "Cracked Stone Bricks";
                case 3:
                    return "Chiseled Stone Bricks";
            }
        } else if (item.getType() == Material.SULPHUR) {
            return "Gunpowder";
        } else if (item.getType() == Material.PRISMARINE) {
            switch (item.getDurability()) {
                case 0:
                    return "Prismarine";
                case 1:
                    return "Prismarine Bricks";
                case 2:
                    return "Dark Prismarine";
            }
        } else if (item.getType() == Material.EXP_BOTTLE) {
            return "Bottle o' Enchanting";
        } else if (item.getType() == Material.TNT)
            return "TNT";
        else if (item.getType() == Material.STEP) {
            switch (item.getDurability()) {
                case 0:
                    return "Stone Slab";
                case 1:
                    return "Sandstone Slab";
                case 2:
                    return "Wooden Slab";
                case 3:
                    return "Cobblestone Slab";
                case 4:
                    return "Brick Slab";
                case 5:
                    return "Stone Brick Slab";
                case 6:
                    return "Nether Brick Slab";
                case 7:
                    return "Quartz Slab";
            }
        } else if (item.getType() == Material.RED_ROSE) {
            switch (item.getDurability()) {
                case 0:
                    return "Poppy";
                case 1:
                    return "Blue Orchid";
                case 2:
                    return "Allium";
                case 3:
                    return "Azure Bluet";
                case 4:
                    return "Red Tulip";
                case 5:
                    return "Orange Tulip";
                case 6:
                    return "White Tulip";
                case 7:
                    return "Pink Tulip";
                case 8:
                    return "Oxeye Daisy";
            }
        } else if (item.getType() == Material.DOUBLE_PLANT) {
            switch (item.getDurability()) {
                case 0:
                    return "Sunflower";
                case 1:
                    return "Lilac";
                case 2:
                    return "Tallgrass";
                case 3:
                    return "Large Fern";
                case 4:
                    return "Rose Bush";
                case 5:
                    return "Peony";
            }
        } else if (item.getType() == Material.YELLOW_FLOWER) {
            return "Dandelion";
        } else if (item.getType() == Material.SAPLING) {
            switch (item.getDurability()) {
                case 0:
                    return "Oak Sapling";
                case 1:
                    return "Spruce Sapling";
                case 2:
                    return "Birch Sapling";
                case 3:
                    return "Jungle Sapling";
                case 4:
                    return "Acacia Sapling";
                case 5:
                    return "Dark Oak Sapling";
            }
        } else if (item.getType() == Material.GOLDEN_APPLE) {
            switch (item.getDurability()) {
                case 0:
                    return ChatColor.AQUA + "Golden Apple";
                case 1:
                    return ChatColor.LIGHT_PURPLE + "Golden Apple";
            }
        } else if (item.getType() == Material.PISTON_BASE) {
            return "Piston";
        } else if (item.getType() == Material.PISTON_STICKY_BASE) {
            return "Sticky Piston";
        } else if (item.getType() == Material.STONE_PLATE) {
            return "Stone Pressure Plate";
        } else if (item.getType() == Material.WOOD_PLATE) {
            return "Wooden Pressure Plate";
        } else if (item.getType() == Material.REDSTONE_TORCH_ON) {
            return "Redstone Torch";
        } else if (item.getType() == Material.REDSTONE_LAMP_OFF) {
            return "Redstone Lamp";
        } else if (item.getType() == Material.HOPPER_MINECART) {
            return "Minecart with Hopper";
        } else if (item.getType() == Material.NETHER_STALK) {
            return "Nether Wart";
        } else if (item.getType() == Material.POTATO_ITEM) {
            return "Potato";
        } else if (item.getType() == Material.SKULL_ITEM) {
            switch (item.getDurability()) {
                case 0:
                    return "Skeleton Skull";
                case 1:
                    return "Wither Skeleton Skull";
                case 2:
                    return "Zombie Head";
                case 3:
                    SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
                    if (skullMeta.hasOwner()) {
                        return skullMeta.getOwner() + "'s Head";
                    } else {
                        return "Head";
                    }
                case 4:
                    return "Creeper Head";
                case 5:
                    return "Dragon Head";
            }
        } else if (item.getType() == Material.WALL_SIGN) {
            return "Sign";
        }

        return WordUtils.capitalize(item.getType().name().toLowerCase().replace("_", " "));
    }

    /**
     * Converts an ItemStack to raw JSON for storage
     * @param stack the itemstack to convert
     * @param escape whether or not to escape the JSON
     * @return the JSON representation of this items
     */
    public static String itemToJSON(ItemStack stack, boolean escape) {
        if (stack == null)
            return null;

        ItemStack newStack = stack.clone();
        if (escape) {
            ItemMeta meta = newStack.getItemMeta();

            if (hasName(newStack))
                meta.setDisplayName(StringEscapeUtils.escapeJson(meta.getDisplayName()));

            if (hasLore(newStack)) {
                List<String> temp = meta.getLore().stream().map(StringEscapeUtils::escapeJson).collect(Collectors.toList());
                meta.setLore(temp);
            }

            newStack.setItemMeta(meta);
        }

        return CraftItemStack.asNMSCopy(newStack).save(new NBTTagCompound()).toString();
    }

    /**
     * Converts an itemstack to json, escaping it
     * @param stack the itemstack to convert
     * @return the result of {@link #itemToJSON(ItemStack, boolean)}
     */
    public static String itemToJSON(ItemStack stack) {
        return itemToJSON(stack, true);
    }

    /**
     * Converts JSON back to an itemStack, defaulting to it being unescaped
     * @param json the json to convert
     * @return the result of {@link #JSONtoItemStack(String, boolean)}
     * @throws MojangsonParseException if Mojang decides to change their items serializing again for whofuckingknowswhy
     */
    public static ItemStack JSONtoItemStack(String json) throws MojangsonParseException {
        return JSONtoItemStack(json, true);
    }

    /**
     * Converts JSON back to an ItemStack
     * @param json the JSON to convert
     * @param unescape whether or not this JSON was escaped
     * @return the newly generated ItemStack
     * @throws MojangsonParseException if Mojang decides to change their items serializing again for whofuckingknowswhy
     */
    public static ItemStack JSONtoItemStack(String json, boolean unescape) throws MojangsonParseException {
        if (json == null)
            return null;

        ItemStack stack = CraftItemStack.asBukkitCopy(net.minecraft.server.v1_8_R3.ItemStack.createStack(MojangsonParser.parse(json)));
        if (unescape) {
            ItemMeta meta = stack.getItemMeta();

            if (hasName(stack)) {
                meta.setDisplayName(StringEscapeUtils.unescapeJson(meta.getDisplayName()));
            }

            if (hasLore(stack)) {
                List<String> temp = meta.getLore().stream().map(StringEscapeUtils::unescapeJson).collect(Collectors.toList());
                meta.setLore(temp);
            }

            stack.setItemMeta(meta);
        }

        return stack;
    }


    /**
     * Adds an NBTTag to an items
     * @param stack the items to add the tag to
     * @param tagName the name of this tag
     * @param tag the NBT data
     * @return the itemStack with the new NBT tag
     */
    public static ItemStack addTag(ItemStack stack, String tagName, NBTBase tag) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = getNMSItemStack(stack);

        NBTTagCompound base = new NBTTagCompound();

        nmsStack.save(base);
        if (!base.hasKey("tag")) {
            base.set("tag", new NBTTagCompound());
        }
        base.getCompound("tag").set(tagName, tag);
        nmsStack.c(base);

        stack.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
        return stack;
    }

    /**
     * Adds multiple tags to an items
     * @param stack the items to add the tags to
     * @param tags A map of tagnames and NBT data to add
     * @return the itemStack with thr newly added NBT data
     */
    public static ItemStack addTags(ItemStack stack, Map<String, NBTBase> tags) {
        NBTTagCompound base = new NBTTagCompound();

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = getNMSItemStack(stack);

        nmsStack.save(base);
        if (!base.hasKey("tag")) {
            base.set("tag", new NBTTagCompound());
        }
        for (Map.Entry<String, NBTBase> entry : tags.entrySet()) {
            base.getCompound("tag").set(entry.getKey(), entry.getValue());
        }
        nmsStack.c(base);

        stack.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
        return stack;
    }

    /**
     * Get Mojang's ItemStack from a Bukkit ItemStack
     * @param stack the Bukkit stack
     * @return the mojang stack
     */
    public static net.minecraft.server.v1_8_R3.ItemStack getNMSItemStack(ItemStack stack) {
        if (!(stack instanceof CraftItemStack)) {
            return CraftItemStack.asNMSCopy(stack);
        }
        try {
            if (stackHandle == null) {
                stackHandle = CraftItemStack.class.getDeclaredField("handle");
                stackHandle.setAccessible(true);
            }

            return (net.minecraft.server.v1_8_R3.ItemStack) stackHandle.get(stack);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return CraftItemStack.asNMSCopy(stack);
        }
    }

    /**
     * Gets NBT data from an items
     * @param stack the items
     * @param tagName the tag you want NBT from
     * @return the NBT data, or NULL if it's not present
     */
    public static NBTBase getTag(ItemStack stack, String tagName) {
        if (stack == null)
            return null;

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = getNMSItemStack(stack);

        if (nmsStack == null)
            return null;

        if (!nmsStack.hasTag())
            return null;

        NBTTagCompound base = nmsStack.getTag();

        if (!base.hasKey(tagName))
            return null;
        return base.get(tagName);

        /*nmsStack.save(base);

        if (!base.hasKey("tag"))
            return null;
        if (!base.getCompound("tag").hasKey(tagName))
            return null;
        return base.getCompound("tag").get(tagName);*/
    }

    /**
     * Removes an NBT tag from an items
     * @param stack the items to remove the tag from
     * @param tagName the name of the tag to remove
     * @return true if the tag was successfully removed
     */
    public static boolean removeTag(ItemStack stack, String tagName) {
        NBTTagCompound base = new NBTTagCompound();

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = getNMSItemStack(stack);

        if (nmsStack == null)
            return false;
        nmsStack.save(base);

        if (!base.hasKey("tag"))
            return false;
        if (!base.getCompound("tag").hasKey(tagName))
            return false;

        base.getCompound("tag").remove(tagName);
        nmsStack.c(base);
        stack.setItemMeta(CraftItemStack.getItemMeta(nmsStack));

        return true;
    }

    public static boolean hasEnchantments(ItemStack stack) {
        if(!stack.getItemMeta().getEnchants().isEmpty()){
            return true;
        }

        return false;
    }
}