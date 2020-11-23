package net.mckitsu.configsync.server.terminal.commands;

import net.mckitsu.lib.terminal.Terminal;
import net.mckitsu.lib.terminal.TerminalCommand;

import java.util.function.Consumer;

public class CommandReload implements TerminalCommand {
    private final Consumer<Terminal> onReload;
    /* **************************************************************************************
     *  Abstract method
     */

    /* **************************************************************************************
     *  Construct method
     */

    public CommandReload(Consumer<Terminal> onReload){
        this.onReload = onReload;
    }

    /* **************************************************************************************
     *  Override method
     */

    @Override
    public String getCommand() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloading resource config.";
    }

    @Override
    public String getHelp() {
        return String.format(" - %s\n", this.getDescription()) +
                String.format("   - %s", this.getCommandFormat());
    }

    @Override
    public boolean handle(Terminal terminal, String[] args) {
        onReload.accept(terminal);
        return true;
    }

    @Override
    public String getCommandFormat() {
        return "reload";
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
