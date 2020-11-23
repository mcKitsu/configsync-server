package net.mckitsu.configsync.server.terminal;

import lombok.Setter;
import net.mckitsu.configsync.server.terminal.commands.*;
import net.mckitsu.lib.terminal.Terminal;
import net.mckitsu.lib.util.EventHandler;

import java.util.Scanner;
import java.util.function.Consumer;

public class CommandLine extends Terminal {
    public final Event event = new Event();
    public final Scanner scanner = new Scanner(System.in);

    /* **************************************************************************************
     *  Abstract method
     */

    /* **************************************************************************************
     *  Construct method
     */
    public CommandLine(){
        this.terminalCommandInit();
    }

    /* **************************************************************************************
     *  Override method
     */
    @Override
    protected boolean onStart() {
        this.getLogger().info("Service starting...");
        this.event.onStart(this);
        this.getLogger().info("Service loading...");
        this.event.onLoad(this);
        this.getLogger().info("Service loaded!");
        this.getLogger().info("Service start finish!");
        this.event.onFinish(this);
        return false;
    }

    @Override
    protected String onRead() {
        return scanner.nextLine();
    }

    @Override
    protected void onUnknownCommand(String[] args) {
        getLogger().info(String.format("Unknown command '%s' - try 'help'\n", args[0]));
    }

    @Override
    public void stop(){
        super.stop();
        event.onStop(this);
    }

    /* **************************************************************************************
     *  Public method
     */

    /* **************************************************************************************
     *  protected method
     */

    /* **************************************************************************************
     *  Private method
     */
    private void terminalCommandInit(){
        add(new CommandHelp(getCommands()));
        add(new CommandReload(this::onReload));
        add(new CommandStop(this::onStop));
        add(new CommandRestart(this::onRestart));
        add(new CommandInfo());
    }

    private void onReload(Terminal terminal){
        this.event.onLoad(terminal);
    }

    private void onRestart(Terminal terminal){

    }

    private void onStop(Terminal terminal){
        this.getLogger().info("Service stopping...");
        this.stop();
        this.getLogger().info("Service stopped!");
    }

    /* **************************************************************************************
     *  Class Event
     */

    @Setter
    public class Event extends EventHandler {
        private Consumer<Terminal> onStart;
        private Consumer<Terminal> onLoad;
        private Consumer<Terminal> onStop;
        private Consumer<Terminal> onFinish;

        protected void onStart(Terminal terminal){
            super.execute(onStart, terminal);
        }

        protected void onLoad(Terminal terminal){
            super.execute(onLoad, terminal);
        }

        protected void onStop(Terminal terminal){
            super.execute(onStop, terminal);
        }

        protected void onFinish(Terminal terminal){
            super.execute(onFinish, terminal);
        }

        public void setEvent(CommandLineEvent event){
            this.setOnStart(event::onStart);
            this.setOnLoad(event::onLoad);
            this.setOnFinish(event::onFinish);
            this.setOnStop(event::onStop);
        }

    }

}
