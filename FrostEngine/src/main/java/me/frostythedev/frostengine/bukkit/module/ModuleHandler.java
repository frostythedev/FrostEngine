package me.frostythedev.frostengine.bukkit.module;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.frostythedev.frostengine.Handler;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ModuleHandler implements Handler {

    private static final Logger log = Logger.getLogger(ModuleHandler.class.getName());
    private static Map<Class<Module>, Module> offeredModules = new HashMap<>();
    private static boolean isAcceptingOffers = true;

    @Nonnull
    private List<Module> modules = new ArrayList<>();

    @Inject
    private Injector injector;

    @Nonnull
    public static Map<Class<Module>, Module> getOfferedModules() {
        return offeredModules;
    }

    @Override
    public void enable() {
        log.info("Loading modules");
        findModules();

        modules.forEach(Module::enable);
    }

    public void findModules(){
        isAcceptingOffers = false;
        for (Class<? extends Module> clazz : offeredModules.keySet()) {
            ModuleInfo info = clazz.getAnnotation(ModuleInfo.class);
            if (info == null) {
                log.warning("Class " + clazz.getSimpleName() + " has no module info!");
                continue;
            }
            log.info("Loading module " + info.name() + " v" + info.version() + " by " + Arrays
                    .toString(info.authors()));
            if (Module.class.isAssignableFrom(clazz)) {
                //noinspection SuspiciousMethodCalls
                Module module = offeredModules.get(clazz);
                injector.injectMembers(module);
                this.modules.add(module);
            } else {
                log.warning("Class " + clazz.getSimpleName() + " has the ModuleInfo annotation but does not implement Module!");
            }
        }

        offeredModules.clear();
        log.info("Loaded " + this.modules.size() + " modules!");
    }

    @Override
    public void disable() {
        modules.forEach(Module::disable);
        modules.clear();
    }

    public static void offerModule(@Nonnull Module module) {
        if (isAcceptingOffers) {
            //noinspection unchecked
            offeredModules.put((Class<Module>) module.getClass(), module);
        } else {
           log.warning("Module offers closed! Make sure you offer the module onLoad!");
        }
    }

    public List<ClassLoader> getModuleClassLoaders() {
        if (offeredModules.size() != 0) {
            // we haven't enabled the module handler yet, grab raw modules
            return offeredModules.keySet().stream().map(Class::getClassLoader).collect(Collectors.toList());
        }
        return modules.stream().map(module -> module.getClass().getClassLoader()).collect(Collectors.toList());
    }

}
