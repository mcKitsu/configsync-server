package net.mckitsu.configsync.server.yaml.config;

import lombok.Data;

@Data
public class SocketAddress {
    private String ipAddress;
    private int port;
    private String verifyKey;
}
