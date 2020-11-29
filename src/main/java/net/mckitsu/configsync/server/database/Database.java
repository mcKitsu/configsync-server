package net.mckitsu.configsync.server.database;

import net.mckitsu.configsync.protocol.ConfigSyncProtocol;
import net.mckitsu.lib.sqlite.SQLite;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public abstract class Database {
    private final Map<String, Map<String, String>> sqlTableConfigs = ConfigSyncProtocol.getAllTables();
    private final Map<String, SQLite> sqLites = new HashMap<>();
    /* **************************************************************************************
     *  Abstract method
     */
    protected abstract Logger getLogger();

    /* **************************************************************************************
     *  Construct method
     */
    public Database(){
        this.getLogger().info("Protocol version: " + net.mckitsu.configsync.protocol.ConfigSyncProtocol.getVersion());

        for(Map.Entry<String, Map<String, String>> entry : sqlTableConfigs.entrySet()){
            this.getLogger().info("Protocol add sql table : " + entry.getKey());
        }
    }

    /* **************************************************************************************
     *  Override method
     */

    /* **************************************************************************************
     *  Public method
     */

    /* **************************************************************************************
     *  protected method
     */

    /* **************************************************************************************
     *  Private method
     */
    private void initSQLiteTable(){

    }

    private void sqLiteConnect(){

    }
}
