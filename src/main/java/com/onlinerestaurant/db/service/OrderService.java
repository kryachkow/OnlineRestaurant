package com.onlinerestaurant.db.service;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.dao.OrderDAOImpl;
import com.onlinerestaurant.db.entity.Order;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    public static final String STATUS_CHOSE = "statusChose";
    public static final String NAME_SEARCH = "nameSearch";
    private static final OrderDAOImpl ORDER_DAO = OrderDAOImpl.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(OrderService.class.getName());

    public static void addOrder(int userId, String deliveryAddress, int totalPrice, HashMap<String, Order.CartContent> cartContentMap) throws SQLException {
        LOGGER.info("Run addOrder method");
        ORDER_DAO.insertOrder(userId, deliveryAddress, totalPrice, cartContentMap);
    }
    public static List<Order> getOrders(Map<String,String> sortMap, int offset, int limit) throws DAOException {
        LOGGER.info("Run getOrders method");
        String statusChose = sortMap.getOrDefault(STATUS_CHOSE,"");
        String nameSearch = sortMap.getOrDefault(NAME_SEARCH, "").trim();
        return ORDER_DAO.selectOrders(statusChose, nameSearch, 0, offset, limit);
    }
    public static List<Order> getOrdersByUserId(int userId, int offset, int limit) throws DAOException {
        LOGGER.info("Run getOrdersByUserId method");
        return ORDER_DAO.selectOrders("", "", userId, offset, limit);
    }
    public static void updateOrderStatus(int changeStatusId, int orderId) throws DAOException {
        LOGGER.info("Run updateOrderStatus method");
        ORDER_DAO.updateOrderStatus(changeStatusId, orderId);
    }
}
