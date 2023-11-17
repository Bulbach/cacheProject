package by.alex.util;

import by.alex.exceptions.CacheException;
import com.google.gson.Gson;

import java.io.Serializable;

public class TransformationObject {

    private Gson gson;

    public TransformationObject() {
        this.gson = new Gson();
    }

    public String objectToString(Serializable object) throws CacheException {
        try {

            return gson.toJson(object);

        } catch (Exception e) {
            throw new CacheException("Не удалась cериализация объекта");
        }
    }

    public Object stringToObject(String str) throws CacheException {

        try {

            return gson.fromJson(str,Object.class);

        } catch (Exception e) {
            throw new CacheException("Не удалась деcериализация объект");
        }
    }

}
