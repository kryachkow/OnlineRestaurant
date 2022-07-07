package com.onlinerestaurant.db.service;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.dao.DishDAO;
import com.onlinerestaurant.db.dao.DishDAOImpl;
import com.onlinerestaurant.db.entity.Dish;
import com.onlinerestaurant.servlet.ConstantFields;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class DishService {
    public static final String CATEGORY_ID = "categoryId";
    public static final String PRICE_FROM = "priceFrom";
    public static final String PRICE_TO = "priceTo";
    public static final String SORT_BY = "sortBy";

    private static final DishDAO DISH_DAO = DishDAOImpl.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(DishService.class.getName());

    public static Dish getDishById(int id) throws DAOException {
        LOGGER.info("Run getDishById method");
        return DISH_DAO.selectDishById(id);
    }

    public static List<Dish> getDishes(Map<String, String> sortMap, Integer offset, Integer limit) throws DAOException {
        LOGGER.info("Run getDishes method");
        String categoryId = sortMap.getOrDefault(CATEGORY_ID,"");
        String priceFrom = sortMap.getOrDefault(PRICE_FROM, "");
        String priceTo = sortMap.getOrDefault(PRICE_TO, "");
        String sortBy = sortMap.getOrDefault(SORT_BY, "");
        return DISH_DAO.selectDishes(categoryId, priceFrom, priceTo, sortBy ,offset, limit);
    }

    public static int addCategory(String categoryName) throws DAOException {
        LOGGER.info("Run addCategory method");
        return DISH_DAO.insertCategory(categoryName);
    }
    public static void deleteCategory(int categoryId) throws DAOException{
        LOGGER.info("Run deleteCategory method");
        DISH_DAO.deleteCategory(categoryId);
    }
    public static List<Dish.Category> getAllCategories() throws DAOException {
        LOGGER.info("Run getAllCategories method");
        return DISH_DAO.selectCategories();
    }
    public static int addDish(Map<String, String> dishMap) throws DAOException {
        LOGGER.info("Run addDish method");
        return DISH_DAO.insertDish(dishMap.get(ConstantFields.DISH_NAME_KEY), dishMap.get(ConstantFields.DISH_INGREDIENTS_KEY), Integer.parseInt(dishMap.get(ConstantFields.DISH_WEIGHT)), Integer.parseInt(dishMap.get(ConstantFields.DISH_CALORIES)), Integer.parseInt(dishMap.get(ConstantFields.DISH_PRICE)), Integer.parseInt(dishMap.get(ConstantFields.CATEGORY_ID)), dishMap.get(ConstantFields.DISH_IMAGE_PATH));
    }

    public static void editDish(Map<String, String> dishMap) throws DAOException {
        LOGGER.info("Run editDish method");
        DISH_DAO.updateDish(dishMap.get(ConstantFields.DISH_NAME_KEY), dishMap.get(ConstantFields.DISH_INGREDIENTS_KEY), Integer.parseInt(dishMap.get(ConstantFields.DISH_WEIGHT)), Integer.parseInt(dishMap.get(ConstantFields.DISH_CALORIES)), Integer.parseInt(dishMap.get(ConstantFields.DISH_PRICE)), Integer.parseInt(dishMap.get(ConstantFields.CATEGORY_ID)), dishMap.get(ConstantFields.DISH_IMAGE_PATH), Integer.parseInt(dishMap.get(ConstantFields.DISH_ID_PARAMETER)));
    }

    public static void deleteDish(int dishId) throws DAOException {
        LOGGER.info("Run deleteDish method");
        DISH_DAO.deleteDish(dishId);
    }
}
