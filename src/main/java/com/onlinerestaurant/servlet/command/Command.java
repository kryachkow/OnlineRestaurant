package com.onlinerestaurant.servlet.command;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interface with method that should return relative path to JSP or another command.
 */
public interface Command {
    String execute (HttpServletRequest request, HttpServletResponse response) throws Exception;
}
