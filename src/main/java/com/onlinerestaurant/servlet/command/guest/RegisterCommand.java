package com.onlinerestaurant.servlet.command.guest;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.entity.Order;
import com.onlinerestaurant.db.service.UserService;
import com.onlinerestaurant.servlet.command.Command;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Registration Command
 * Gets credentials from request and creates user in DB, creates user and sortMap attributes and sends to main page.
 * If error occurs sends back to registration.jsp with remembered credentials and error message.
 */
public class RegisterCommand implements Command {

    public static final String REGISTER_MAP = "registerMap";
    public static final String PHONE = "phone";
    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("Run RegisterCommand");

        Map<String, String> registerMap = new HashMap<>();
        request.getParameterMap().forEach((key, value) -> registerMap.put(key,value[0].trim()));
        registerMap.remove("command");


        try {
            int id = UserService.addUser(registerMap);
            request.getSession().setAttribute(ConstantFields.USER_ATTRIBUTE, UserService.getUserById(id));
        } catch (DAOException e){
            request.getSession().setAttribute(ConstantFields.ERROR_MESSAGE_ATTRIBUTE, "userExistsError.message");
            registerMap.remove(PHONE);
            registerMap.remove(ConstantFields.EMAIL);
            request.getSession().setAttribute(REGISTER_MAP, registerMap);
            return Paths.REGISTER_JSP;
        }

        request.getSession().setAttribute(ConstantFields.CART_ATTRIBUTE, new HashMap<String, Order.CartContent>());
        request.getSession().setAttribute(ConstantFields.SORT_MAP_ATTRIBUTE, new HashMap<String, String>());
        return Paths.INDEX_JSP;
    }
}
