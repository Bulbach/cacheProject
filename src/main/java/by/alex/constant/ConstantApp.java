package by.alex.constant;
public interface ConstantApp {
    String DATABASE_PROPERTIES_FILE_PATH = "application.yml";

    String DATABASE_DRIVER_KEY = "driver-class-name";
    String DATABASE_URL_KEY = "url";
    String DATABASE_USER_KEY = "username";
    String DATABASE_PASSWORD_KEY = "password";



    String DDL_INITIALIZATION_SCRIPT_PATH = "src/main/resources/database/DDL_myBase.sql";
    String DML_INITIALIZATION_SCRIPT_PATH = "src/main/resources/database/DML_myBase.sql";
    String DDL_INITIALIZATION_DROP_PATH = "src/main/resources/database/Drop_myBase.sql";
}
