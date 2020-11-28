package net.mckitsu.configsync.server.sqlite.database;


import net.mckitsu.lib.file.FileManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import net.mckitsu.lib.file.YamlManager;

public class Database extends FileManager {
    private final HashMap<String, Map<String, String>> tables = new HashMap<>();
    private final YamlManager<Map<String, String>> yamlManager = new YamlManager<>();
    private Connection connection;
    private Statement statement;

    public Database(String dirPath, String fileName) throws SQLException {
        super(dirPath, fileName);
        boolean isExists = super.exists();
        this.connection = connect(getDirPath() + "\\" + getFileName());
        this.statement = connection.createStatement();
        if(!isExists){
            createTableList();
        }

    }

    public Database(FileManager file){
        super(file.getDirPath(), file.getFileName());

        if(!exists())
            createFile();
    }

    public void addTable(String tableName, Map<String, String> table){
        this.tables.put(tableName, table);
        try {
            writeTableFormat(tableName, yamlManager.dump(table));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(yamlManager.dump(table));
    }

    private void createTableList() throws SQLException {
        String sql = "CREATE TABLE COMPANY " +
                "(NAME CHAR(50) PRIMARY KEY     NOT NULL," +
                " TAB                TEXT)";
        this.statement.executeUpdate(sql);
    }

    private void writeTableFormat(String tableName, String table) throws SQLException {
        String sql = "INSERT INTO COMPANY (NAME,TAB) VALUES (" +
                String.format("'%s', '%s'", tableName, table) +
                ");";

        this.statement.executeUpdate(sql);
    }

    private Connection connect(String filePath) throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + filePath);
    }
}
