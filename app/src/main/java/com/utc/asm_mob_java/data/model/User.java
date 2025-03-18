package com.utc.asm_mob_java.data.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class User {
    @Expose
    private String name;
    @Expose
    private String email;
    @Expose
    private String phone;
    @Expose
    private String dateOfBirth;
    @Expose
    private List<DeliveryAddress> address;
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String avt;
    @Expose
    private boolean isLogin;
    @Expose
    private List<Cart> listCart;
    @Expose
    private List<Order> listOrder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<DeliveryAddress> getAddress() {
        return address;
    }

    public void setAddress(List<DeliveryAddress> address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    public User(String name, String email, String phone, String dateOfBirth, List<DeliveryAddress> address, String username, String password, String avt) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.username = username;
        this.password = password;
        this.avt = avt;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public List<Cart> getListCart() {
        return listCart;
    }

    public void setListCart(List<Cart> listCart) {
        this.listCart = listCart;
    }

    public List<Order> getListOrder() {
        return listOrder;
    }

    public void setListOrder(List<Order> listOrder) {
        this.listOrder = listOrder;
    }
}
