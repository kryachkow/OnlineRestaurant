package com.onlinerestaurant.db;

import com.onlinerestaurant.db.entity.Dish;
import com.onlinerestaurant.db.service.DishService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DishServiceTest {

    static private Connection con;

    @BeforeAll
    static void setUp() {
        con = DAOTestingUtils.setUpConnectionMethod();
        DAOTestingUtils.setUpDataBase();
    }

    @AfterAll
    static void tearDown() throws SQLException, FileNotFoundException {
        DAOTestingUtils.clearDataBase();
        con.close();
    }

    @Test
    void categoryTests() throws SQLException {
        Dish.Category dishCategory = new Dish.Category();
        dishCategory.setId(4);
        dishCategory.setName("AddedCategory");
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        List<Dish.Category> categoryList = DishService.getAllCategories();
        assertEquals(3, categoryList.size());

        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        DishService.addCategory("AddedCategory");
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        List<Dish.Category> updatedCategoryList = DishService.getAllCategories();
        assertEquals(4, updatedCategoryList.size());
        updatedCategoryList.sort(Comparator.comparing(Dish.Category::getId));
        assertEquals("AddedCategory", updatedCategoryList.get(3).getName());

        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        assertThrows(DAOException.class, () -> DishService.addCategory("AddedCategory"));
    }

    @Test
    void addingAndEditingDishTest() throws SQLException {
        Map<String, String> dishMap = new HashMap<>();
        dishMap.put("dishName","testDish");
        dishMap.put("dishIngredients", "testIngredients");
        dishMap.put("dishWeight","1");
        dishMap.put("dishCalories","2");
        dishMap.put("dishPrice","3");
        dishMap.put("categoryId","1");
        dishMap.put("dishImagePath","path");

        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        DishService.addDish(dishMap);
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        Dish testDish = DishService.getDishById(7);
        assertEquals(testDish.getName(),"testDish");
        assertEquals(testDish.getIngredients(),"testIngredients");
        assertEquals(testDish.getWeight(),1);
        assertEquals(testDish.getCalories(),2);
        assertEquals(testDish.getPrice(),3);
        assertEquals(testDish.getCategory().getId(),1);
        assertEquals(testDish.getImagePath(),"path");



        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        assertThrows(DAOException.class,
                () -> DishService.addDish(dishMap) );

        dishMap.put("dishName","changedName");
        dishMap.put("dishIngredients", "testIngredients");
        dishMap.put("dishWeight","1");
        dishMap.put("dishCalories","2");
        dishMap.put("dishPrice","3");
        dishMap.put("categoryId","1");
        dishMap.put("dishImagePath","path");
        dishMap.put("dishId", "7");

        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        DishService.editDish(dishMap);
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        Dish changedDish = DishService.getDishById(7);
        assertEquals(changedDish.getId(), 7);
        assertEquals(changedDish.getName(), "changedName");

        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        DishService.deleteDish(7);
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        assertThrows(DAOException.class, ()-> DishService.deleteDish(7) );
    }

    @Test
    void dishSortingPaginationTest() throws SQLException {
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection()).thenReturn(DAOTestingUtils.getConnection());
        Map<String,String> sortMap = new HashMap<>();
        List<Dish> dishes;
        dishes = DishService.getDishes(sortMap,0,4);
        dishes.sort(Comparator.comparing(Dish::getId));
        assertEquals("Карбонара",dishes.get(0).getName());
        assertEquals("Суші з лососем", dishes.get(1).getName());
        assertEquals("Удон з куркою та овочами", dishes.get(2).getName());
        assertEquals("Апельсинова Курка", dishes.get(3).getName());

        sortMap.put("priceFrom", "10");
        dishes = DishService.getDishes(sortMap,0,4);
        dishes.sort(Comparator.comparing(Dish::getId));
        assertEquals("Карбонара",dishes.get(0).getName());
        assertEquals("Суші з лососем", dishes.get(1).getName());
        assertEquals("Удон з куркою та овочами", dishes.get(2).getName());
        assertEquals("Апельсинова Курка", dishes.get(3).getName());

        sortMap.put("priceFrom", "150");
        sortMap.put("priceTo", "175");
        sortMap.put("categoryId", "3");
        sortMap.put("orderBy","ORDER BY dish.name");
        Mockito.when(DBUtils.getInstance().getConnection()).thenReturn(DAOTestingUtils.getConnection());
        dishes = DishService.getDishes(sortMap,0,4);
        assertEquals("Удон з куркою та овочами", dishes.get(0).getName());
        assertEquals("Апельсинова Курка", dishes.get(1).getName());


    }

}