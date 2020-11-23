package net.mckitsu.configsync.server.terminal.commands;

import net.mckitsu.lib.terminal.Terminal;
import net.mckitsu.lib.terminal.TerminalCommand;

import java.util.function.Consumer;

public class CommandStop implements TerminalCommand {
    private final Consumer<Terminal> onStop;
    /* **************************************************************************************
     *  Abstract method
     */

    /* **************************************************************************************
     *  Construct method
     */

    public CommandStop(Consumer<Terminal> onStop){
        this.onStop = onStop;
    }

    /* **************************************************************************************
     *  Override method
     */
    @Override
    public String getCommand() {
        return "stop";
    }

    @Override
    public String getDescription() {
        return "Stop service.";
    }

    @Override
    public String getHelp() {
        return String.format(" - %s\n", this.getDescription()) +
                String.format("   - %s", this.getCommandFormat());
    }

    @Override
    public boolean handle(Terminal terminal, String[] args) {
        this.onStop.accept(terminal);
        return true;
    }

    @Override
    public String getCommandFormat() {
        return "stop";
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
}
