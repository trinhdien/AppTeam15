package com.utc.asm_mob_java.data.source.request;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class NewsRequest {
    @Expose
    private String userId;
    @Expose
    private String newsId;
    @Expose
    private int customerId;
    @Expose
    private int area;
    @Expose
    private int price;
    @Expose
    private String description;
    @Expose
    private String status;
    @Expose
    private String furnitureStatus;
    @Expose
    private int deposit;
    @Expose
    private String title;
    @Expose
    private String customerType;
    @Expose
    private String address;
    @Expose
    private List<String> images;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFurnitureStatus() {
        return furnitureStatus;
    }

    public void setFurnitureStatus(String furnitureStatus) {
        this.furnitureStatus = furnitureStatus;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
