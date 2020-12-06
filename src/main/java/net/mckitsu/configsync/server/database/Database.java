package net.mckitsu.configsync.server.database;

import net.mckitsu.configsync.protocol.ConfigSyncProtocol;
import net.mckitsu.lib.sqlite.SQLite;
import net.mckitsu.configsync.protocol.*;
import net.mckitsu.lib.sqlite.SQLiteTable;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public abstract class Database {
    private final Map<String, SQLite> sqLites = new HashMap<>();
    private boolean isConnect = false;


    /* **************************************************************************************
     *  Abstract method
     */
    protected abstract Logger getLogger();

    /* **************************************************************************************
     *  Construct method
     */
    public Database(){
        this.getLogger().info("[Database] Protocol version: " + net.mckitsu.configsync.protocol.ConfigSyncProtocol.getVersion());
    }

    /* **************************************************************************************
     *  Override method
     */

    /* **************************************************************************************
     *  Public method
     */
    public void connect(){
        if(isConnect)
            return;

        this.isConnect = true;
        for(DatabaseConfig config : ConfigSyncProtocol.getAllDatabaseConfig()){
            String filePath = String.format("database\\%s.db", config.getSqlName());
            SQLite sqLite = this.sqLites.get(config.getSqlName());
            if(sqLite == null){

                sqLite = new SQLite(String.format("database\\%s.db", config.getSqlName()));

                if(sqLite.connect()){
                    this.getLogger().info("[Database] connect successful " + filePath);
                    this.sqLites.put(config.getSqlName(), sqLite);
                }else{
                    this.getLogger().severe("[Database] connect fail " + filePath);
                    continue;
                }
            }

            SQLiteTable sqLiteTable = convertToSQLiteTable(config);
            SQLite.Status result = sqLite.createTable(sqLiteTable);
            if(result == SQLite.Status.SUCCESS){
                getLogger().info(String.format("[Database] add table [%s] to %s", config.getTableName(), filePath));
            }else{
                getLogger().info(String.format("[Database] found table [%s] from %s", config.getTableName(), filePath));
            }
        }

        this.getLogger().info("[Database] connect");
    }

    public void close(){
        if(!isConnect)
            return;

        this.isConnect = false;

        for(Map.Entry<String, SQLite> entry : this.sqLites.entrySet()){
            entry.getValue().close();
        }

        this.getLogger().info("[Database] close");

        this.sqLites.clear();
    }

    /* **************************************************************************************
     *  protected method
     */

    /* **************************************************************************************
     *  Private method
     */

    private SQLiteTable convertToSQLiteTable(DatabaseConfig config){
        SQLiteTable result = new SQLiteTable();
        result.setTableName(config.getTableName());
        result.setPrimaryKey(config.getPrimaryKey());
        result.setTable(config.getMap());
        return result;
    }
}
