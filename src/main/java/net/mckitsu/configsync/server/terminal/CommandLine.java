package net.mckitsu.configsync.server.terminal;

import lombok.Setter;
import net.mckitsu.configsync.server.terminal.commands.*;
import net.mckitsu.lib.terminal.Terminal;
import net.mckitsu.lib.util.EventHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class CommandLine extends Terminal {
    public final Event event = new Event();
    private BufferedReader input;
    private boolean stopRead = false;

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
    protected void onStart() {
        this.stopRead = false;
        this.input = new BufferedReader(new InputStreamReader(System.in));
        this.getLogger().info("Service starting...");
        this.event.onStart(this);
        this.getLogger().info("Service loading...");
        this.event.onLoad(this);
        this.getLogger().info("Service loaded!");
        this.getLogger().info("Service start finish!");
        this.event.onFinish(this);
    }

    @Override
    protected String onRead() {
        try {
            while(!this.stopRead){
                if(this.input.ready())
                    return this.input.readLine();

                synchronized (this){
                    this.wait(100);
                }
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }

        return "";
    }

    @Override
    protected void onUnknownCommand(String[] args) {
        getLogger().info(String.format("Unknown command '%s' - try 'help'\n", args[0]));
    }

    @Override
    public void stop(){
        super.stop();
        this.stopRead = true;
        synchronized (this){
            this.notify();
        }
        this.getLogger().info("Service stopping...");
        this.event.onStop(this);
        this.getLogger().info("Service stopped!");
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
        this.getLogger().info("Server reload!");
    }

    private void onRestart(Terminal terminal){
        this.getLogger().info("Server restart");
        event.onStop(this);
        event.onStart(this);
        event.onLoad(this);
        event.onFinish(this);
        this.getLogger().info("Server restart finish!");
    }

    private void onStop(Terminal terminal){
        this.stop();
    }

    /* **************************************************************************************
     *  Class Event
     */

    @Setter
    public static class Event extends EventHandler {
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
