package com.onlinerestaurant.servlet.command.manager;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.dao.DishDAOImpl;
import com.onlinerestaurant.db.service.DishService;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.Paths;
import com.onlinerestaurant.servlet.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * DeleteCategory Command
 * Deletes category, puts success message to session, executes AddDish Command.
 * If error occurs puts error message and executes AddDish Command.
 */
public class DeleteCategoryCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            DishService.deleteCategory(Integer.parseInt(request.getParameter(ConstantFields.CATEGORY_ID)));
        } catch (DAOException e) {
            request.getSession().setAttribute(ConstantFields.ERROR_MESSAGE_ATTRIBUTE, "cannotDeleteCategoryError.message");
            return Paths.ADD_DISH_COMMAND_PATH;
        }
        request.getSession().setAttribute(ConstantFields.MESSAGE_ATTRIBUTE, "categoryDeleted.message");
        return Paths.ADD_DISH_COMMAND_PATH;
     }
}
