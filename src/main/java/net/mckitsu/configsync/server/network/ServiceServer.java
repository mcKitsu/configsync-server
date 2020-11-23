package net.mckitsu.configsync.server.network;

import net.mckitsu.lib.network.net.NetClient;
import net.mckitsu.lib.network.net.NetServer;

import java.net.InetSocketAddress;

public class ServiceServer extends NetServer {
    public ServiceServer(byte[] verifyKey, InetSocketAddress address) {
        super(verifyKey);
        super.start(address);
    }

    protected void onAccept(NetClient netClient) {

    }
}
