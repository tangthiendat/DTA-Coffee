package com.ttd.dtacoffee.model;

import java.time.LocalDateTime;

public class Order {
    private String orderID;
    private LocalDateTime createdDate;
    private Integer tableNumber;
    private Long totalValue;
    private String paymentStatus;

    public Order(String orderID, LocalDateTime createdDate, Integer tableNumber, Long totalValue, String paymentStatus) {
        this.orderID = orderID;
        this.createdDate = createdDate;
        this.tableNumber = tableNumber;
        this.totalValue = totalValue;
        this.paymentStatus = paymentStatus;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Long getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Long totalValue) {
        this.totalValue = totalValue;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
