package net.mckitsu.configsync.server.sqlite;

import net.mckitsu.configsync.server.sqlite.database.Database;
import net.mckitsu.lib.file.FolderManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SqlDatabaseManager extends FolderManager {
    private final HashMap<String, Database> databases = new HashMap<>();

    public SqlDatabaseManager() {
        super("database");
        super.createDir();
    }

    public void add(String sql, String tableName, Map<String, String> table){
        Database database = this.databases.get(sql);
        if(database == null){
            try {
                database = new Database("database", sql+".db");
                this.databases.put(sql, database);
                database.addTable(tableName, table);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            database.addTable(tableName, table);
        }
    }
}
