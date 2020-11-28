package net.mckitsu.configsync.server.sqlite;

import lombok.Data;

import java.util.Map;

@Data
public class SqlConfig {
    private String sql;
    private String tableName;
    private String primaryKey;
    private Map<String, String> table;
}
