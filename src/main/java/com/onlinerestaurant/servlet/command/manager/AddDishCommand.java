package com.onlinerestaurant.servlet.command.manager;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.service.DishService;
import com.onlinerestaurant.servlet.command.Command;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.ManageDishesUtils;
import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * AddDish Command
 * Without given parameters, sends to addDish.jsp with gotten category list.
 * With dish parameters adds new dish and executes Menu Command.
 * If error occurs remembers entered dish parameters in session, puts error message and executes AddDish Command without parameters.
 */
public class AddDishCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AddDishCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("Run AddDish Command");
        if(request.getParameter(ConstantFields.TO_DO_PARAMETER)!= null) {
            Map<String, String> dishMap = ManageDishesUtils.convertParamMap(request);
            try {
                DishService.addDish(dishMap);
            } catch (DAOException e) {
                request.getSession().setAttribute(ConstantFields.DISH_MAP_ATTRIBUTE, dishMap);
                request.getSession().setAttribute(ConstantFields.ERROR_MESSAGE_ATTRIBUTE, "addDishError.message");
                return Paths.ADD_DISH_COMMAND_PATH;
            }
            return Paths.MENU_COMMAND_PATH;
        }
        request.setAttribute(ConstantFields.CATEGORIES_ATTRIBUTE, DishService.getAllCategories());
        return Paths.ADD_DISH_JSP;
    }


}
