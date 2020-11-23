package net.mckitsu.configsync.server.terminal.commands;

import net.mckitsu.lib.terminal.Terminal;
import net.mckitsu.lib.terminal.TerminalCommand;

import java.util.function.Consumer;

public class CommandInfo implements TerminalCommand {
    /* **************************************************************************************
     *  Abstract method
     */

    /* **************************************************************************************
     *  Construct method
     */

    /* **************************************************************************************
     *  Override method
     */

    @Override
    public String getCommand() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "About this program information";
    }

    @Override
    public String getHelp() {
        return String.format(" - %s\n", this.getDescription()) +
                String.format("   - %s", this.getCommandFormat());
    }

    @Override
    public boolean handle(Terminal terminal, String[] args) {
        terminal.getLogger().info("Server version");
        return true;
    }

    @Override
    public String getCommandFormat() {
        return "info";
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
