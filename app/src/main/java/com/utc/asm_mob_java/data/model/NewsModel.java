package com.utc.asm_mob_java.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NewsModel {

    @Expose
    @SerializedName("ROOM_NEWS_ID")
    private int roomId;
    @Expose
    @SerializedName("CUSTOMER_ID")
    private int customerId;
    @Expose
    @SerializedName("AREA")
    private int area;
    @Expose
    @SerializedName("PRICE")
    private int price;
    @Expose
    @SerializedName("DESCRIPTION")
    private String description;
    @Expose
    @SerializedName("STATUS")
    private String status;
    @Expose
    @SerializedName("FURNITURE_STATUS")
    private String furnitureStatus;
    @Expose
    @SerializedName("DEPOSIT")
    private int deposit;
    @Expose
    @SerializedName("TITLE")
    private String title;
    @Expose
    @SerializedName("CUSTOMER_TYPE")
    private String customerType;
    @Expose
    @SerializedName("POST_TIME")
    private Date postTime;
    @Expose
    @SerializedName("ADDRESS")
    private String address;
    @Expose
    @SerializedName("ISDRAFT")
    private int isDraft;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFurnitureStatus() {
        return furnitureStatus;
    }

    public void setFurnitureStatus(String furnitureStatus) {
        this.furnitureStatus = furnitureStatus;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsDraft() {
        return isDraft;
    }

    public void setIsDraft(int isDraft) {
        this.isDraft = isDraft;
    }
}
