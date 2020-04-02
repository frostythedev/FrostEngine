package me.frostythedev.frostengine.bukkit.utils.gui;

import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public abstract class GUI {

    @SuppressWarnings("deprecation")
    protected static final ItemStack SPACER = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0, DyeColor.GRAY.getData());
    protected static final ItemStack NEXT_PAGE  = new ItemBuilder(Material.ARROW).withCustomName("&aNext Page &l>").build();
    protected static ItemStack PREVIOUS_PAGE  = new ItemBuilder(Material.ARROW).withCustomName("&a&l< &aPrevious Page").build();

    public static Plugin instance = FEPlugin.get();

    static {
        ItemMeta spacerMeta = SPACER.getItemMeta();
        spacerMeta.setDisplayName(" ");
        SPACER.setItemMeta(spacerMeta);
    }

    protected InventoryProxy inventory;
    protected Player player = null;
    protected boolean populated = false;
    private Inventory bukkitInventory;
    private boolean invCheckOverride = false;
    private boolean allowDrag = false;
    private boolean allowShiftClicking = false;
    private BukkitRunnable updaterTask;

    private Map<Integer, ErrorIcon> errorIconMap = new HashMap<>();

    public GUI(String title, int size) {
        if (title.length() > 32)
            title = title.substring(0, 32);
        this.bukkitInventory = Bukkit.createInventory(null, getInvSizeForCount(size), title);
        this.inventory = new InventoryProxy(bukkitInventory, Bukkit.createInventory(null, getInvSizeForCount(size), title));
        Bukkit.getPluginManager().registerEvents(new GUIEvents(this), instance);
    }

    public GUI(String title, InventoryType type) {
        this.bukkitInventory = Bukkit.createInventory(null, type, title);
        this.inventory = new InventoryProxy(bukkitInventory, Bukkit.createInventory(null, type, title));
        Bukkit.getPluginManager().registerEvents(new GUIEvents(this), instance);
    }

    public GUI(Inventory bukkitInventory) {
        this.bukkitInventory = bukkitInventory;
        this.inventory = new InventoryProxy(bukkitInventory, bukkitInventory);
        Bukkit.getPluginManager().registerEvents(new GUIEvents(this), instance);
    }

    public final InventoryView open(Player p) {
        this.player = p;
        try {
            if (!this.populated) {
                this.populate();
                this.inventory.apply();
                this.populated = true;
            }
            return this.openInventory(p);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public Player getPlayer() {
        return player;
    }

    public final void close() {
        for (HumanEntity human : new ArrayList<>(this.getBukkitInventory().getViewers())) {
            human.closeInventory();
        }
    }

    protected InventoryView openInventory(Player p) {
        return p.openInventory(this.getBukkitInventory());
    }

    public Inventory getBukkitInventory() {
        return bukkitInventory;
    }

    public Inventory getProxyInventory() {
        return inventory;
    }

    protected abstract void onGUIInventoryClick(InventoryClickEvent event);

    protected void onPlayerInventoryClick(InventoryClickEvent event) {
    }

    protected void onTickUpdate() {
    }

    protected void onPlayerCloseInv(InventoryCloseEvent event) {
    }

    protected void onPlayerDrag(InventoryDragEvent event) {
    }

    protected final int getInvSizeForCount(int count) {
        int size = (count / 9) * 9;
        if (count % 9 > 0) size += 9;
        if (size < 9) return 9;
        if (size > 54) return 54;
        return size;
    }

    public void setInvCheckOverride(boolean invCheckOverride) {
        this.invCheckOverride = invCheckOverride;
    }

    protected abstract void populate();

    private void cleanupErrors() {
        Iterator<Map.Entry<Integer, ErrorIcon>> iterator = errorIconMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            ErrorIcon icon = (ErrorIcon) entry.getValue();
            if (System.currentTimeMillis() >= icon.getExpire()) {
                iterator.remove();
            }
        }
    }

    protected void repopulate() {
        try {
            this.inventory.clear();
            if (player == null || player.isOnline()) {
                this.populate();
                cleanupErrors();
                for (Map.Entry entry : errorIconMap.entrySet()) {
                    int slot = (int) entry.getKey();
                    ErrorIcon icon = (ErrorIcon) entry.getValue();
                    this.inventory.setItem(slot, icon.toItem());
                }
                this.inventory.apply();
            }
            this.populated = true;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    protected final void setUpdateTicks(int ticks) {
        this.setUpdateTicks(ticks, false);
    }

    protected final void setUpdateTicks(int ticks, boolean sync) {
        if (this.updaterTask != null) {
            this.updaterTask.cancel();
            this.updaterTask = null;
        }
        this.updaterTask = new GUIUpdateTask(this);
        if (sync) {
            this.updaterTask.runTaskTimer(instance, 0, ticks);
        } else {
            this.updaterTask.runTaskTimerAsynchronously(instance, 0, ticks);
        }
    }

    public void showError(int slot, String title, String... subtitle) {
        ErrorIcon icon = new ErrorIcon(title, subtitle, System.currentTimeMillis() + 3000);
        errorIconMap.put(slot, icon);
        repopulate();
    }

    protected final void scheduleOpen(final GUI gui, final Player player) {
        Bukkit.getScheduler().runTask(instance, () -> gui.open(player));
    }


    protected void setAllowDrag(boolean allowDrag) {
        this.allowDrag = allowDrag;
    }

    protected boolean isAllowShiftClicking() {
        return allowShiftClicking;
    }

    protected void setAllowShiftClicking(boolean allowShiftClicking) {
        this.allowShiftClicking = allowShiftClicking;
    }

    private class GUIEvents implements Listener {

        private GUI gui;

        public GUIEvents(GUI gui) {
            this.gui = gui;
        }

        @EventHandler
        public void onInventoryClick(InventoryClickEvent event) {
            try {
                if (this.gui.bukkitInventory.getViewers().contains(event.getWhoClicked())) {
                    List<InventoryAction> deniedActions = new ArrayList<>(Arrays.asList(
                            InventoryAction.CLONE_STACK,
                            InventoryAction.COLLECT_TO_CURSOR,
                            InventoryAction.UNKNOWN
                    ));

                    if (!allowShiftClicking) {
                        deniedActions.add(InventoryAction.MOVE_TO_OTHER_INVENTORY);
                    }

                    if (deniedActions.contains(event.getAction())) {
                        event.setCancelled(true);
                    }

                    if (!allowShiftClicking && event.getClick().isShiftClick()) {
                        event.setCancelled(true);
                    }

                    if (!invCheckOverride && (event.getClickedInventory() == null))
                        return;

                    if (!event.getClickedInventory().equals(gui.getBukkitInventory())) {
                        gui.onPlayerInventoryClick(event);
                        return;
                    }

                    event.setCancelled(true);

                    if (!(event.getWhoClicked() instanceof Player))
                        return;

                    gui.onGUIInventoryClick(event);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        @EventHandler
        public void onInventoryClose(InventoryCloseEvent event) {
            if (!event.getInventory().equals(gui.getBukkitInventory()))
                return;
            if (bukkitInventory.getViewers().size() <= 1) {
                HandlerList.unregisterAll(this);
                try {
                    gui.onPlayerCloseInv(event);
                    //Bukkit.getScheduler().runTask(FEPlugin.get(), () -> BlockListener.updateSpecialItemCooldown(player));
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                if (gui.updaterTask != null) {
                    gui.updaterTask.cancel();
                }
            }
        }

        @EventHandler
        public void onInventoryDrag(InventoryDragEvent event) {
            try {
                if (!event.getInventory().equals(gui.getBukkitInventory())) return;
                if (!allowDrag) {
                    event.setCancelled(true);
                } else {
                    gui.onPlayerDrag(event);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private class GUIUpdateTask extends BukkitRunnable {

        private GUI gui;

        public GUIUpdateTask(GUI gui) {
            this.gui = gui;
        }

        public void run() {
            try {
                this.gui.repopulate();
                this.gui.onTickUpdate();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public class ErrorIcon {

        private String errorTitle;
        private String[] errorSubtitle;
        private long expire;

        public ErrorIcon(String errorTitle, String[] errorSubtitle, long expire) {
            this.errorTitle = errorTitle;
            this.errorSubtitle = errorSubtitle;
            this.expire = expire;
        }

        public ItemStack toItem() {
            List<String> lore = new ArrayList<>();
            for (String line : errorSubtitle) {
                lore.add("§7" + line);
            }
            return new ItemBuilder(Material.BARRIER)
                    .withCustomName("§c§l" + errorTitle)
                    .withLore(lore)
                    .build();
        }

        public String getErrorTitle() {
            return errorTitle;
        }

        public String[] getErrorSubtitle() {
            return errorSubtitle;
        }

        public long getExpire() {
            return expire;
        }
    }

    public class InventoryProxy implements Inventory {

        private Inventory mainInventory;
        private Inventory proxyInventory;

        private InventoryProxy(Inventory mainInventory, Inventory proxyInventory) {
            this.mainInventory = mainInventory;
            this.proxyInventory = proxyInventory;
        }

        public void apply() {
            this.mainInventory.setContents(this.proxyInventory.getContents());
        }

        public int getSize() {
            return proxyInventory.getSize();
        }

        public int getMaxStackSize() {
            return proxyInventory.getMaxStackSize();
        }

        public void setMaxStackSize(int i) {
            proxyInventory.setMaxStackSize(i);
        }

        public String getName() {
            return proxyInventory.getName();
        }

        public ItemStack getItem(int i) {
            return proxyInventory.getItem(i);
        }

        public void setItem(int i, ItemStack itemStack) {
            proxyInventory.setItem(i, itemStack);
        }

        public HashMap<Integer, ItemStack> addItem(ItemStack... itemStacks) throws IllegalArgumentException {
            return proxyInventory.addItem(itemStacks);
        }

        public HashMap<Integer, ItemStack> removeItem(ItemStack... itemStacks) throws IllegalArgumentException {
            return proxyInventory.removeItem(itemStacks);
        }

        public ItemStack[] getContents() {
            return proxyInventory.getContents();
        }

        public void setContents(ItemStack[] itemStacks) throws IllegalArgumentException {
            proxyInventory.setContents(itemStacks);
        }

        public boolean contains(int i) {
            return proxyInventory.contains(i);
        }

        public boolean contains(Material material) throws IllegalArgumentException {
            return proxyInventory.contains(material);
        }

        public boolean contains(ItemStack itemStack) {
            return proxyInventory.contains(itemStack);
        }

        public boolean contains(int i, int i1) {
            return proxyInventory.contains(i, i1);
        }

        public boolean contains(Material material, int i) throws IllegalArgumentException {
            return proxyInventory.contains(material, i);
        }

        public boolean contains(ItemStack itemStack, int i) {
            return proxyInventory.contains(itemStack, i);
        }

        public boolean containsAtLeast(ItemStack itemStack, int i) {
            return proxyInventory.containsAtLeast(itemStack, i);
        }

        public HashMap<Integer, ? extends ItemStack> all(int i) {
            return proxyInventory.all(i);
        }

        public HashMap<Integer, ? extends ItemStack> all(Material material) throws IllegalArgumentException {
            return proxyInventory.all(material);
        }

        public HashMap<Integer, ? extends ItemStack> all(ItemStack itemStack) {
            return proxyInventory.all(itemStack);
        }

        public int first(int i) {
            return proxyInventory.first(i);
        }

        public int first(Material material) throws IllegalArgumentException {
            return proxyInventory.first(material);
        }

        public int first(ItemStack itemStack) {
            return proxyInventory.first(itemStack);
        }

        public int firstEmpty() {
            return proxyInventory.firstEmpty();
        }

        public void remove(int i) {
            proxyInventory.remove(i);
        }

        public void remove(Material material) throws IllegalArgumentException {
            proxyInventory.remove(material);
        }

        public void remove(ItemStack itemStack) {
            proxyInventory.remove(itemStack);
        }

        public void clear(int i) {
            proxyInventory.clear(i);
        }

        public void clear() {
            proxyInventory.clear();
        }

        public List<HumanEntity> getViewers() {
            return mainInventory.getViewers();
        }

        public String getTitle() {
            return mainInventory.getTitle();
        }

        public InventoryType getType() {
            return mainInventory.getType();
        }

        public InventoryHolder getHolder() {
            return mainInventory.getHolder();
        }

        public ListIterator<ItemStack> iterator() {
            return proxyInventory.iterator();
        }

        public ListIterator<ItemStack> iterator(int i) {
            return proxyInventory.iterator(i);
        }
    }
}