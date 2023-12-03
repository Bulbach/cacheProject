package by.alex.util.print;

import by.alex.dto.WagonDto;
import by.alex.exceptions.CacheException;
import by.alex.util.print.adaptor.WagonDtoAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TransformationObject {

    private Gson gson;

    public TransformationObject() {
        this.gson = new Gson();
    }

    public String objectToString(Object object) throws CacheException {
        try {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(WagonDto.class, new WagonDtoAdapter());
            Gson gson = gsonBuilder.setPrettyPrinting().create();
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
