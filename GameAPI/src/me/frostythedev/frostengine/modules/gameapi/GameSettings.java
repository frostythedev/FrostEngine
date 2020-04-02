package gameapi.

import me.frostythedev.frostengine.bukkit.utilities.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.*;

public class GameSettings implements Listener {

    private boolean notify, build, destroy, pickup, drop, teleport,
            pvp, pve, buckets, interact, interactEntity, chat, explode,
            tracking, target;

    public GameSettings() {
    }

    public GameSettings(boolean notify, boolean build, boolean destroy, boolean pickup, boolean drop, boolean teleport,
                        boolean pvp, boolean pve, boolean buckets, boolean interact, boolean interactEntity, boolean chat,
                        boolean explode, boolean tracking, boolean target) {
        this.notify = notify;
        this.build = build;
        this.destroy = destroy;
        this.pickup = pickup;
        this.drop = drop;
        this.teleport = teleport;
        this.pvp = pvp;
        this.pve = pve;
        this.buckets = buckets;
        this.interact = interact;
        this.interactEntity = interactEntity;
        this.chat = chat;
        this.explode = explode;
        this.tracking = tracking;
        this.target = target;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public boolean isBuild() {
        return build;
    }

    public void setBuild(boolean build) {
        this.build = build;
    }

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public boolean isPickup() {
        return pickup;
    }

    public void setPickup(boolean pickup) {
        this.pickup = pickup;
    }

    public boolean isDrop() {
        return drop;
    }

    public void setDrop(boolean drop) {
        this.drop = drop;
    }

    public boolean isTeleport() {
        return teleport;
    }

    public void setTeleport(boolean teleport) {
        this.teleport = teleport;
    }

    public boolean isPvp() {
        return pvp;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }

    public boolean isPve() {
        return pve;
    }

    public void setPve(boolean pve) {
        this.pve = pve;
    }

    public boolean isBuckets() {
        return buckets;
    }

    public void setBuckets(boolean buckets) {
        this.buckets = buckets;
    }

    public boolean isInteract() {
        return interact;
    }

    public void setInteract(boolean interact) {
        this.interact = interact;
    }

    public boolean isInteractEntity() {
        return interactEntity;
    }

    public void setInteractEntity(boolean interactEntity) {
        this.interactEntity = interactEntity;
    }

    public boolean isChat() {
        return chat;
    }

    public void setChat(boolean chat) {
        this.chat = chat;
    }

    public boolean isExplode() {
        return explode;
    }

    public void setExplode(boolean explode) {
        this.explode = explode;
    }

    public boolean isTracking() {
        return tracking;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }

    public boolean isTarget() {
        return target;
    }

    public void setTarget(boolean target) {
        this.target = target;
    }

    @EventHandler
    public void onBuild(BlockPlaceEvent event) {
        if (!isBuild()) {
            event.setCancelled(true);
            if (isNotify()) {
                event.getPlayer().sendMessage(StringUtil.formatColorCodes("&4>> &cYou are not allowed to build!"));
            }
        }
    }

    @EventHandler
    public void onDestroy(BlockBreakEvent event) {
        if (!isDestroy()) {
            event.setCancelled(true);
            if (isNotify()) {
                event.getPlayer().sendMessage(StringUtil.formatColorCodes("&4>> &cYou are not allowed to destroy!"));
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (!isPickup()) {
            event.setCancelled(true);
            if (isNotify()) {
                event.getPlayer().sendMessage(StringUtil.formatColorCodes("&4>> &cYou are not allowed to pickup!"));
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!isDrop()) {
            event.setCancelled(true);
            if (isNotify()) {
                event.getPlayer().sendMessage(StringUtil.formatColorCodes("&4>> &cYou are not allowed to drop items!"));
            }
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (!isTeleport()) {
            event.setCancelled(true);
            if (isNotify()) {
                event.getPlayer().sendMessage(StringUtil.formatColorCodes("&4>> &cYou are not allowed to teleport!"));
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (event.getEntity() instanceof Player) {
                if (!isPvp()) {
                    event.setCancelled(true);
                    if (isNotify()) {
                        event.getDamager().sendMessage(StringUtil.formatColorCodes("&4>> &cYou are not allowed to harm players!"));
                    }
                }
            } else {
                if (!isPve()) {
                    event.setCancelled(true);
                    if (isNotify()) {
                        event.getDamager().sendMessage(StringUtil.formatColorCodes("&4>> &cYou are not allowed to harm entities!"));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBuckets(PlayerBucketFillEvent event) {
        if (!isBuckets()) {
            event.setCancelled(true);
            if (isNotify()) {
                event.getPlayer().sendMessage(StringUtil.formatColorCodes("&4>> &cYou are not allowed to fill buckets!"));
            }
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if (!isInteractEntity()) {
            event.setCancelled(true);
            if (isNotify()) {
                event.getPlayer().sendMessage(StringUtil.formatColorCodes("&4>> &cYou are not allowed to interact with entities!"));
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (!isChat()) {
            event.setCancelled(true);
            if (isNotify()) {
                event.getPlayer().sendMessage(StringUtil.formatColorCodes("&4>> &cYou are not allowed to chat!"));
            }
        }
    }

    @EventHandler
    public void onDrop(EntityTargetEvent event) {
        if (!isTarget()) {
            event.setCancelled(true);
        }
    }
}
