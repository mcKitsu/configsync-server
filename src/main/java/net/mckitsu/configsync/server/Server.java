package net.mckitsu.configsync.server;

import net.mckitsu.configsync.server.database.Database;
import net.mckitsu.configsync.server.loader.ResourceLoader;
import net.mckitsu.configsync.server.network.ServiceServer;

import net.mckitsu.configsync.server.network.TerminalServer;
import net.mckitsu.configsync.server.terminal.CommandLine;
import net.mckitsu.configsync.server.terminal.CommandLineEvent;
import net.mckitsu.lib.terminal.Terminal;
import net.mckitsu.lib.terminal.TerminalCommand;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.logging.Logger;

public class Server implements CommandLineEvent {
    private final ResourceLoader resource = new ResourceLoader();
    private final CommandLine commandLine = new CommandLine();
    private ServiceServer serviceServer;
    private TerminalServer remoteServer;
    private Database database;

    /* **************************************************************************************
     *  Construct method
     */

    public Server(){
        commandLine.event.setEvent(this);
    }

    /* **************************************************************************************
     *  Override method
     */
    @Override
    public void onStart(Terminal terminal) {
        if(resource.config.load()) {
            terminal.getLogger().info("Service");
        }else{
            terminal.getLogger().severe("Config.yml load fail!");
        }
    }

    @Override
    public void onLoad(Terminal terminal) {

    }

    @Override
    public void onFinish(Terminal terminal) {
        database = new Database() {
            @Override
            protected Logger getLogger() {
                return terminal.getLogger();
            }
        };

        serviceServer = constructServiceServer(getServiceVerifyKey(), getServiceAddress());
        try {
            terminal.getLogger().info("Start ServiceServer as IP: " + serviceServer.getLocalAddress());
        } catch (IOException e) {
            terminal.getLogger().info("ServiceServer start fail. " + e);
        }

        this.remoteServer = constructRemoteShellServer(getTerminalVerifyKey(), getTerminalAddress());

        try {
            terminal.getLogger().info("Start RemoteServer as IP: " + remoteServer.getLocalAddress());
        } catch (IOException e) {
            terminal.getLogger().info("RemoteServer start fail. " + e);
        }
    }

    @Override
    public void onStop(Terminal terminal) {
        this.serviceServer.stop();
        this.remoteServer.stop();
    }

    /* **************************************************************************************
     *  Public method
     */
    public void start(){
        this.commandLine.start();
    }

    /* **************************************************************************************
     *  protected method
     */

    /* **************************************************************************************
     *  Private method
     */

    /* **************************************************************************************
     *  Private method - getter
     */

    private InetSocketAddress getServiceAddress(){
        return new InetSocketAddress(
                resource.config.yaml.getService().getIpAddress(),
                resource.config.yaml.getService().getPort());
    }

    private byte[] getServiceVerifyKey(){
        return resource.config.yaml.getService().getVerifyKey().getBytes();
    }

    private InetSocketAddress getTerminalAddress(){
        return new InetSocketAddress(
                resource.config.yaml.getTerminal().getIpAddress(),
                resource.config.yaml.getTerminal().getPort());
    }

    private byte[] getTerminalVerifyKey(){
        return resource.config.yaml.getTerminal().getVerifyKey().getBytes();
    }

    /* **************************************************************************************
     *  Private method - construct
     */

    private TerminalServer constructRemoteShellServer(byte[] verifyKey, InetSocketAddress address){
        return new TerminalServer(verifyKey, address) {

            @Override
            protected Map<String, TerminalCommand> getCommands() {
                return Server.this.commandLine.getCommands();
            }

            @Override
            protected Logger getLogger() {
                return Server.this.commandLine.getLogger();
            }
        };
    }

    private ServiceServer constructServiceServer(byte[] verifyKey, InetSocketAddress address){
      return new ServiceServer(verifyKey, address);
    };
}
