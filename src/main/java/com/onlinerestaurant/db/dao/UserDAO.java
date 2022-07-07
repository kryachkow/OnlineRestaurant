package com.onlinerestaurant.db.dao;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.entity.User;

import java.util.List;

public interface UserDAO {
     User selectUserByID(int id) throws DAOException;
     User selectUserByEmail(String email) throws DAOException;
     List<User> selectUsers(String userNameSearch, int offset, int limit) throws DAOException;

     int insertUser(String email, String name, String password, String phone, String address) throws DAOException;
     void updateUser(String email, String name, String password, String phone, String address, boolean is_banned, int id) throws DAOException;
     void deleteUserById(int id) throws DAOException;
}
