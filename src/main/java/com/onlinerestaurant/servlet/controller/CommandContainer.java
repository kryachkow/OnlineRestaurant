package com.onlinerestaurant.servlet.controller;

import com.onlinerestaurant.servlet.command.*;
import com.onlinerestaurant.servlet.command.guest.LoginCommand;
import com.onlinerestaurant.servlet.command.guest.RegisterCommand;
import com.onlinerestaurant.servlet.command.manager.*;
import com.onlinerestaurant.servlet.command.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static Map<String, Command> commands;

    static {
        commands = new HashMap<>();
        commands.put("login", new LoginCommand());
        commands.put("register", new RegisterCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("users", new UsersCommand());
        commands.put("menu", new MenuCommand());
        commands.put("addToCart", new AddToCartCommand());
        commands.put("myOrder", new MyOrderCommand());
        commands.put("editDish", new EditDishCommand());
        commands.put("addDish", new AddDishCommand());
        commands.put("deleteDish", new DeleteDishCommand());
        commands.put("manageCart", new ManageCartCommand());
        commands.put("makeOrder", new MakeOrderCommand());
        commands.put("orders", new OrdersCommand());
        commands.put("updateOrderStatus", new UpdateOrderCommand());
        commands.put("editProfile", new EditProfileCommand());
        commands.put("addCategory", new AddCategoryCommand());
        commands.put("deleteCategory", new DeleteCategoryCommand());
        commands.put("changeLocale", new ChangeLocaleCommand());
    }

    public static Command getCommand(String commandName){
        return commands.get(commandName);
    }
}
