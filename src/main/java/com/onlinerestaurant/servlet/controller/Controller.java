package com.onlinerestaurant.servlet.controller;

import com.onlinerestaurant.servlet.command.Command;
import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Controller servlet, executes chosen command and passes results to JSPs.
 * If unexpected exception throws sends user to error.jsp.
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(Controller.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.info("Run Controller doGet method");
        String address = Paths.ERROR_JSP;
        String commandName = req.getParameter("command");
        Command command = CommandContainer.getCommand(commandName);
        try{
            address = command.execute(req, resp);
        } catch (Exception e) {
            LOGGER.error("Cannot execute a command", e);
            req.setAttribute("errorMessage", e.getMessage());
        }
        req.getRequestDispatcher(address).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info("Run Controller doPost method");
        String address = Paths.ERROR_JSP;
        String commandName = req.getParameter("command");
        Command command = CommandContainer.getCommand(commandName);
        try {
            address = command.execute(req, resp);
        } catch (Exception ex) {
            LOGGER.error("Cannot execute a command", ex);
            req.getSession().setAttribute("errorMessage", ex.getMessage());
        }
        resp.sendRedirect(address);
    }

}
