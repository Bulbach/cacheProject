package by.alex;

import by.alex.util.FillBase;

import static by.alex.constant.ConstantApp.DDL_INITIALIZATION_DROP_PATH;
import static by.alex.constant.ConstantApp.DDL_INITIALIZATION_SCRIPT_PATH;
import static by.alex.constant.ConstantApp.DML_INITIALIZATION_SCRIPT_PATH;

public class Runner {
    public Runner() {
        try {
            FillBase.deleteBase(DDL_INITIALIZATION_DROP_PATH);
            FillBase.createDDl(DDL_INITIALIZATION_SCRIPT_PATH);
            FillBase.createDMl(DML_INITIALIZATION_SCRIPT_PATH);
//            createObjects();
        } catch (RuntimeException e) {
            FillBase.deleteBase(DDL_INITIALIZATION_DROP_PATH);
            throw new RuntimeException("Ошибка инициализации базы ", e);
        }
    }
}
