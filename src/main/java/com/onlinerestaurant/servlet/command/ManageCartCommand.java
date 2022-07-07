package com.onlinerestaurant.servlet.command;

import com.onlinerestaurant.db.entity.Order;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * ManageCart Command
 * Increases or Decreases CartContent quantity based on given parameter.
 * Deletes fully CartContent from cart if needed.
 * Executes MyOrderCommand.
 */
public class ManageCartCommand implements Command {

    public static final String DELETE = "delete";
    public static final String TO_DELETE_DISH_NAME = "toDeleteDishName";
    public static final String CHANGE_QUANTITY = "changeQuantity";
    public static final String ADD = "add";
    public static final String CHANGE_QUANTITY_DISH_NAME = "changeQuantityDishName";
    private static final Logger LOGGER = LogManager.getLogger(ManageCartCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("Run ManageCartCommand");
        HashMap<String, Order.CartContent> cart = (HashMap<String, Order.CartContent>) request.getSession().getAttribute(ConstantFields.CART_ATTRIBUTE);
        if (request.getParameter(DELETE) != null){
            String toDeleteDishName = request.getParameter(TO_DELETE_DISH_NAME);
            cart.remove(toDeleteDishName);
            return Paths.MY_ORDER_COMMAND_PATH;
        }
        int changeQuantity = request.getParameter(CHANGE_QUANTITY).equals(ADD) ? 1 : -1;
        String changeQuantityDishName = request.getParameter(CHANGE_QUANTITY_DISH_NAME);
        Order.CartContent toEditContent = cart.get(changeQuantityDishName);
        toEditContent.setQuantity(toEditContent.getQuantity() + changeQuantity);
        if (toEditContent.getQuantity() <= 0){
            cart.remove(changeQuantityDishName);
            return Paths.MY_ORDER_COMMAND_PATH;
        }
        toEditContent.setPrice(toEditContent.getQuantity() * toEditContent.getDish().getPrice());
        cart.replace(changeQuantityDishName, toEditContent);
        return Paths.MY_ORDER_COMMAND_PATH;
    }
}
