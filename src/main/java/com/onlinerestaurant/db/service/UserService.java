package com.onlinerestaurant.db.service;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.dao.UserDAOImpl;
import com.onlinerestaurant.db.dao.UserDAO;
import com.onlinerestaurant.db.entity.User;
import com.onlinerestaurant.servlet.ConstantFields;
import com.onlinerestaurant.servlet.PasswordCodec;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UserService {
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";

    private static final UserDAO userDAO = UserDAOImpl.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(UserService.class.getName());

    public static int addUser(Map<String, String> registerMap) throws DAOException, NoSuchAlgorithmException, InvalidKeySpecException {
        LOGGER.info("Run addUser method");
        return userDAO.insertUser(registerMap.get(ConstantFields.EMAIL).toLowerCase(Locale.ROOT), registerMap.get(NAME), PasswordCodec.encodePassword(registerMap.get(ConstantFields.NEW_PASSWORD)),registerMap.get(PHONE),registerMap.get(ADDRESS));
    }

    public static User getUserById(int id) throws DAOException{
        LOGGER.info("Run getUserById method");
        return userDAO.selectUserByID(id);
    }

    public static User getUserByEmail(String email) throws DAOException{
        LOGGER.info("Run getUserByEmail method");
        return userDAO.selectUserByEmail(email);
    }

    public static List<User> getUsers(Map<String, String> sortMap, int offset, int limit) throws DAOException {
        LOGGER.info("Run getUsers method");
        return userDAO.selectUsers(sortMap.getOrDefault("userNameSearch", ""), offset, limit);
    }
    public static void changeUserPasswordById(User user, String password) throws DAOException, NoSuchAlgorithmException, InvalidKeySpecException {
        LOGGER.info("Run changeUserPasswordById method");
        userDAO.updateUser(user.getEmail(), user.getName(), PasswordCodec.encodePassword(password), user.getPhone(), user.getAddress(), user.isBanned(), user.getId());
        user.setPassword(password);
    }
    public static void updateUserDataById(User user, Map<String, String> parameterMap) throws DAOException {
        LOGGER.info("Run updateUserDataById method");
        userDAO.updateUser(parameterMap.get(ConstantFields.EMAIL), parameterMap.get(NAME), user.getPassword(), parameterMap.get(PHONE),  parameterMap.get(ADDRESS), user.isBanned(), user.getId());
        user.setEmail(parameterMap.get(ConstantFields.EMAIL));
        user.setName(parameterMap.get(NAME));
        user.setPhone(parameterMap.get(PHONE));
        user.setAddress(parameterMap.get(ADDRESS));
    }

    public static void setBanStatus(boolean is_banned, int id) throws DAOException {
        LOGGER.info("Run setBanStatus method");
        User user = userDAO.selectUserByID(id);
        userDAO.updateUser(user.getEmail(), user.getName(), user.getPassword(), user.getPhone(), user.getAddress(), is_banned, id);
    }
}
