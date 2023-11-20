package by.alex.validation;

import by.alex.exceptions.CacheException;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationEntity {

    public static boolean validate(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Validation.class)) {
                try {
                    String fieldName = field.getName();
                    Field fieldDto = object.getClass().getDeclaredField(fieldName);
                    Validation validation = field.getAnnotation(Validation.class);
                    String regex = validation.regex();
                    Pattern pattern = Pattern.compile(regex);
                    Object valueField = null;
                    fieldDto.setAccessible(true);
                    valueField = fieldDto.get(object);
                    Matcher matcher = pattern.matcher(valueField.toString());
                    if (!matcher.find()) {
                        return false;
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new CacheException(e.getMessage());
                }
            }
        }
        return true;
    }
}
