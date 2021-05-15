package staffcontrol;

import staffcontrol.util.ConnectionUtil;
import staffcontrol.dao.impl.jdbc.FeedbackDaoImpl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionTestUtil extends ConnectionUtil {


    @Override
    public Connection getConnection() {
        try (InputStream in = FeedbackDaoImpl.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
