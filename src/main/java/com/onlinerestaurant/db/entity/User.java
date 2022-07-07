package com.onlinerestaurant.db.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String email;
    private String name;
    private String password;
    private String phone;
    private String address;
    private Role role;

    private boolean banned;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() { return email; }
    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public Role getRole() {
        return role;
    }

    public boolean isBanned(){return banned;}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) { this.email = email;}

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setBanned(boolean isBanned) {this.banned = isBanned;}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                ", isBanned=" + banned +
                '}';
    }


    public enum Role {
        MANAGER, USER;

        public boolean isUser() {
            return this == USER;
        }
        public boolean isAdmin() {
            return this == MANAGER;
        }

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }




}
