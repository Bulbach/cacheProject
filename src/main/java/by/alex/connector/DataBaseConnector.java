package by.alex.connector;

import by.alex.util.ApplicationProperties;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.Data;
import javax.sql.DataSource;
import static by.alex.constant.ConstantApp.DATABASE_DRIVER_KEY;
import static by.alex.constant.ConstantApp.DATABASE_URL_KEY;
import static by.alex.constant.ConstantApp.DATABASE_USER_KEY;
import static by.alex.constant.ConstantApp.DATABASE_PASSWORD_KEY;
import java.sql.Connection;
import java.sql.SQLException;


@Data
public final class DataBaseConnector {

    private static final DataBaseConnector INSTANCE = new DataBaseConnector();

    private static final String DATABASE_DRIVER;
    private static final String DATABASE_URL;
    private static final String DATABASE_USER;
    private static final String DATABASE_PASSWORD;

    static {
        try {

            DATABASE_DRIVER = ApplicationProperties.prop().get(DATABASE_DRIVER_KEY);
            DATABASE_URL = ApplicationProperties.prop().get(DATABASE_URL_KEY);
            DATABASE_USER = ApplicationProperties.prop().get(DATABASE_USER_KEY);
            DATABASE_PASSWORD = ApplicationProperties.prop().get(DATABASE_PASSWORD_KEY);

            Class.forName(DATABASE_DRIVER);

        } catch (Exception e) {

            System.err.println("Error initialization DataBaseConnector: " + e.getMessage());
            throw new RuntimeException("Error initialization DataBaseConnector: " + e.getMessage());
        }
    }

    private DataBaseConnector() {
    }

    public static DataBaseConnector getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(DATABASE_URL);
        dataSource.setUser(DATABASE_USER);
        dataSource.setPassword(DATABASE_PASSWORD);
        dataSource.setInitialPoolSize(5);
        dataSource.setMinPoolSize(3);
        dataSource.setMaxPoolSize(10);

        return dataSource.getConnection();
    }

    public DataSource getDataSource() throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(DATABASE_URL);
        dataSource.setUser(DATABASE_USER);
        dataSource.setPassword(DATABASE_PASSWORD);
        dataSource.setInitialPoolSize(5);
        dataSource.setMinPoolSize(3);
        dataSource.setMaxPoolSize(10);

        return dataSource;
    }
}



