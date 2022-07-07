package com.onlinerestaurant.db.dao;

import com.onlinerestaurant.db.DAOException;
import com.onlinerestaurant.db.entity.Dish;

import java.util.List;

public interface DishDAO {
    Dish selectDishById(int id) throws DAOException;
    List<Dish> selectDishes(String categoryId, String priceFrom, String priceTo, String sortBy, int offset, int limit) throws DAOException;
    int insertDish(String name, String ingredients, int weight, int calories, int price, int categoryId, String imagePath) throws DAOException;
    void updateDish(String name, String ingredients, int weight, int calories, int price, int categoryId, String imagePath, int id) throws DAOException;
    void deleteDish(int dishId) throws DAOException;
    List<Dish.Category> selectCategories() throws DAOException;
    int insertCategory(String categoryName) throws DAOException;
    void editCategory(int categoryId) throws DAOException;
    void deleteCategory(int categoryId) throws DAOException;

}
