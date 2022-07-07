package com.onlinerestaurant.db.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private int id;
    private User user;
    private String deliveryAddress;
    private Status status;
    private Date createTime;
    private List<CartContent> contentList;

    private int totalPrice;

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getDeliveryAddress() {return deliveryAddress;}

    public Status getStatus() {
        return status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public List<CartContent> getContentList() {
        return contentList;
    }

    public int getTotalPrice(){return totalPrice;};

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDeliveryAddress(String deliveryAddress) {this.deliveryAddress = deliveryAddress;}

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setContentList(List<CartContent> contentList) {
        this.contentList = contentList;
    }

    public void setTotalPrice(int totalPrice){this.totalPrice = totalPrice;};

    public enum Status  {
        CREATED, COOKING, DELIVERING, DONE, CANCELLED;
        public String toString() {
            return super.toString().toLowerCase();
        }
        public int getId() {
            return ordinal();
        }
        public String getStatus() {
            return toString();
        }
    }

    public static class CartContent implements Serializable{
        private Dish dish;
        private int quantity;
        private int price;

        public Dish getDish() {
            return dish;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getPrice() {
            return price;
        }

        public void setDish(Dish dish) {
            this.dish = dish;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "CartContent{" +
                    "dish=" + dish +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    '}';
        }
    }

}
