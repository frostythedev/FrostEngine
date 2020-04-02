package me.frostythedev.frostengine.modules;

import me.frostythedev.frostengine.config.BukkitDocument;

public class TimedPunishment extends Punishment {

    private long expiry;

    public TimedPunishment(PunishmentType punishmentType, String enforcer, String offender, String reason, long expiry) {
        super(punishmentType, enforcer, offender, reason);
        this.expiry = expiry;
    }

    public TimedPunishment(PunishmentType punishmentType, String enforcer, String offender, String reason, long timestamp, long expiry) {
        super(punishmentType, enforcer, offender, reason, timestamp);
        this.expiry = expiry;
    }

    public long getEndTime(){
       return (getTimestamp()+getExpiry());
    }

    public boolean isExpired(){
        return System.currentTimeMillis() > (getEndTime());
    }

    public long getExpiry() {
        return expiry;
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    @Override
    protected void into(BukkitDocument document) {
        super.into(document);
        document.set("expiry", getExpiry());
        document.set("endTime", getEndTime());
    }

}
