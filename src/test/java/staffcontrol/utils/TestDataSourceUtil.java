package staffcontrol.utils;

import org.apache.commons.dbcp.BasicDataSource;
import staffcontrol.dao.interfaces.FeedbackDAO;

import java.io.InputStream;
import java.util.Properties;

public class TestDataSourceUtil {

    public static BasicDataSource createDataSource() {
        try (InputStream in = FeedbackDAO.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl(config.getProperty("url"));
            dataSource.setUsername(config.getProperty("username"));
            dataSource.setPassword(config.getProperty("password"));
            return dataSource;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
