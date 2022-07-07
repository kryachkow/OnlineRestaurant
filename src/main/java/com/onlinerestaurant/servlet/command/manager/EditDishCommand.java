package com.onlinerestaurant.servlet.command.manager;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.dao.DishDAOImpl;
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
 * EditDish Command.
 * If toDoParameter is empty gets dish data by id and puts it like attribute with all categories sends to edit.jsp.
 * If toDoParameter isn`t empty edits dish and executes Menu Command.
 * If error occurs sets errorMessage and executes EditDish Command with given dishID.
 */
public class EditDishCommand implements Command {

    public static final String DISH_ATTRIBUTE = "dish";

    private static final Logger LOGGER = LogManager.getLogger(EditDishCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("Run EditDishCommand");
        if (request.getParameter(ConstantFields.TO_DO_PARAMETER) != null) {
            Map<String, String> dishMap = ManageDishesUtils.convertParamMap(request);
            dishMap.put("dishId", request.getParameter(ConstantFields.DISH_ID_PARAMETER));
            try {
                DishService.editDish(dishMap);
            } catch (DAOException e) {
                request.getSession().setAttribute(ConstantFields.ERROR_MESSAGE_ATTRIBUTE, "dishEditionError.message");
                return Paths.EDIT_DISH_COMMAND_PATH + "&dishId=" + request.getParameter(ConstantFields.DISH_ID_PARAMETER) ;
            }
            return Paths.MENU_COMMAND_PATH;
        }
        request.setAttribute(DISH_ATTRIBUTE, DishService.getDishById(Integer.parseInt(request.getParameter(ConstantFields.DISH_ID_PARAMETER))));
        request.setAttribute(ConstantFields.CATEGORIES_ATTRIBUTE, DishService.getAllCategories());
        return Paths.EDIT_DISH_JSP;
    }

}
