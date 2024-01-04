//package by.alex.util;
//
//import by.alex.connector.DataBaseConnector;
//import by.alex.exceptions.CacheException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class FillBase {
//
//    private static final Logger logger = LoggerFactory.getLogger(FillBase.class);
//
//    private static void validatePath(String path) throws CacheException {
//        if (path == null) {
//            throw new CacheException("Путь к файлу свойств не может быть null");
//        }
//    }
//
//
//    private static void executeSQL(String str, String successful, String problemDuringOperation) {
//        try (Connection connection = DataBaseConnector.getInstance().getConnection();
//             Statement statement = connection.createStatement()) {
//            validatePath(str);
//            String ddlScript = readFile(str);
//            statement.executeUpdate(ddlScript);
//            logger.info(successful);
//
//        } catch (SQLException | IOException | CacheException e) {
//            throw new RuntimeException(problemDuringOperation, e);
//        }
//    }
//
//    public static void createDDl(String str) {
//        String successful = "таблицы созданы";
//        String problemDuringOperation = "Проблема в создании таблиц бд";
//        executeSQL(str, successful, problemDuringOperation);
//    }
//
//    public static void createDMl(String str) {
//        String successful = "таблицы заполнены";
//        String problemDuringOperation = "Проблема в заполнении таблиц бд";
//        executeSQL(str, successful, problemDuringOperation);
//    }
//
//    public static void deleteBase(String str) {
//        String successful = "таблицы удалены";
//        String problemDuringOperation = "Проблема в удалении таблиц бд";
//        executeSQL(str, successful, problemDuringOperation);
//    }
//
//
//    public static String readFile(String fileName) throws IOException {
//        StringBuilder content = new StringBuilder();
//        String resource = FillBase.class.getClassLoader().getResource(fileName).getFile();
//        try (BufferedReader reader = new BufferedReader(new FileReader(resource))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                content.append(line);
//                content.append("\n");
//            }
//        }
//        return content.toString();
//    }
//}
//
