package com.meolunr.edittextcolorhelper;

import java.lang.reflect.Field;

/**
 * Created by Meolunr on 2017/2/12
 * Email meolunr@gmail.com
 */
class ReflectUtils {

    static Field getDeclaredField(Class clazz, String fieldName) {
        Field field = null;

        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return field;
    }

    static int getIntField(Class clazz, String fieldName, Object obj) {
        Field field = getDeclaredField(clazz, fieldName);

        try {
            return field.getInt(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    static void setObjectField(Class clazz, String fieldName, Object obj, Object value) {
        try {
            Field field = getDeclaredField(clazz, fieldName);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}