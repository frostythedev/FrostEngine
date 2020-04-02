package me.frostythedev.frostengine.config;

import com.google.common.collect.Lists;
import me.frostythedev.frostengine.data.storage.StoreHashMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BukkitDocument implements Runnable {

    private static StoreHashMap<BukkitDocument> INST;

    private String directory;

    private YamlConfiguration config;

    protected BukkitDocument(String directory) {
        this.directory = directory;
    }

    public File getFile() {
        return new File(this.directory);
    }

    public boolean exists() {
        return getFile() != null && getFile().exists();
    }

    public String getDirectory() {
        return directory;
    }

    public void create() {
        this.create(false);
    }

    public void create(boolean load) {
        if(!this.getFile().getParentFile().exists()){
            this.getFile().getParentFile().mkdir();
        }

        if (!exists()) {
            try {
                this.getFile().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (load) {
            this.load();
        }
    }

    public void load() {
        try {
            this.config = YamlConfiguration.loadConfiguration(this.getFile());
            this.config.load(this.getFile());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save(boolean force) {
        if (force) this.create(true);
        try {
            this.config.save(this.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        this.save(false);
    }

    public void delete() {
        if (exists()) {
            if (this.getFile().delete()) {
                this.config = null;
            }
        }
    }

    public Object get(String path) {
        return this.config.get(path);
    }

    public Object get(String path, Object def) {
        return this.config.get(path, def);
    }

    public String getString(String path) {
        return this.config.getString(path);
    }

    public String getString(String path, String def) {
        return this.config.getString(path, def);
    }

    public int getInteger(String path) {
        return this.config.getInt(path);
    }

    public int getInteger(String path, int def) {
        return this.config.getInt(path, def);
    }

    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    public boolean getBoolean(String path, boolean def) {
        return this.config.getBoolean(path, def);
    }

    public void createSection(String path) {
        this.config.createSection(path);
    }

    public ConfigurationSection getSection(String path) {
        return this.config.getConfigurationSection(path);
    }

    public boolean isSection(String path) {
        return this.getSection(path) == null;
    }

    public boolean createSectionIfNotExists(String path) {
        if (isSection(path)) {
            return false;
        }
        this.createSection(path);
        return true;
    }

    public Map<String, Object> getValues(boolean deep) {
        return this.config.getValues(deep);
    }

    public double getDouble(String path) {
        return this.config.getDouble(path);
    }

    public double getDouble(String path, double def) {
        return this.config.getDouble(path, def);
    }

    public long getLong(String path) {
        return this.config.getLong(path);
    }

    public long getLong(String path, long def) {
        return this.config.getLong(path, def);
    }

    public List<?> getList(String path) {
        return this.config.getList(path);
    }

    public List<?> getList(String path, List<?> def) {
        return this.config.getList(path, def);
    }

    public boolean contains(String path) {
        return this.config.contains(path);
    }

    public void removeKey(String path) {
        this.config.set(path, null);
    }

    public Set<String> getKeys(Boolean deep) {
        return this.config.getKeys(deep);
    }

    public Set<String> getKeys(boolean deep) {
        return this.config.getKeys(deep);
    }

    public List<String> getStringList(String path) {
        return this.config.getStringList(path);
    }

    public void createStringListIfNotExists(String path){
        if(this.getStringList(path) == null){
            List<String> empty = Lists.newArrayList();
            this.config.set(path, empty);
        }
    }

    public void set(String path, Object value) {
        this.config.set(path, value);
    }

    public static BukkitDocument of(String directory) {
        if(INST == null){
            INST = new StoreHashMap<>();
        }
        if (INST.get(directory) == null) {
            BukkitDocument document = new BukkitDocument(directory);
            document.create(true);
            INST.put(directory, document);
            return document;
        }
        return INST.get(directory);
    }

    @Override
    public final void run() {
        this.save(true);
    }
}
