package by.alex;

import by.alex.mapper.WagonMapper;
import by.alex.mapper.WagonMapperImpl;
import by.alex.repository.AbstractRepository;
import by.alex.repository.WagonRepository;
import by.alex.repository.impl.WagonRepositoryImpl;
import by.alex.service.WagonService;
import by.alex.service.impl.WagonServiceImpl;
import by.alex.util.FillBase;

import java.util.HashMap;
import java.util.Map;

import static by.alex.constant.ConstantApp.DDL_INITIALIZATION_DROP_PATH;
import static by.alex.constant.ConstantApp.DDL_INITIALIZATION_SCRIPT_PATH;
import static by.alex.constant.ConstantApp.DML_INITIALIZATION_SCRIPT_PATH;

public class Runner {
    private final Map<Class<?>, Object> container = new HashMap<>();

    public Runner() {
        try {
            FillBase.deleteBase(DDL_INITIALIZATION_DROP_PATH);
            FillBase.createDDl(DDL_INITIALIZATION_SCRIPT_PATH);
            FillBase.createDMl(DML_INITIALIZATION_SCRIPT_PATH);
            createObjects();
        } catch (RuntimeException e) {
            FillBase.deleteBase(DDL_INITIALIZATION_DROP_PATH);
            throw new RuntimeException("Ошибка инициализации базы ", e);
        }
    }

    private void createObjects() {

        WagonRepository wagonRepository = new WagonRepositoryImpl();
        container.put(WagonRepository.class, wagonRepository);
        WagonMapper wagonMapper = new WagonMapperImpl();
        container.put(WagonMapper.class, wagonMapper);
        WagonService wagonServiceImpl = new WagonServiceImpl(wagonRepository, wagonMapper);
        container.put(WagonService.class, wagonServiceImpl);

    }

    public Object getObject(String name) {
        return container.get(name);
    }

    public <T> T getObject(Class<T> clazz) {
        return (T) container.get(clazz);
    }
}
