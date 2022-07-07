package com.onlinerestaurant.servlet.filter;

import com.onlinerestaurant.db.entity.User;
import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * Ban Filter.
 * Check user ban status, sends to errorNotFound with banMessage if user is banned.
 */
    @WebFilter("/*")
public class BanFilter implements Filter {

    private static final String USER_ATTRIBUTE = "user";


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = (User) request.getSession().getAttribute(USER_ATTRIBUTE);
        if (user != null && user.isBanned()) {
            request.setAttribute("banMessage", "youWereBanned.message");
            request.getRequestDispatcher(Paths.ERROR_NOT_FOUND_JSP).forward(request, servletResponse);
            return;
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
