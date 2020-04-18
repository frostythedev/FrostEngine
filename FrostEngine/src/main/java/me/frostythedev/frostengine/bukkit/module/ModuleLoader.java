package me.frostythedev.frostengine.bukkit.module;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

public class ModuleLoader extends URLClassLoader {

    private Plugin plugin;
    private File dir;

    private Map<String, Module> modules;
    private Map<String, File> moduleFiles;

    private URLClassLoader classLoader;

    public ModuleLoader(Plugin plugin) {
        super(plugin.getURLClassLoader().getURLs(), plugin.getClass().getClassLoader());
        this.plugin = plugin;
        this.modules = Maps.newHashMap();
        this.moduleFiles = Maps.newHashMap();
        this.dir = new File(plugin.getDataFolder() + File.separator + "modules");
        this.dir.mkdir();
        if (getClass().getConstructors().length > 1) {
            this.classLoader = null;
            return;
        }
        this.classLoader = new URLClassLoader(getURLs(), this);

        for (String moduleFile : this.dir.list()) {
            if (moduleFile.contains(".jar")) {

                File file = new File(this.dir, moduleFile);
                String name = moduleFile.replace(".jar", "").replace("Module", "");

                if (this.moduleFiles.containsKey(name.toLowerCase())) {
                    Bukkit.getLogger().log(Level.SEVERE, "Duplicate module jar found! Please remove " + moduleFile + " or " + this.moduleFiles.get(name).getName());
                } else {
                    System.out.println("Added to moduleFiles: " + name.toLowerCase());
                    this.moduleFiles.put(name.toLowerCase(), file);
                    //Bukkit.getLogger().info("Loaded module file " + name + ".jar");
                    try {
                        addURL(file.toURI().toURL());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void addModule(Module module) {
        this.modules.put(module.getModuleName().toLowerCase().replace("Module", ""), module);
    }

    public Module getModule(String name) {
        if (name == null) {
            return null;
        }
        if ((!isLoaded(name)) && (this.moduleFiles.containsKey(name.toLowerCase()))) {
            loadModule(name);
        }
        return this.modules.get(name.toLowerCase());
    }

    public Collection<Module> getModules() {
        return Collections.unmodifiableCollection(this.modules.values());
    }

    public boolean isLoaded(String name) {
        return this.modules.containsKey(name.toLowerCase());
    }

    public Module loadModule(File file) {
        String mainClass = null;
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry element = entries.nextElement();
                if (element.getName().equalsIgnoreCase("module.info")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(element)));
                    mainClass = reader.readLine().substring(12);
                    break;
                }
            }
            if (mainClass != null) {
                Class<?> clazz = Class.forName(mainClass, true, this.classLoader);
                return initModule(clazz.asSubclass(Module.class));
            }
            throw new IllegalArgumentException();
        } catch (InstantiationException | InvocationTargetException | NoClassDefFoundError | ClassNotFoundException | NoSuchMethodException | IllegalArgumentException e) {
            e.printStackTrace();
            //Bukkit.getLogger().log(Level.INFO, "For detail, ensure debug is on and inspect debug.log");
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, String.format("Unable to load module in %s.", new Object[]{file.getName()}));
            Bukkit.getLogger().log(Level.WARNING, String.format("%s: %s", new Object[]{e.getClass().getName(), e.getMessage()}));
            e.printStackTrace();
        } finally {
            try {
                jarFile.close();
            } catch (IOException | NullPointerException ignored) {
            }
        }
        return null;
    }

    public Module loadExternModule(Class<? extends Module> clazz) {
        try {
            return initModule(clazz);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Module initModule(Class<? extends Module> skillClass)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Constructor<? extends Module> ctor = skillClass.getConstructor();
        Module module = ctor.newInstance();
        this.plugin.addChildModule(module);

        if(module.getParentName() != null && !module.getParentName().equals("")){
            System.out.println("Parent Module is: " + module.getParentName());
            if(loadModule(module.getParentName())){
                System.out.println("Parent Module has been loaded!");
                module.enable();
                return module;
            }
        }else{
            System.out.println("No parent module found, initializing normally!");
            module.enable();
            return module;
        }
        return null;
    }

    public void loadModules() {
        for (Map.Entry<String, File> entry : this.moduleFiles.entrySet()) {
            if (!isLoaded(entry.getKey())) {
                Module module = loadModule(entry.getValue());
                if (module != null) {
                    addModule(module);
                    //Bukkit.getLogger().log(Level.INFO, "Module " + module.getModuleName() + " Loaded");
                }
            }
        }
       /*
        for (SkillRegistrar.Pair<Plugin, Class<? extends Skill>> p : SkillRegistrar.getRegisteredSkillClasses()) {
            Skill skill = loadExternSkill((Plugin) p.getLeft(), (Class) p.getRight());
            if (skill != null) {
                addSkill(skill);
                if (Heroes.properties.debug) {
                    Heroes.debugLog(Level.INFO, "Skill " + skill.getName() + " Loaded");
                }
            }
        }
        SkillRegistrar.stopRegistration();
        */
    }

    private boolean loadModule(String name) {
        if (isLoaded(name)) {
            return true;
        }
        Module module = loadModule(this.moduleFiles.get(name.toLowerCase()));
        if (module == null) {
            return false;
        }
        addModule(module);
        return true;
    }

    public Module enableModule(String name) {
        if (!isLoaded(name)) {
            Module module = getModule(name);
            if (module != null) {
                return module;
            }
        } else {
            return getModule(name);
        }

        return null;
    }

    public boolean disableModule(String name) {
        if (isLoaded(name)) {
            Module module = getModule(name);
            module.onModuleDisable();
            this.modules.remove(name);
            return true;
        } else {
            return false;
        }
    }

}
