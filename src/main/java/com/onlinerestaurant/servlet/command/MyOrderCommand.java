package com.onlinerestaurant.servlet.command;

import com.onlinerestaurant.db.entity.Order;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * MyOrder Command
 * Gets total price for cart and sends to myOrder.jsp.
 */
public class MyOrderCommand implements Command {

    public static final String TOTAL_PRICE = "totalPrice";
    private static final Logger LOGGER = LogManager.getLogger(MyOrderCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("Run MyOrderCommand");
        HashMap<String, Order.CartContent> cart = (HashMap<String, Order.CartContent>) request.getSession().getAttribute(ConstantFields.CART_ATTRIBUTE);
        AtomicInteger totalPrice = new AtomicInteger();
        cart.values().forEach(cartContent -> totalPrice.addAndGet(cartContent.getPrice()));
        request.setAttribute(TOTAL_PRICE,totalPrice );
        return Paths.MY_ORDER_JSP;
    }
}
