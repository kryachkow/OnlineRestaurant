package com.onlinerestaurant.db.dao;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.DBUtils;
import com.onlinerestaurant.db.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserDAOImpl implements UserDAO {

    private static final String GET_USER_BY_ID = "SELECT user.*,role.name AS role_name FROM user LEFT JOIN role ON role_id=role.id WHERE user.id=?";
    private static final String GET_USER_BY_EMAIL = "SELECT user.*,role.name AS role_name FROM user LEFT JOIN role ON role_id=role.id WHERE user.email=?";
    private static final String GET_ALL_USERS = "SELECT user.*,role.name AS role_name FROM user LEFT JOIN role ON role_id=role.id";
    private static final String INSERT_USER = "INSERT INTO user(email, name, password, phone, address) VALUES(?,?,?,?,?)";
    private static final String UPDATE_USER_DATA = "UPDATE user set name = ?, email = ?, phone = ?, address = ?, password = ?, is_banned = ? WHERE id = ?";
    private static final String ID = "id";
    private static final String EMAIl = "email";
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String PHONE = "phone";
    private static final String ADDRESS = "address";
    private static final String ROLE_NAME = "role_name";
    private static final String IS_BANNED = "is_banned";
    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class.getName());

    private static UserDAOImpl instance;

    public static UserDAOImpl getInstance(){
        if (instance == null){
            instance = new UserDAOImpl();
        }
        return instance;
    }
    private UserDAOImpl(){
    }

    @Override
    public User selectUserByID(int id) throws DAOException {
        LOGGER.info("Run selectUserById method");
        User user = null;
        try(Connection con = DBUtils.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(GET_USER_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                user = mapUser(rs);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Cannot select user by Id", e);
            throw new DAOException("Cannot select user by Id", e);
        }
        return user;
    }

    @Override
    public User selectUserByEmail(String email) throws DAOException {
        LOGGER.info("Run selectUserByEmail method");
        User user = null;
        try(Connection con = DBUtils.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(GET_USER_BY_EMAIL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                user = mapUser(rs);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot select user by email", e);
            throw new DAOException("Cannot select user by email", e);
        }
        return user;
    }

    @Override
    public List<User> selectUsers(String userNameSearch, int offset, int limit) throws DAOException {
        LOGGER.info("Run selectUsers method");
        List<User> users = new ArrayList<>();
        String whereClause = "";
        if (!userNameSearch.isEmpty()) {
            whereClause = " WHERE user.name LIKE '%" + userNameSearch + "%' ";
        }
        try(Connection con = DBUtils.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(GET_ALL_USERS + whereClause + " LIMIT ?,?")){
            ps.setInt(1, offset);
            ps.setInt(2,limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(mapUser(rs));
            }

        } catch (SQLException e) {
            LOGGER.error("Cannot select users", e);
            throw new DAOException("Cannot select users", e);
        }
        return users;
    }

    @Override
    public int insertUser(String email, String name, String password, String phone, String address) throws DAOException {
        LOGGER.info("Run insertUser method");
        int id = 0;
        try(Connection con = DBUtils.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,email);
            ps.setString(2, name);
            ps.setString(3, password);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            id = generatedKeys.getInt(1);
        } catch (SQLException e) {
            LOGGER.error("Cannot insert user", e);
            throw new DAOException("Cannot insert user", e);
        }
        return id;
    }

    @Override
    public void updateUser(String email, String name, String password, String phone, String address, boolean is_banned, int id) throws DAOException {
        LOGGER.info("Run updateUser method");
        try(Connection con = DBUtils.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(UPDATE_USER_DATA)) {
            ps.setString(1,name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setString(5, password);
            ps.setBoolean(6, is_banned);
            ps.setInt(7, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Cannot update user", e);
            throw new DAOException("Cannot update user", e);
        }
    }

    @Override
    public void deleteUserById(int id) throws DAOException {
        throw new UnsupportedOperationException();
    }

    private User mapUser(ResultSet rs) throws SQLException{
        LOGGER.info("Mapping User");
        User user = new User();
        user.setId(rs.getInt(ID));
        user.setName(rs.getString(NAME));
        user.setEmail(rs.getString(EMAIl));
        user.setPassword(rs.getString(PASSWORD));
        user.setPhone(rs.getString(PHONE));
        user.setAddress(rs.getString(ADDRESS));
        user.setRole(User.Role.valueOf(rs.getString(ROLE_NAME).toUpperCase(Locale.ROOT)));
        user.setBanned(rs.getInt(IS_BANNED) == 1);
        return user;
    }
}
