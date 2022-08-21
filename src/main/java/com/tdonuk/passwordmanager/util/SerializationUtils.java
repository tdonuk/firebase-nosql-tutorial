package com.tdonuk.passwordmanager.util;

import com.tdonuk.passwordmanager.domain.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SerializationUtils {
    public static HashMap<String, Object> toHashMapWithoutCollectionFields(UserEntity entity) throws IllegalAccessException {
        HashMap<String, Object> map = new HashMap<>();

        Field[] fields = UserEntity.class.getDeclaredFields();

        for(Field field : fields) {
            if(! Collection.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(entity));
                field.setAccessible(false);
            }
        }
        return map;
    }
}
