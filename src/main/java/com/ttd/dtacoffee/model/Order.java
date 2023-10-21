package com.ttd.dtacoffee.model;

import java.time.LocalDateTime;

public class Order {
    private Integer orderID;
    private String orderNumber;
    private LocalDateTime createdDate;
    private Long totalValue;

    public Order(String orderNumber, LocalDateTime createdDate, Long totalValue) {
        this.orderNumber = orderNumber;
        this.createdDate = createdDate;
        this.totalValue = totalValue;
    }

    public Order(Integer orderID, String orderNumber, LocalDateTime createdDate, Long totalValue) {
        this.orderID = orderID;
        this.orderNumber = orderNumber;
        this.createdDate = createdDate;
        this.totalValue = totalValue;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Long totalValue) {
        this.totalValue = totalValue;
    }
}
