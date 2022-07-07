package com.onlinerestaurant.servlet.command.manager;

import com.onlinerestaurant.db.service.OrderService;
import com.onlinerestaurant.servlet.command.Command;
import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * UpdateOrder Command
 * Updates changes order status by given ids.
 * Executes Order Command.
 */
public class UpdateOrderCommand implements Command {

    public static final String ORDER_ID_PARAMETER = "orderId";
    public static final String CHANGE_STATUS_ID_PARAMETER = "changeStatusId";
    private static final Logger LOGGER = LogManager.getLogger(UpdateOrderCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("Run UpdateOrderCommand");
        int changeStatusId = Integer.parseInt(request.getParameter(CHANGE_STATUS_ID_PARAMETER));
        int orderId = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));
        OrderService.updateOrderStatus(changeStatusId, orderId);
        return Paths.ORDERS_COMMAND_PATH;
    }
}
