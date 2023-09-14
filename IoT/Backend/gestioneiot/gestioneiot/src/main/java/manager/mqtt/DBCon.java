package manager.mqtt;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBCon {

    private static ThreadLocal<Connection> connectionThreadLocal = ThreadLocal.withInitial(() -> null);
    private static final Object lock = new Object();

    private DBCon() {
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = connectionThreadLocal.get();
        if (connection == null || connection.isClosed() || !connection.isValid(120)) {
            synchronized (lock) { // Bloqueia a seção crítica para uma thread de cada vez
                if (connection == null || connection.isClosed() || !connection.isValid(5)) {
                    Properties properties = loadProperties("config.properties");

                    String url = properties.getProperty("db.url");
                    String driver = properties.getProperty("db.driver");
                    String user = properties.getProperty("db.user");
                    String password = properties.getProperty("db.pwr");

                    try {
                        Class.forName(driver);
                        connection = DriverManager.getConnection(url, user, password);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return connection;
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            }

            if (stmt != null) {
                stmt.close();
            }

            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try (InputStream input = DBCon.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
