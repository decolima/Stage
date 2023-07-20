/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.service;

/**
 *
 *
 * @author andrelima
 */

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlLiteEntityManager {
    private final Connection connection;
    private final String databasePath;

    public SqlLiteEntityManager() throws SQLException {
        databasePath = new File(SqlLiteEntityManager.class.getProtectionDomain().getCodeSource().getLocation().getPath())
                .getParentFile().getPath() + File.separator + "blescan.db";
        connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
    }

    public void createTable(Class<?> entityClass) throws SQLException {
        String tableName = entityClass.getSimpleName();
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");

        // Obter os campos da classe da entidade
        java.lang.reflect.Field[] fields = entityClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            java.lang.reflect.Field field = fields[i];
            String fieldName = field.getName();
            String fieldType = field.getType().getSimpleName();

            query.append(fieldName).append(" ").append(getSqliteType(fieldType));

            if (i < fields.length - 1) {
                query.append(", ");
            }
        }

        query.append(")");

        try (Statement statement = connection.createStatement()) {
            statement.execute(query.toString());
        }
    }

    private String getSqliteType(String javaType) {
        switch (javaType) {
            case "String":
                return "TEXT";
            case "int":
                return "INTEGER";
            case "double":
                return "REAL";
            case "LocalDateTime":
                return "DATETIME"; // ou "INTEGER" se preferir armazenar como timestamp
            // Adicione mais tipos de dados conforme necessário
            default:
                return "TEXT";
        }
    }

    public void close() throws SQLException {
        connection.close();
    }

    public String getDatabasePath() {
        return databasePath;
    }
}
