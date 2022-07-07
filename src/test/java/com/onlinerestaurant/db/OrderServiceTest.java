package com.onlinerestaurant.db;

import com.onlinerestaurant.db.entity.Order;
import com.onlinerestaurant.db.service.DishService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    static private Connection con;

    @BeforeAll
    static void setUp() {
        con = DAOTestingUtils.setUpConnectionMethod();
        DAOTestingUtils.setUpDataBase();
    }

    @AfterAll
    static void tearDown() throws SQLException, FileNotFoundException {
        DAOTestingUtils.clearDataBase();
        con.close();
    }

    @Test
    void addOrder() throws SQLException {
        HashMap<String, Order.CartContent> cartContentMap = new HashMap<>();
        Order.CartContent cartContent = new Order.CartContent();
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        cartContent.setDish(DishService.getDishById(1));
        cartContent.setQuantity(5);
        cartContent.setPrice(1000);
        cartContentMap.put(cartContent.getDish().getName(), cartContent);

        cartContent = new Order.CartContent();
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        cartContent.setDish(DishService.getDishById(2));
        cartContent.setQuantity(2);
        cartContent.setPrice(80);
        cartContentMap.put(cartContent.getDish().getName(), cartContent);

        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        com.onlinerestaurant.db.service.OrderService.addOrder(1,"address6",1080, cartContentMap);

        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection());
        List<Order> orders = com.onlinerestaurant.db.service.OrderService.getOrdersByUserId(1,0,4);
        orders.sort(Comparator.comparing(Order::getDeliveryAddress));

        assertEquals(2, orders.size());
        assertEquals(1, orders.get(0).getUser().getId());
        assertEquals(1, orders.get(1).getUser().getId());
        assertEquals("address5", orders.get(0).getDeliveryAddress());
        assertEquals("address6", orders.get(1).getDeliveryAddress());
        assertEquals(1, orders.get(0).getContentList().size());
        assertEquals(2, orders.get(1).getContentList().size());
    }

    @Test
    void getOrderByStatusAndUpdateStatusName() throws SQLException {
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection());
        HashMap<String,String> sortMap = new HashMap<>();
        sortMap.put("statusChose", "2");
        sortMap.put("nameSearch", "");
        List<Order> ordersById = com.onlinerestaurant.db.service.OrderService.getOrders(sortMap,0,4);
        assertEquals(2,ordersById.size());

        Order orderToUpdate = ordersById.get(0);
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection());
        com.onlinerestaurant.db.service.OrderService.updateOrderStatus(3, orderToUpdate.getId());
        ordersById = com.onlinerestaurant.db.service.OrderService.getOrders(sortMap, 0,4);
        assertEquals(1, ordersById.size());

        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        sortMap.put("nameSearch", "ma");
        ordersById = com.onlinerestaurant.db.service.OrderService.getOrders(sortMap,0,4);
        assertTrue(ordersById.isEmpty());

    }
}