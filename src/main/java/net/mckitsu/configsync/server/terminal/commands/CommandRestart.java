package net.mckitsu.configsync.server.terminal.commands;


import net.mckitsu.lib.terminal.Terminal;
import net.mckitsu.lib.terminal.TerminalCommand;

import java.util.function.Consumer;

public class CommandRestart implements TerminalCommand {
    private final Consumer<Terminal> onRestart;
    /* **************************************************************************************
     *  Abstract method
     */

    /* **************************************************************************************
     *  Construct method
     */

    public CommandRestart(Consumer<Terminal> onRestart){
        this.onRestart = onRestart;
    }

    /* **************************************************************************************
     *  Override method
     */

    @Override
    public String getCommand() {
        return "restart";
    }

    @Override
    public String getDescription() {
        return "Restart service.";
    }

    @Override
    public String getHelp() {
        return String.format(" - %s\n", this.getDescription()) +
                String.format("   - %s", this.getCommandFormat());
    }

    @Override
    public boolean handle(Terminal terminal, String[] args) {
        return true;
    }

    @Override
    public String getCommandFormat() {
        return "restart";
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
