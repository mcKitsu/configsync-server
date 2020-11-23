package net.mckitsu.configsync.server.yaml;

import lombok.Data;
import net.mckitsu.configsync.server.yaml.config.*;

@Data
public class Config {
    private SocketAddress service;
    private SocketAddress terminal;
}
