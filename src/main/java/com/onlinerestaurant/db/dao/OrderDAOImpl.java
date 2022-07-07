package com.onlinerestaurant.db.dao;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.DBUtils;
import com.onlinerestaurant.db.entity.Order;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class OrderDAOImpl implements OrderDAO {

    private static final String ADD_ORDER = "INSERT INTO `order` (user_id, delivery_address, total_price) Values (?,?,?);";
    private static final String ADD_CART_CONTENT = "INSERT INTO cart_content (dish_id, order_id, quantity, cart_price) VALUES (?,?,?,?)";
    private static final String GET_CART_CONTENT_BY_ORDER_ID = "SELECT * FROM cart_content WHERE order_id = ?";
    private static final String GET_ORDERS_BY_STATUS_ID = "SELECT * FROM `order` LEFT JOIN order_status ON status_id = order_status.id LEFT JOIN user ON user_id = user.id ";
    private static final String UPDATE_ORDER_STATUS_ID = "UPDATE `order` SET status_id = ? WHERE id = ?";
    private static final String ID = "id";
    private static final String USER_ID ="user_id";

    private static final String DELIVERY_ADDRESS ="delivery_address";
    private static final String STATUS_NAME ="order_status.name";
    private static final String CREATE_TIME = "create_time";
    private static final String DISH_ID = "dish_id";
    private static final String QUANTITY = "quantity";
    private static final String CART_PRICE = "cart_price";

    private static final Logger LOGGER = LogManager.getLogger(OrderDAOImpl.class.getName());

    private static OrderDAOImpl instance;

    public static OrderDAOImpl getInstance(){
        if (instance == null){
            instance = new OrderDAOImpl();
        }
        return instance;
    }
    private OrderDAOImpl(){

    }

    @Override
    public void insertOrder(int userId, String deliveryAddress, int totalPrice, HashMap<String, Order.CartContent> cartContentMap) throws SQLException {
        LOGGER.info("Run insertOrder method");
        Connection con = null;
        PreparedStatement addOrderSt = null;
        PreparedStatement addCartContentSt = null;
        try  {
            con = DBUtils.getInstance().getConnection();
            addOrderSt = con.prepareStatement(ADD_ORDER, Statement.RETURN_GENERATED_KEYS);
            addCartContentSt = con.prepareStatement(ADD_CART_CONTENT);
            con.setAutoCommit(false);
            addOrderSt.setInt(1, userId);
            addOrderSt.setString(2,deliveryAddress);
            addOrderSt.setInt(3, totalPrice);
            int affectedRows = addOrderSt.executeUpdate();
            if(affectedRows > 0) {
                ResultSet generatedKeys = addOrderSt.getGeneratedKeys();
                generatedKeys.next();
                int orderId = generatedKeys.getInt(1);
                for (Order.CartContent cartContent : cartContentMap.values()){
                    addCartContentSt.setInt(1,cartContent.getDish().getId());
                    addCartContentSt.setInt(2, orderId);
                    addCartContentSt.setInt(3, cartContent.getQuantity());
                    addCartContentSt.setInt(4,cartContent.getPrice());
                    addCartContentSt.executeUpdate();
                }
            }
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            LOGGER.error("Cannot insert order" , e);
            throw new DAOException("Cannot insert order", e);
        }
        finally {
            if (con != null) {
                con.close();
            }
            if (addOrderSt != null) {
                addOrderSt.close();
            }
            if (addCartContentSt != null) {
                addCartContentSt.close();
            }
        }
    }

    @Override
    public List<Order> selectOrders(String statusChose, String nameSearch, int userId, int offset, int limit) throws DAOException {
        LOGGER.info("Run selectOrders method");
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("WHERE");
        if (!statusChose.isEmpty()){
            whereClause.append(" status_id = ").append(statusChose).append(" AND");
        }
        if (!nameSearch.isEmpty()){
            whereClause.append(" user.name LIKE '%").append(nameSearch.toLowerCase(Locale.ROOT)).append("%' AND");
        }
        if (userId > 0) {
            whereClause.append(" user_id = ").append(userId).append(" AND");
        }
        int deleteIndex = whereClause.length() > 5 ? 3 : 5;
        whereClause.delete(whereClause.length() - deleteIndex, whereClause.length());

        List<Order> list = new ArrayList<>();
        try (Connection con = DBUtils.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ORDERS_BY_STATUS_ID + whereClause + " ORDER BY create_time DESC LIMIT ?,?")) {
            ps.setInt(1,offset);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(mapOrder(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot select orders", e);
            throw new DAOException("Cannot select orders", e);
        }
        return list;
    }

    @Override
    public List<Order.CartContent> selectCartContent(int orderId) throws DAOException {
        LOGGER.info("Run getCartContentByOrderId method");
        List<Order.CartContent> list = new ArrayList<>();
        try (Connection con = DBUtils.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(GET_CART_CONTENT_BY_ORDER_ID)) {
            ps.setInt(1,orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapCartContent(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot select cart content by order id", e);
            throw new DAOException("Cannot select cart content by order id", e);
        }
        return list;
    }

    @Override
    public void updateOrderStatus(int changeStatusId, int orderId) throws DAOException {
        LOGGER.info("Run updateOrderStatus method");
        try(Connection con = DBUtils.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(UPDATE_ORDER_STATUS_ID)) {
            ps.setInt(1,changeStatusId);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot update order status", e);
            throw new DAOException("Cannot update order status", e);
        }
    }

    private  Order mapOrder(ResultSet rs)throws SQLException{
        LOGGER.info("Mapping Order");
        Order order = null;
        order = new Order();
        order.setId(rs.getInt(ID));
        order.setUser(UserDAOImpl.getInstance().selectUserByID(rs.getInt(USER_ID)));
        order.setDeliveryAddress(rs.getString(DELIVERY_ADDRESS));
        order.setStatus(Order.Status.valueOf(rs.getString(STATUS_NAME).toUpperCase(Locale.ROOT)));
        order.setCreateTime(new Timestamp(rs.getTimestamp(CREATE_TIME).getTime()));
        order.setContentList(selectCartContent(order.getId()));
        int totalPrice = order.getContentList().stream().mapToInt(Order.CartContent::getPrice).sum();
        order.setTotalPrice(totalPrice);
        return order;
    }

    private  Order.CartContent mapCartContent(ResultSet rs) throws SQLException {
        LOGGER.info("Mapping CartContent");
        Order.CartContent cartContent = null;
        cartContent = new Order.CartContent();
        cartContent.setDish(DishDAOImpl.getInstance().selectDishById(rs.getInt(DISH_ID)));
        cartContent.setQuantity(rs.getInt(QUANTITY));
        cartContent.setPrice(rs.getInt(CART_PRICE));
        return cartContent;
    }
}
