package net.mckitsu.configsync.server.sqlite;

public class Sqlite {
    private final SqlConfigManager sqlConfigs = new SqlConfigManager();
    private final SqlDatabaseManager sqlDatabase = new SqlDatabaseManager();

    public Sqlite(){
        sqlConfigs.loadConfig();
        for (SqlConfig config : sqlConfigs.getConfigs()){
            sqlDatabase.add(config.getSql(), config.getTableName(), config.getTable());
        }
    }

}
