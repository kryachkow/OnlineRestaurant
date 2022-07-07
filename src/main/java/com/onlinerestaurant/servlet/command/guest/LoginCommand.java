package com.onlinerestaurant.servlet.command.guest;

import com.onlinerestaurant.db.entity.Order;
import com.onlinerestaurant.db.entity.User;
import com.onlinerestaurant.db.service.UserService;
import com.onlinerestaurant.servlet.PasswordCodec;
import com.onlinerestaurant.servlet.command.Command;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Login command
 * Check credentials, creates user, sortMap attributes in session and sends to main page when credentials correct
 * Otherwise put errorMessage and remembered email to session and sends back to login.jsp.
 */
public class LoginCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        LOGGER.info("Run LoginCommand");

        String email = request.getParameter(ConstantFields.EMAIL);
        String password = request.getParameter(ConstantFields.PASSWORD);

        try {
            User user = UserService.getUserByEmail(email);
            if (!PasswordCodec.checkPassword(password, user.getPassword())) {
                throw new Exception();
            }
            request.getSession().setAttribute(ConstantFields.USER_ATTRIBUTE, user);
        } catch (Exception e) {
            request.getSession().setAttribute(ConstantFields.EMAIL, email);
            request.getSession().setAttribute(ConstantFields.ERROR_MESSAGE_ATTRIBUTE, "illegalLoginError.message");
            return Paths.LOGIN_JSP;
        }
        request.getSession().setAttribute(ConstantFields.CART_ATTRIBUTE, new HashMap<String, Order.CartContent>());
        request.getSession().setAttribute(ConstantFields.SORT_MAP_ATTRIBUTE, new HashMap<String, String>());
        return Paths.INDEX_JSP;
    }
}
