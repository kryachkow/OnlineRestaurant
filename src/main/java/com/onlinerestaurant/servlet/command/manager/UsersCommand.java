package com.onlinerestaurant.servlet.command.manager;

import com.onlinerestaurant.db.entity.User;
import com.onlinerestaurant.db.service.UserService;
import com.onlinerestaurant.servlet.command.Command;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.Paths;
import com.onlinerestaurant.servlet.SortingPaginationHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Users Command
 * If toDoParameter isn`t empty bans/unbans user by id, and executes Users Command.
 * If toDoParameter is empty gets list of users with given sort and pagination parameters, sends to user.jsp.
 * Stores sorting and pagination parameters in session.
 * When list is empty and usersPageNumber != 1 executes Users Command with given sorting parameters and usersPageNumber =1.
 */
public class UsersCommand implements Command {

    public static final String BAN_ACTION = "ban";
    public static final String ID = "id";
    public static final int PAGE_SIZE = 3;
    private static final SortingPaginationHandler USERS_SORTING_PAGINATION_HANDLER = new SortingPaginationHandler(PAGE_SIZE, "users", "users");
    private static final Logger LOGGER = LogManager.getLogger(UsersCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("Run UsersCommand");
        if (request.getParameter(ConstantFields.TO_DO_PARAMETER) != null) {
            UserService.setBanStatus(request.getParameter(ConstantFields.TO_DO_PARAMETER).equals(BAN_ACTION),
                    Integer.parseInt(request.getParameter(ID)));
            return Paths.USERS_COMMAND_PATH;
        }
        Map<String,String> sortMap = USERS_SORTING_PAGINATION_HANDLER.getSortMap(request);
        Map<String, Integer> pageMap = USERS_SORTING_PAGINATION_HANDLER.getPageMap(request);
        List<User> list = UserService.getUsers(sortMap, pageMap.get(ConstantFields.OFFSET),pageMap.get(ConstantFields.LIMIT));
        return USERS_SORTING_PAGINATION_HANDLER.handlePages(list, request) ? Paths.USERS_JSP : Paths.USERS_COMMAND_PATH + "&usersPageNumber=1";
    }
}
