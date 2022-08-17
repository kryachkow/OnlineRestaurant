package com.onlinerestaurant.servlet.filter;


import com.onlinerestaurant.db.entity.User;
import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * RoleFilter.
 * Gives access to manager commands, if user is manager, sends to errorNotFound.jsp if not.
 */
@WebFilter("/*")
public class RoleFilter implements Filter {

    private static final List<String> MANAGER_COMMANDS = Arrays.asList("addDish", "deleteDish", "users", "updateOrderStatus", "editDish", "addCategory", "deleteCategory");
    private static final String USER_ATTRIBUTE = "user";
    private static final String COMMAND_PARAMETER = "command";



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if(MANAGER_COMMANDS.contains(request.getParameter(COMMAND_PARAMETER))) {
            User user = (User) request.getSession().getAttribute(USER_ATTRIBUTE);
            if(user == null || user.getRole() != User.Role.MANAGER) {
                request.getRequestDispatcher(Paths.ERROR_NOT_FOUND_JSP).forward(servletRequest, servletResponse);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
