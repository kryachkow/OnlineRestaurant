package com.onlinerestaurant.servlet.command.manager;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.service.DishService;
import com.onlinerestaurant.servlet.command.Command;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * AddCategory Command
 * Creates category by given name and executes AddDish Command.
 * If error occurs sets errorMessage and executes  AddDish Command.
 */
public class AddCategoryCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AddCategoryCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        LOGGER.info("Run AddCategoryCommand");
        try {
            DishService.addCategory(request.getParameter("categoryName").trim());
        }catch (DAOException e) {
            request.getSession().setAttribute(ConstantFields.ERROR_MESSAGE_ATTRIBUTE, "addCategoryError.message");
            return Paths.ADD_DISH_COMMAND_PATH;
        }
        request.getSession().setAttribute("message", "categoryAdded.message");
        return Paths.ADD_DISH_COMMAND_PATH;
    }
}
