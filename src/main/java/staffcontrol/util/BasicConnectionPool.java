package staffcontrol.util;

import lombok.Getter;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Getter
public class BasicConnectionPool implements ConnectionPool {
    private static final int MAX_TIMEOUT = 2;
    private String url;
    private String username;
    private String password;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 10;


    public static BasicConnectionPool create() {
        try (InputStream in = BasicConnectionPool.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            String url = config.getProperty("url");
            String username = config.getProperty("username");
            String password = config.getProperty("password");

            List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                pool.add(createConnection(url, username, password));
            }
            return new BasicConnectionPool(url, username, password, pool);

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private BasicConnectionPool(String url, String user, String password, List<Connection> connectionPool) {
        this.url = url;
        this.username = user;
        this.password = password;
        this.connectionPool = connectionPool;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < INITIAL_POOL_SIZE) {
                connectionPool.add(createConnection(url, username, password));
            } else {
                throw new RuntimeException(
                        "Maximum pool size reached, no available connections!");
            }
        }
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        if (!connection.isValid(MAX_TIMEOUT)) {
            connection = createConnection(url, username, password);
        }
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection(String url, String username, String password)
            throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }

}