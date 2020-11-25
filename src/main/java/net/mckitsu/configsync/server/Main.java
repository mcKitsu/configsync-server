package net.mckitsu.configsync.server;

import net.mckitsu.configsync.server.sqlite.SqlConfigManager;
import net.mckitsu.configsync.server.sqlite.SqlDatabaseManager;
import net.mckitsu.configsync.server.sqlite.yaml.SqlConfig;

public class Main {
    public static final String format = "%1$tF %1$tT [%4$s] %5$s%6$s%n";

    public static void main(String[] args){
        System.setProperty("java.util.logging.SimpleFormatter.format", format);

        SqlConfigManager cfg = new SqlConfigManager();
        SqlDatabaseManager db = new SqlDatabaseManager();
        cfg.loadConfig();

        for (SqlConfig config: cfg.getConfigs()) {
            System.out.println(config.getSql());
            System.out.println(config.getTableName());
            System.out.println(config.getTable());
            System.out.println();
        }

        //Server server = new Server();
        //server.start();
    }
}
