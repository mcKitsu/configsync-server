package net.mckitsu.configsync.server.terminal.commands;


import net.mckitsu.lib.terminal.Terminal;
import net.mckitsu.lib.terminal.TerminalCommand;

import java.util.Map;

public class CommandHelp implements TerminalCommand {
    private final Map<String, TerminalCommand> commandMap;

    public CommandHelp(Map<String, TerminalCommand> commandMap){
        this.commandMap = commandMap;
    }

    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Command list and information.";
    }

    @Override
    public String getHelp() {
        return String.format(" - %s\n", this.getDescription()) +
                String.format("   - %s", this.getCommandFormat());
    }

    @Override
    public boolean handle(Terminal terminal, String[] args) {
        StringBuilder logout = new StringBuilder();

        if(args.length>=2){
            logout.append(String.format("%s %s\n", args[0], args[1]));
        }else{
            logout.append(String.format("%s\n",args[0]));
        }


        if(args.length == 1){
            for (Map.Entry<String, TerminalCommand> entry : this.commandMap.entrySet()) {
                logout.append(String.format(" - %s, %s\n", entry.getValue().getCommand(), entry.getValue().getDescription()));
            }
        }else {
            TerminalCommand command = this.commandMap.get(args[1].toLowerCase());

            if(command != null){
                logout.append(command.getHelp());
                logout.append('\n');
            } else
                logout.append(String.format("Unknown command '%s' - try 'help'\n", args[1]));
        }

        terminal.getLogger().info(logout.toString());
        return true;
    }

    @Override
    public String getCommandFormat() {
        return "help <command>";
    }
}
