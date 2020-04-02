package me.frostythedev.frostengine.modules;

import me.frostythedev.frostengine.bukkit.module.Module;

public class ModuleCollectable extends Module {

    private static ModuleCollectable inst;

    public static ModuleCollectable get() {
        return inst;
    }

    private CollectableManager cm;

    public ModuleCollectable() {
        super("collectables", "Fun gadget addon with collecting element", "1.0.0", "frostythedev");
    }

    @Override
    public void onModuleEnable() {
        inst = this;

        saveDefaultConfig();

        this.cm = new CollectableManager();
        this.cm.loadGadget(new GadgetExample());

        this.addCommand(new CmdGadgets());
    }

    public CollectableManager getManager() {
        return cm;
    }
}
