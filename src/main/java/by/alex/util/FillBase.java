package by.alex.util;

import by.alex.exceptions.CacheException;
import by.alex.connector.DataBaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class FillBase {

    private static final Logger logger = LoggerFactory.getLogger(FillBase.class);
    private static void validatePath(String path) throws CacheException {
        if (path == null) {
            throw new CacheException("Путь к файлу свойств не может быть null");
        }
    }

    public static void createDDl(String str){
        try(Connection connection = DataBaseConnector.getInstance().getConnection();
            Statement statement = connection.createStatement()) {
            validatePath(str);
            String ddlScript = readFile(str);
            statement.executeUpdate(ddlScript);
            logger.info("таблицы созданы");

        } catch (SQLException | IOException | CacheException e)
        {
            throw new RuntimeException("Проблема в создании таблиц бд",e);
        }
    }
    public static void createDMl(String str){
        try(Connection connection = DataBaseConnector.getInstance().getConnection();
            Statement statement = connection.createStatement()) {
            validatePath(str);
            String ddlScript = readFile(str);
            statement.executeUpdate(ddlScript);
            logger.info("таблицы созданы");

        } catch (SQLException | IOException | CacheException e)
        {
            throw new RuntimeException("Проблема в создании таблиц бд",e);
        }
    }
    public static void deleteBase(String str){
        try(Connection connection = DataBaseConnector.getInstance().getConnection();
            Statement statement = connection.createStatement()) {
            validatePath(str);
            String ddlScript = readFile(str);
            statement.executeUpdate(ddlScript);
            logger.info("таблицы удалены");

        } catch (SQLException | IOException | CacheException e)
        {
            throw new RuntimeException("Проблема в удалении таблиц бд",e);
        }
    }


    private static String readFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append("\n");
            }
        }
        return content.toString();
    }
}

