package com.onlinerestaurant.servlet;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * ManageDishesUtils
 * Creates map with dish parameters.
 */
public class ManageDishesUtils {

    public static Map<String, String> convertParamMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        request.getParameterMap().forEach((key, value) -> map.put(key, value[0].trim()));
        map.remove("command");
        return map;
    }
}
