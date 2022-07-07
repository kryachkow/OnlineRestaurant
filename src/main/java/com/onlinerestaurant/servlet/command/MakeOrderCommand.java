package com.onlinerestaurant.servlet.command;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.entity.Order;
import com.onlinerestaurant.db.entity.User;
import com.onlinerestaurant.db.service.OrderService;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * MakeOrder Command.
 * Checks if cart is empty, puts errorMessage in session and executes MyOrder Command on true.
 * Creates order, clears cart and sends to index.jsp.
 */
public class MakeOrderCommand implements Command{

    public static final String DELIVERY_ADDRESS = "deliveryAddress";
    public static final String TOTAL_PRICE = "totalPrice";
    private static final Logger LOGGER = LogManager.getLogger(MakeOrderCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("Run MakeOrderCommand");
        User user = (User) request.getSession().getAttribute(ConstantFields.USER_ATTRIBUTE);
        HashMap<String, Order.CartContent> cart = (HashMap<String, Order.CartContent>) request.getSession().getAttribute(ConstantFields.CART_ATTRIBUTE);
        String deliveryAddress = request.getParameter(DELIVERY_ADDRESS);
        if(cart == null || cart.isEmpty()) {
            request.getSession().setAttribute(ConstantFields.ERROR_MESSAGE_ATTRIBUTE, "emptyCartError.message");
            return Paths.MY_ORDER_COMMAND_PATH;
        }
        int totalPrice = Integer.parseInt(request.getParameter(TOTAL_PRICE));
        try{
            OrderService.addOrder(user.getId(),deliveryAddress, totalPrice, cart);
        } catch (DAOException e) {
            request.getSession().setAttribute(ConstantFields.ERROR_MESSAGE_ATTRIBUTE, "cannotAddOrderError.message");
            return Paths.MY_ORDER_COMMAND_PATH;
        }

        cart.clear();
        return Paths.INDEX_JSP;
    }
}
