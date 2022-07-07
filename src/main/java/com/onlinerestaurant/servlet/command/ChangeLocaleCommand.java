package com.onlinerestaurant.servlet.command;

import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ChangeLocale Command.
 * Change currentLocale attribute to given and sends to changeLocale.jsp.
 */
public class ChangeLocaleCommand implements Command {

    public static final String LOCALE = "locale";
    public static final String CURRENT_LOCALE = "currentLocale";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().setAttribute(CURRENT_LOCALE, request.getParameter(LOCALE));
        return Paths.CHANGE_LOCALE_JSP;
    }
}
