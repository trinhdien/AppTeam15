package com.utc.asm_mob_java.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class GsonUtils {

    private static Gson instance;

    public static synchronized Gson getInstance() {
        if (instance == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double originalValue, Type typeOf,
                                             JsonSerializationContext context) {
                    BigDecimal bigValue = BigDecimal.valueOf(originalValue);

                    return new JsonPrimitive(bigValue.toPlainString());
                }
            });

            gsonBuilder.excludeFieldsWithoutExposeAnnotation();
            instance = gsonBuilder.create();
        }

        return instance;
    }

    public static String Object2String(Object obj) {

        return getInstance().toJson(obj);
    }

    public static <T> T String2Object(String json, Class<T> clzz) {
        try {
            return getInstance().fromJson(json, clzz);
        }catch (Exception e){
            Logger.log(GsonUtils.class,e);
        }
        return null;

    }

    public static <T> T String2Object(String json, Type typeOfT) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.fromJson(json, typeOfT);
    }

    public static <T> List<T> String2ListObject(String json, Class<T[]> clazz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        } else {
            T[] t = getInstance().fromJson(json, clazz);
            return Arrays.asList(t);
        }

    }

    public static <T> List<T> String2ListObject(String json,Type type) {
        if (TextUtils.isEmpty(json)) {
            return null;
        } else {
            return getInstance().fromJson(json, type);
        }



    }

    public static <T> T String2Object(String json, TypeToken<T> tTypeToken) {
        return getInstance().fromJson(json, tTypeToken.getType());
    }
}