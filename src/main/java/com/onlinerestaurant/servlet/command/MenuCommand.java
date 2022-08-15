package com.onlinerestaurant.servlet.command;

import com.onlinerestaurant.db.entity.Dish;
import com.onlinerestaurant.db.service.DishService;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.Paths;
import com.onlinerestaurant.servlet.SortingPaginationHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Menu Command.
 * Gives dish list by given sorting and pagination parameters.
 * Sends to menu.jsp.
 * Stores sorting and pagination parameters in session.
 * When list is empty and menuPageNumber != 1 executes Menu Command with given sorting parameters and menuPageNumber =1.
 */
public class MenuCommand implements Command {
    private static final int PAGE_SIZE = 3;
    private static final SortingPaginationHandler MENU_SORTING_PAGINATION_HANDLER = new SortingPaginationHandler(PAGE_SIZE, "menu", "dishes");
    private static final Logger LOGGER = LogManager.getLogger(MenuCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("Run MenuCommand");
        Map<String,String> sortMap = MENU_SORTING_PAGINATION_HANDLER.getSortMap(request);
        Map<String, Integer> pageMap = MENU_SORTING_PAGINATION_HANDLER.getPageMap(request);
        request.setAttribute(ConstantFields.CATEGORIES_ATTRIBUTE, DishService.getAllCategories());
        List<Dish> dishes = DishService.getDishes(sortMap, pageMap.get(ConstantFields.OFFSET), pageMap.get(ConstantFields.LIMIT));
        return MENU_SORTING_PAGINATION_HANDLER.handlePages(dishes,request) ? Paths.MENU_JSP : Paths.MENU_COMMAND_PATH + "&menuPageNumber=1";
    }

}

