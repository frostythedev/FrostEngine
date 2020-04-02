package me.frostythedev.frostengine.modules;

import me.frostythedev.frostengine.config.BukkitDocument;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public abstract class Punishment {

    private PunishmentType punishmentType;
    private String enforcer;
    private String offender;
    private String reason;
    private long timestamp;

    public Punishment(PunishmentType punishmentType, String enforcer, String offender, String reason) {
       this(punishmentType, enforcer, offender, reason, System.currentTimeMillis());
    }

    public Punishment(PunishmentType punishmentType, String enforcer, String offender, String reason, long timestamp) {
        this.punishmentType = punishmentType;
        this.enforcer = enforcer;
        this.offender = offender;
        this.reason = reason;
        this.timestamp = timestamp;
    }

    public void broadcast(boolean silent) {
    }

    public void execute() {
    }

    public PunishmentType getPunishmentType() {
        return punishmentType;
    }

    public String getEnforcer() {
        return enforcer;
    }

    public void setEnforcer(String enforcer) {
        this.enforcer = enforcer;
    }

    public String getOffender() {
        return offender;
    }

    public void setOffender(String offender) {
        this.offender = offender;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    protected void into(BukkitDocument document) {
        document.set("type", getPunishmentType().name());
        document.set("enforcer", getEnforcer());
        document.set("offender", getOffender());
        document.set("reason", getReason());
        document.set("timestamp", getTimestamp());
    }
}
