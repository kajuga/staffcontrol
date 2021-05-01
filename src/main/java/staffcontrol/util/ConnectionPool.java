package staffcontrol.util;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    Connection getConnection() throws SQLException;
    boolean releaseConnection(Connection connection);
    String getUrl();
    String getUsername();
    String getPassword();
    public void shutdown() throws SQLException;
}