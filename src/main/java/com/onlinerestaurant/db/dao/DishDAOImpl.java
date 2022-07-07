package com.onlinerestaurant.db.dao;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.DBUtils;
import com.onlinerestaurant.db.entity.Dish;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DishDAOImpl implements DishDAO {

    private static final String GET_DISH_BY_ID = "SELECT * FROM dish LEFT JOIN category ON category_id = category.id WHERE dish.id = ?";
    private static final String UPDATE_DISH_BY_ID = "UPDATE dish set name = ?, ingredients = ?, weight = ?, calories = ?, price = ?, category_id = ?, image_path = ? WHERE id = ?";
    private static final String ADD_DISH = "INSERT INTO dish (name, ingredients, weight, calories, price, category_id, image_path) VALUES(?,?,?,?,?,?,?)";

    private static final String DELETE_DISH_BY_ID = "DELETE FROM dish WHERE id = ?";
    private static final String DELETE_CATEGORY_BY_ID = "DELETE FROM category WHERE id = ?";
    private static final String GET_ALL_CATEGORIES = "SELECT * FROM category";

    private static final String ADD_CATEGORY = "INSERT INTO category(name) value (?)";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String INGREDIENTS = "ingredients";
    private static final String WEIGHT = "weight";
    private static final String CALORIES = "calories";
    private static final String PRICE = "price";
    private static final String IMAGE_PATH = "image_path";

    private static final Logger LOGGER = LogManager.getLogger(DishDAOImpl.class.getName());

    private static DishDAOImpl instance;


    public static DishDAOImpl getInstance() {
        if (instance == null) {
            instance = new DishDAOImpl();
        }
        return instance;
    }

    private DishDAOImpl() {

    }

    @Override
    public Dish selectDishById(int id) throws DAOException {
        LOGGER.info("Run selectDishById method");
        Dish dish;
        try (Connection con = DBUtils.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(GET_DISH_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dish = mapDish(rs);
            } else {
                throw new SQLException("There is no dish with such ID");
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot select dish by id", e);
            throw new DAOException("Cannot select dish", e);
        }
        return dish;
    }

    @Override
    public List<Dish> selectDishes(String categoryId, String priceFrom, String priceTo, String sortBy, int offset, int limit) throws DAOException {
        LOGGER.info("Run selectDishes method");
        List<Dish> list = new ArrayList<>();
        StringBuilder sortingSelector = new StringBuilder();
        sortingSelector.append("WHERE");
        if (!categoryId.isEmpty()) {
            sortingSelector.append(" category.id = ").append(categoryId).append(" AND");
        }
        if (!priceFrom.isEmpty())
            sortingSelector.append(" price >= ").append(priceFrom).append(" AND");
        if (!priceTo.isEmpty())
            sortingSelector.append(" price <= ").append(priceTo).append(" AND");
        int deleteIndex = sortingSelector.length() > 5 ? 3 : 5;
        sortingSelector.delete(sortingSelector.length() - deleteIndex, sortingSelector.length());

        try (Connection con = DBUtils.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM dish LEFT JOIN category ON category_id = category.id " + sortingSelector + " " + sortBy + " LIMIT ?,?")) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Dish dish = mapDish(rs);
                list.add(dish);
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot select dishes", e);
            throw new DAOException("Cannot select dishes", e);
        }
        return list;
    }

    @Override
    public int insertDish(String name, String ingredients, int weight, int calories, int price, int categoryId, String imagePath) throws DAOException {
        LOGGER.info("Run insertDish method");
        int id;
        try (Connection con = DBUtils.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(ADD_DISH, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, ingredients);
            ps.setInt(3, weight);
            ps.setInt(4, calories);
            ps.setInt(5, price);
            ps.setInt(6, categoryId);
            ps.setString(7, imagePath);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            id = generatedKeys.getInt(1);
        } catch (SQLException e) {
            LOGGER.error("Cannot insert dish", e);
            throw new DAOException("Cannot insert dish", e);
        }
        return id;
    }

    @Override
    public void updateDish(String name, String ingredients, int weight, int calories, int price, int categoryId, String imagePath, int id) throws DAOException {
        LOGGER.info("Run updateDish method");
        try (Connection con = DBUtils.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_DISH_BY_ID)) {
            ps.setString(1, name);
            ps.setString(2, ingredients);
            ps.setInt(3, weight);
            ps.setInt(4, calories);
            ps.setInt(5, price);
            ps.setInt(6, categoryId);
            ps.setString(7, imagePath);
            ps.setInt(8, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot update dish", e);
            throw new DAOException("Cannot update dish", e);
        }
    }

    @Override
    public void deleteDish(int dishId) throws DAOException {
        LOGGER.info("Run deleteDish method");
        try (Connection con = DBUtils.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_DISH_BY_ID)) {
            ps.setInt(1, dishId);
            int i = ps.executeUpdate();
            if (i < 1) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot delete dish by id", e);
            throw new DAOException("Cannot delete dish", e);
        }
    }

    @Override
    public List<Dish.Category> selectCategories() throws DAOException {
        LOGGER.info("Run selectCategories method");
        List<Dish.Category> list = new ArrayList<>();
        try (Connection con = DBUtils.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ALL_CATEGORIES)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(mapCategory(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot select all categories", e);
            throw new DAOException("Cannot select dishes categories", e);
        }
        return list;
    }

    @Override
    public int insertCategory(String categoryName) throws DAOException {
        LOGGER.info("Running insertCategory method");
        int id;
        try (Connection con = DBUtils.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(ADD_CATEGORY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, categoryName);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            id = generatedKeys.getInt(1);
        } catch (SQLException e) {
            LOGGER.error("Cannot insert this category", e);
            throw new DAOException("Cannot insert this category", e);
        }
        return id;
    }

    @Override
    public void editCategory(int categoryId) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteCategory(int categoryId) throws DAOException {
        LOGGER.info("Running deleteCategory method");
        try (Connection con = DBUtils.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_CATEGORY_BY_ID)) {
            ps.setInt(1, categoryId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot delete category by id", e);
            throw new DAOException("Cannot delete category", e);
        }
    }


        private  Dish mapDish(ResultSet rs) throws SQLException {
        LOGGER.info("Mapping Dish");
        Dish dish = mapDishContent(rs);
        Dish.Category category = mapCategory(rs);
        dish.setCategory(category);
        return dish;
        }

    private  Dish.Category mapCategory(ResultSet rs) throws SQLException {
        LOGGER.info("Mapping Category");
        Dish.Category category = new Dish.Category();
        category.setId(rs.getInt("category" + "." + ID));
        category.setName(rs.getString("category" + "." + NAME));
        return category;
    }

    private  Dish mapDishContent(ResultSet rs) throws SQLException {
        LOGGER.info("Mapping Dish content");
        Dish dish = new Dish();
        dish.setId(rs.getInt(ID));
        dish.setName(rs.getString(NAME));
        dish.setIngredients(rs.getString(INGREDIENTS));
        dish.setWeight(rs.getInt(WEIGHT));
        dish.setCalories(rs.getInt(CALORIES));
        dish.setPrice(rs.getInt(PRICE));
        dish.setImagePath(rs.getString(IMAGE_PATH));
        return dish;
    }
}
