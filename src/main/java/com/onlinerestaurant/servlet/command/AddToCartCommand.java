package com.onlinerestaurant.servlet.command;

import com.onlinerestaurant.db.entity.Order;
import com.onlinerestaurant.db.service.DishService;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * AddToCart Command
 * Creates CartContent By Given parameters and puts it to cart.
 * CartContents stores in Map for easier merging purposes.
 * Executes MenuCommand.
 */
public class AddToCartCommand implements Command {

    public static final String QUANTITY = "quantity";
    private static final Logger LOGGER = LogManager.getLogger(AddToCartCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("Run AddToCartCommand");
        int dishId = Integer.parseInt(request.getParameter(ConstantFields.DISH_ID_PARAMETER));
        int quantity = Integer.parseInt(request.getParameter(QUANTITY));
        Order.CartContent cartContent = new Order.CartContent();
        cartContent.setDish(DishService.getDishById(dishId));
        cartContent.setQuantity(quantity);
        cartContent.setPrice(cartContent.getQuantity() * cartContent.getDish().getPrice());
        HashMap<String, Order.CartContent> cart = (HashMap<String, Order.CartContent>) request.getSession().getAttribute(ConstantFields.CART_ATTRIBUTE);
        cart.merge(cartContent.getDish().getName(), cartContent, (oldContent, newContent) -> {
            Order.CartContent returnContent = new Order.CartContent();
            returnContent.setPrice(oldContent.getPrice() + newContent.getPrice());
            returnContent.setDish(oldContent.getDish());
            returnContent.setQuantity(oldContent.getQuantity() + newContent.getQuantity());
            return returnContent;});
        return Paths.MENU_COMMAND_PATH;
    }
}
