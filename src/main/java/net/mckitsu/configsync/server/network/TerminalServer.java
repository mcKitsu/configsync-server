package net.mckitsu.configsync.server.network;

import net.mckitsu.lib.remoteshell.RemoteShellServer;
import net.mckitsu.lib.terminal.TerminalCommand;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.logging.Logger;

public class TerminalServer extends RemoteShellServer {

    /* **************************************************************************************
     *  Abstract method
     */

    /* **************************************************************************************
     *  Construct method
     */

    public TerminalServer(byte[] verifyKey, InetSocketAddress address) {
        super(verifyKey, address);
    }

    /* **************************************************************************************
     *  Override method
     */

    @Override
    protected Map<String, TerminalCommand> getCommands() {
        return null;
    }

    @Override
    protected Logger getLogger() {
        return null;
    }

    @Override
    protected boolean onVerifyToken(byte[] token) {
        this.getLogger().info("Token is " + new String(token));
        return true;
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
