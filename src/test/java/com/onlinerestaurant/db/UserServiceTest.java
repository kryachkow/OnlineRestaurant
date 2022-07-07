package com.onlinerestaurant.db;


import com.onlinerestaurant.db.dao.UserDAOImpl;
import com.onlinerestaurant.db.entity.User;
import com.onlinerestaurant.db.service.UserService;
import com.onlinerestaurant.servlet.PasswordCodec;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UserServiceTest {
    static private Connection con;


    @BeforeAll
    static void setUp() throws SQLException {
        con = DAOTestingUtils.setUpConnectionMethod();
        DAOTestingUtils.setUpDataBase();
    }

    @AfterAll
    static void tearDown() throws FileNotFoundException, SQLException {
        DAOTestingUtils.clearDataBase();
        con.close();
    }

    @Test
    void addUser() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection());
        HashMap<String, String> registerMap = new HashMap<>();
        registerMap.put("email", "email");
        registerMap.put("name", "asdasdasd");
        registerMap.put("password", "pass");
        registerMap.put("phone", "phone");
        registerMap.put("address", "address");

        UserService.addUser(registerMap);
        Assertions.assertEquals("phone",UserService.getUserByEmail("email").getPhone());
        Assertions.assertThrows(DAOException.class, () -> {
            UserService.addUser(registerMap);
        });
    }

    @Test
    void getUserById() throws SQLException {
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection());
        Assertions.assertEquals(UserService.getUserById(1).getName(),"Manager");
        Assertions.assertThrows(DAOException.class, () -> UserService.getUserById(4));

    }

    @Test
    void getUserByEmail() throws SQLException {
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection());
        Assertions.assertEquals(UserService.getUserByEmail("krachkow@gmail.com").getName(), "Manager");
        Assertions.assertThrows(DAOException.class, () -> UserService.getUserByEmail("invalidEmail"));
    }

    @Test
    void getAllUsers() throws SQLException {
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection());
        Map<String,String> sortMap = new HashMap<>();
        List<User> userList = UserService.getUsers(sortMap, 0, 4);
        Assertions.assertEquals(userList.size(), 3);
        sortMap.put("userNameSearch", "ma");
        userList = UserService.getUsers(sortMap, 0,4);
        Assertions.assertEquals(userList.size(),1);
        Assertions.assertThrows(DAOException.class, () -> UserService.getUsers(sortMap,0,4));
    }

    @Test
    void changeUserPasswordById() throws SQLException, DecoderException, NoSuchAlgorithmException, InvalidKeySpecException {
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection());
        User user = UserService.getUserById(1);
        UserService.changeUserPasswordById(user, "newPassword");
        Assertions.assertTrue(PasswordCodec.checkPassword("newPassword", UserService.getUserById(1).getPassword()));
    }

    @Test
    void updateUserDataById() throws SQLException {
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection());
        User user = UserService.getUserById(2);
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("name", "name1");
        parameterMap.put("email", "examp@example.com");
        parameterMap.put("phone", "+412412");
        parameterMap.put("address", "address1");
        UserService.updateUserDataById(user, parameterMap);
        User updatedUser = UserService.getUserById(2);
        Assertions.assertEquals(user.getName(), updatedUser.getName());
        Assertions.assertEquals("examp@example.com" ,updatedUser.getEmail());
        Assertions.assertEquals("name1" ,updatedUser.getName());
        Assertions.assertEquals("+412412" ,updatedUser.getPhone());
        Assertions.assertEquals("address1" ,updatedUser.getAddress());

    }

    @Test
    void setBanStatus() throws SQLException {
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection());
        User user = UserService.getUserById(1);
        UserService.setBanStatus(true,1);
        User updatedUser = UserService.getUserById(1);
        Assertions.assertFalse(user.isBanned());
        Assertions.assertTrue(updatedUser.isBanned());
    }


}