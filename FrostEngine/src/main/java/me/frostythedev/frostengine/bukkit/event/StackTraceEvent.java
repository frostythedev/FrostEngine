package me.frostythedev.frostengine.bukkit.event;

import me.frostythedev.frostengine.bukkit.module.Plugins;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StackTraceEvent extends Event {
    private static StackTraceErrorHandler errorHandler = StackTraceErrorHandler.getInstance();

    private static final HandlerList handler = new HandlerList();
    private Throwable throwable;

    public StackTraceEvent(Throwable ex) {
        this.throwable = ex;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    public static HandlerList getHandlerList() {
        return handler;
    }

    public Throwable getException() {
        return throwable;
    }

    public static void call(Throwable throwable) {
       // Chat.messageConsole("Calling Stack Trace Event!");
        StackTraceEvent event = new StackTraceEvent(throwable);
        Plugins.callEvent(event);
        handle(event);
    }

    public static void handle(StackTraceEvent e) {
        Configuration config = FEPlugin.get().getConfig();
        /*
        Set<Player> debuggingPlayers = PlayerUtil.getAllDebugging();
        Throwable eventException = e.getException();
        //If the books for stack-tracing are enabled, then give one to all the debugging player
        if (config.enableStackTraceBook()) {
            ItemStack exceptionBook = Debugger.createExceptionBook(eventException);
            debuggingPlayers.forEach(p -> PlayerUtil.giveItem(p, exceptionBook));
        }

        //If the stack trace messages are to be sent in chat, send em!
        if (config.enableStackTraceChat()) {
            String[] exceptionMessages = Messages.exceptionInfo(eventException);
            //For every player that's debugging, send them the exception-info message
            debuggingPlayers.forEach(p -> Chat.message(p.getPlayer(), exceptionMessages));
        }
         */
    }

    public static class StackTraceErrorHandler implements Thread.UncaughtExceptionHandler {
        private static StackTraceErrorHandler instance = null;

        public static StackTraceErrorHandler getInstance() {
            if (instance == null) {
                instance = new StackTraceErrorHandler();
            }
            return instance;
        }

        protected StackTraceErrorHandler() {

        }

       /*
        public static void register() {
            Thread.setDefaultUncaughtExceptionHandler(getInstance());
            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();

            Thread serverThread = Reflect.on(server).get("serverThread");
            serverThread.setUncaughtExceptionHandler(getInstance());

            Thread primaryThread = Reflect.on(server).get("primaryThread");
            primaryThread.setUncaughtExceptionHandler(getInstance());
           // Chat.messageConsole("Registered uncaught exception handler for serverThread and PrimaryThread");
        }
        */

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            try {
                StackTraceEvent.call(e);
            } catch (Throwable th) {
                e.printStackTrace();
            }
        }
    }
}
