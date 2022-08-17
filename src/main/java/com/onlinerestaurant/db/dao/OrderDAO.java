package com.onlinerestaurant.db.dao;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.entity.Order;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface OrderDAO {
    void insertOrder(int userId, String deliveryAddress, int totalPrice, HashMap<String, Order.CartContent> cartContentMap) throws SQLException;
    List<Order> selectOrders(String statusChose, String nameSearch, int userId, int offset, int limit) throws DAOException;
    List<Order.CartContent> selectCartContent(int orderId) throws DAOException;
    void updateOrderStatus(int changeStatusId, int orderId) throws DAOException;

    int selectTotalSpent(int userID) throws DAOException;
}
