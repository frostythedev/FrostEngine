package me.frostythedev.frostengine.bukkit.module;

import co.aikar.commands.BaseCommand;
import com.google.common.collect.Sets;
import me.frostythedev.frostengine.bukkit.event.api.AbstractListener;
import me.frostythedev.frostengine.bukkit.event.api.ListenerUtils;
import me.frostythedev.frostengine.bukkit.impl.Constants;
import me.frostythedev.frostengine.config.BukkitDocument;
import org.apache.commons.io.FileUtils;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Programmed by Tevin on 7/18/2016.
 */
public class Module implements ModuleBase {

    private Module parent;
    private String parentName;

    private String moduleName;
    private String description;
    private String version;
    private String author;
    private boolean loaded;
    private Set<Listener> listeners;
    private Set<BaseCommand> commands;
    private Set<Module> childModules;

    public Module(String moduleName) {
        this(moduleName, "Default description [:(]");
    }

    public Module(String moduleName, String description) {
        this(moduleName, description, "UNRELEASED", "frostythedev");
    }

    public Module(String moduleName, String description, String version) {
       this(moduleName, description, version, "frostythedev");
    }

    public Module(String moduleName, String description, String version, String author) {
        this.moduleName = moduleName;
        this.description = description;
        this.version = version;
        this.author = author;
        this.loaded = true;
        this.listeners = Sets.newLinkedHashSet();
        this.commands = Sets.newLinkedHashSet();
        this.childModules = Sets.newLinkedHashSet();
    }


    ///////////////////////////////////////////////
    // MADE METHODS
    ///////////////////////////////////////////////

    public void addListener(AbstractListener listener) {
        if ( !isLoaded()) return;
        getListeners().add(listener);
    }

    public void registerListeners(Listener... listeners) {
        if ( !isLoaded()) return;
        for(Listener listener : listeners){
            getListeners().add(listener);
        }
    }

    public void addCommand(BaseCommand command) {
        if (!isLoaded()) return;
        getCommands().add(command);
    }

    public String getModuleDirectory(){
        return "plugins/FrostEngine/modules/storage/" + getModuleName() + "";
    }

    public BukkitDocument getConfig(){
        return BukkitDocument.of(Constants.MODULE_STORAGE_DIRECTORY + "/" + getModuleName() + "/config.yml");
    }

    public void saveDefaultConfig(){
        if(!new File(getModuleDirectory()).exists()){
            new File(getModuleDirectory()).mkdir();
        }
        if(!new File(getModuleDirectory() + File.separator + "config.yml").exists()){
            if(this.getClass().getResource("config.yml") != null){
                try {
                    FileUtils.copyURLToFile(this.getClass().getResource("config.yml"), new File(getModuleDirectory()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadConfig(){
        getConfig().create(true);
    }

    public void saveConfig(){
        getConfig().save();
    }

    ///////////////////////////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////////////////////////

    public Module getParent() {
        return parent;
    }

    public void setParent(Module parent) {
        this.parent = parent;
        this.parentName = parent.getModuleName();
    }


    ///////////////////////////////////////////////
    // OVERRIDES
    ///////////////////////////////////////////////



    @Override
    public String getModuleName() {
        return moduleName;
    }

    @Override
    public String getModuleDescription() {
        return description;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public void setLoaded(boolean b) {
        this.loaded = b;
    }

    @Override
    public Set<Listener> getListeners() {
        return listeners;
    }

    @Override
    public Set<BaseCommand> getCommands() {
        return commands;
    }

    @Override
    public Set<Module> getChildModules() {
        return childModules;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public void onModuleEnable() {
        File configFile = new File(Constants.MODULE_STORAGE_DIRECTORY + "/" + getModuleName() + "/config.yml");
        if(!configFile.exists()){
            if(!configFile.getParentFile().exists()){
                configFile.getParentFile().mkdir();
            }
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(Module module : childModules){
            module.setParent(this);
        }

        /*if(getParent() != null){
            if(!getParent().isLoaded()){
                FEPlugin.get().getModuleLoader().loadExternModule(getParent().getClass());
            }
        }*/
    }

    @Override
    public void onModuleDisable() {
        ListenerUtils.disableAll(getListeners());
    }

    public void onCompleteLoad() {

    }
}
