package by.alex.configuration;

import by.alex.cache.AbstractCache;
import by.alex.cache.impl.LFUCache;
import by.alex.cache.impl.LRUCache;
import by.alex.dto.WagonDto;
import by.alex.util.YamlPropertySourceFactory;
import by.alex.util.print.PrintInfo;
import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.UUID;


@Configuration
@WebListener
@ComponentScan("by.alex")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
@Slf4j
public class AppConfig {

    @Value("${spring.database.driver-class-name}")
    private String DATABASE_DRIVER;
    @Value("${spring.database.url}")
    private String DATABASE_URL;
    @Value("${spring.database.username}")
    private String DATABASE_USER;
    @Value("${spring.database.password}")
    private String DATABASE_PASSWORD;

    @Value("${spring.cache.algorithm}")
    private String CACHE_ALGORITHM;
    @Value("${spring.cache.max-size}")
    private int CAPACITY_KEY;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource getDataSource() throws SQLException, PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(DATABASE_DRIVER);
        dataSource.setJdbcUrl(DATABASE_URL);
        dataSource.setUser(DATABASE_USER);
        dataSource.setPassword(DATABASE_PASSWORD);
        dataSource.setInitialPoolSize(5);
        dataSource.setMinPoolSize(3);
        dataSource.setMaxPoolSize(10);

        return dataSource;
    }

    @Bean
    JdbcTemplate jdbcTemplate() throws PropertyVetoException, SQLException {
        return new JdbcTemplate(getDataSource());
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:database/changelog.yaml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

    @Bean
    public PrintInfo printInfo() {
        return new PrintInfo();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public AbstractCache<UUID, WagonDto> cache() {
        return "LFU".equals(CACHE_ALGORITHM)
                ? new LFUCache<>(CAPACITY_KEY)
                : new LRUCache<>(CAPACITY_KEY);
    }
}
