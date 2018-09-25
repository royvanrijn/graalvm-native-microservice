package com.royvanrijn.graal.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

    public static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }
}
