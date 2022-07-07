package com.onlinerestaurant.servlet.command;

import com.onlinerestaurant.db.dao.OrderDAOImpl;
import com.onlinerestaurant.db.entity.User;
import com.onlinerestaurant.db.service.OrderService;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.Paths;
import com.onlinerestaurant.servlet.SortingPaginationHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Orders Command
 * Gives all orders for users by pagination parameters sorted by date.
 * Gives all users orders for manager by given sorting and pagination parameters.
 * Stores sorting and pagination parameters in session.
 * When list is empty and orderPageNumber != 1 executes Orders Command with given sorting parameters and orderPageNumber =1.
 */
public class OrdersCommand implements Command {

    private static final int PAGE_SIZE = 3;
    private static final SortingPaginationHelper ORDERS_SORTING_PAGINATION_HELPER = new SortingPaginationHelper(PAGE_SIZE, "order", "orders");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, String> sortMap = ORDERS_SORTING_PAGINATION_HELPER.getSortMap(request);
        Map<String, Integer> pageMap = ORDERS_SORTING_PAGINATION_HELPER.getPageMap();

        User user = (User) request.getSession().getAttribute(ConstantFields.USER_ATTRIBUTE);
        if (user.getRole() == User.Role.USER) {
            ORDERS_SORTING_PAGINATION_HELPER.handlePages(OrderService.getOrdersByUserId(user.getId(), pageMap.get(ConstantFields.OFFSET), pageMap.get(ConstantFields.LIMIT)), request);
            return Paths.ORDERS_JSP;
        }

        return ORDERS_SORTING_PAGINATION_HELPER.handlePages(OrderService.getOrders(sortMap, pageMap.get(ConstantFields.OFFSET), pageMap.get(ConstantFields.LIMIT)), request) ? Paths.ORDERS_JSP : Paths.ORDERS_COMMAND_PATH + "&orderPageNumber=1";

    }


}
