package com.onlinerestaurant.servlet.command.manager;

import com.onlinerestaurant.db.service.DishService;
import com.onlinerestaurant.servlet.command.Command;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * DeleteDish Command.
 * Delete selected dish, sends to Menu Command.
 */
public class DeleteDishCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteDishCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("Run DeleteDish Command");
        DishService.deleteDish(Integer.parseInt(request.getParameter(ConstantFields.DISH_ID_PARAMETER)));
        return Paths.MENU_COMMAND_PATH;
    }
}
