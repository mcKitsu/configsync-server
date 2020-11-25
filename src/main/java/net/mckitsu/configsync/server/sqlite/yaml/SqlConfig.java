package net.mckitsu.configsync.server.sqlite.yaml;

import lombok.Data;

import java.util.Map;

@Data
public class SqlConfig {
    private String sql;
    private String tableName;
    private Map<String, String> table;
}
