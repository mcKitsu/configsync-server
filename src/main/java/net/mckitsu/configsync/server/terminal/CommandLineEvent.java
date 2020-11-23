package net.mckitsu.configsync.server.terminal;

import net.mckitsu.lib.terminal.Terminal;

public interface CommandLineEvent {
    void onStart(Terminal terminal);

    void onLoad(Terminal terminal);

    void onFinish(Terminal terminal);

    void onStop(Terminal terminal);
}
