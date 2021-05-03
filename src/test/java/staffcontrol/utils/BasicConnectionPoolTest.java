package staffcontrol.utils;

import org.junit.Test;
import staffcontrol.util.BasicConnectionPool;
import staffcontrol.util.ConnectionPool;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BasicConnectionPoolTest {

    @Test
    public void whenCalledgetConnection_thenCorrect() throws SQLException {
        ConnectionPool connectionPool = BasicConnectionPool.create();
        assertTrue(connectionPool.getConnection().isValid(1));
        assertFalse(connectionPool.getConnection().isClosed());
    }
}
