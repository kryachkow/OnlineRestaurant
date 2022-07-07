package com.onlinerestaurant.servlet.command;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.entity.User;
import com.onlinerestaurant.db.service.UserService;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.PasswordCodec;
import com.onlinerestaurant.servlet.Paths;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * EditProfile Command
 * If no parameters passed sends to profile.jsp.
 * Check user password to edit data, if passwords don`t match puts error message and executes EditProfile Command.'
 * Edits user data or password based on given parameters, puts message attribute on success.
 */
public class EditProfileCommand implements Command{

    public static final String NAME = "name";
    public static final String MESSAGE = "message";
    public static final String NEW_PASSWORD = "newPassword";
    private static final Logger LOGGER = LogManager.getLogger(EditProfileCommand.class.getName());


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("Run EditProfileCommand");
        User user = (User) request.getSession().getAttribute(ConstantFields.USER_ATTRIBUTE);
        Map<String, String> parameterMap = request.getParameterMap().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));
        parameterMap.remove("command");

        if(!parameterMap.containsKey(ConstantFields.PASSWORD)){
            return Paths.PROFILE_JSP;
        }

        if(!PasswordCodec.checkPassword(parameterMap.get(ConstantFields.PASSWORD), user.getPassword())) {
            request.getSession().setAttribute(ConstantFields.ERROR_MESSAGE_ATTRIBUTE, "wrongPasswordError.message");
            return Paths.EDIT_PROFILE_COMMAND;
        }

        if(parameterMap.containsKey(NAME)) {
            try {
                UserService.updateUserDataById(user, parameterMap);
            } catch (DAOException e) {
                request.getSession().setAttribute(ConstantFields.ERROR_MESSAGE_ATTRIBUTE, e.getMessage());
                return Paths.EDIT_PROFILE_COMMAND;
            }
            request.getSession().setAttribute(MESSAGE, "userDataUpdated.message");
            return Paths.EDIT_PROFILE_COMMAND;
        }

        try {
            UserService.changeUserPasswordById(user, parameterMap.get(NEW_PASSWORD));
        } catch (DAOException e) {
            LOGGER.info("Change user password exception caught", e);
            request.getSession().setAttribute(ConstantFields.ERROR_MESSAGE_ATTRIBUTE, e.getMessage());
            return Paths.EDIT_PROFILE_COMMAND;
        }
        request.getSession().setAttribute(MESSAGE, "passwordUpdated.message");
        return Paths.EDIT_PROFILE_COMMAND;
    }
}
