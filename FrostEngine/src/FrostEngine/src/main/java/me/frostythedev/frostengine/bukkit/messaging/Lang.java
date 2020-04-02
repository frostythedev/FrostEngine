package me.frostythedev.frostengine.bukkit.messaging;

import me.frostythedev.frostengine.bukkit.thread.Tasks;
import me.frostythedev.frostengine.config.BukkitDocument;

/**
 * Programmed by Tevin on 8/5/2016.
 */
public enum Lang {
    BAN_MESSAGE("punishments.ban", "&c%o has been banned by %e", new String[]{"o", "e"});

    private String path;
    private String value;
    private String[] variables;

    private final BukkitDocument document = BukkitDocument.of("plugins/FrostEngine/system/lang.yml");

    Lang(String path, String value, String[] variables) {
        this.path = path;
        this.value = value;
        this.variables = variables;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String[] getVariables() {
        return variables;
    }

    public void setVariables(String[] variables) {
        this.variables = variables;
    }


    public Object get(String path) {
        for (Lang lang : values()) {
            if(lang.getPath().equalsIgnoreCase(path)){
                return lang.getValue();
            }
        }
        return null;
    }

    public void load() {
        this.document.load();

        for (Lang lang : values()) {
            if (this.document.getString(lang.path) == null) {
                this.document.set(lang.path, lang.value);
            } else {
                lang.setValue(this.document.getString(lang.path));
            }
        }

        Tasks.run(this.document);
    }

    public void save() {
        this.document.load();

        for (Lang lang : values()) {
            this.document.set(lang.path, lang.value);
        }

        Tasks.run(this.document);
    }



}
