package net.mckitsu.configsync.server.sqlite;

import net.mckitsu.lib.file.FolderManager;

public class SqlDatabaseManager extends FolderManager {
    public SqlDatabaseManager() {
        super("database");
        super.createDir();
    }
}
