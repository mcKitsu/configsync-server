package net.mckitsu.configsync.server;

import net.mckitsu.configsync.server.sqlite.Sqlite;

public class Main {
    public static final String format = "%1$tF %1$tT [%4$s] %5$s%6$s%n";

    public static void main(String[] args){
        System.setProperty("java.util.logging.SimpleFormatter.format", format);

        Sqlite sqlite = new Sqlite();

        //Server server = new Server();
        //server.start();
    }
}
