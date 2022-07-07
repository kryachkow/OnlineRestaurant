package com.onlinerestaurant.servlet;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper for sorting and pagination.
 */
public class SortingPaginationHelper {

    int pageSize;
    String tableName;
    String listName;
    Map<String, String> sortMap;
    int pageNumber;

    public SortingPaginationHelper(int pageSize, String tableName, String listName){
        this.pageSize = pageSize;
        this.tableName = tableName;
        this.listName = listName;
    }

    /**
     * @param request HttpServletRequest
     * @return sortMap based on sortMap from session and parameters given in request
     */
    public Map<String, String> getSortMap(HttpServletRequest request) {
        Map<String, String[]> parameterMap = new HashMap<>(request.getParameterMap());
        parameterMap.remove("command");
        Map<String, String> sortMap = (Map<String, String>) request.getSession().getAttribute("sortMap");
        if (!parameterMap.isEmpty()) {
            parameterMap.forEach((key, value) -> sortMap.put(key, value[0]));
        }
        this.sortMap = sortMap;
        return sortMap;
    }

    /**
     * @return pageMap based on sortMap and builderParameters
     */
    public Map<String, Integer> getPageMap(){
        Map<String, Integer> pageMap = new HashMap<>();
        pageNumber = 1;
        if(sortMap.containsKey( tableName + "PageNumber")) {
            pageNumber = Integer.parseInt(sortMap.get( tableName + "PageNumber"));
        }
        pageMap.put("offset", (pageNumber - 1) * pageSize);
        pageMap.put("limit", pageSize + 1 );
        return pageMap;
    }

    /**
     * @param list itemList
     * @param request request to set pagination attributes
     * @return false if list is empty and pageNumber != 1, true otherwise.
     */
    public boolean handlePages(List<?> list, HttpServletRequest request) {
        if(list.isEmpty() && pageNumber != 1){
            return false;
        }
        boolean hasNextPage = list.size() > pageSize;
        if(hasNextPage) {
            list.remove(list.size() - 1);
        }
        request.getSession().setAttribute(tableName +"PageNumber", pageNumber);
        request.getSession().setAttribute("hasNextPage", hasNextPage);
        request.setAttribute(listName, list);
        return true;
    }
}
