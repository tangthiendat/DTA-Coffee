package com.ttd.dtacoffee.model;

import java.time.LocalDateTime;

public class Order {
    private String orderID;
    private LocalDateTime createdDate;
    private String tableNumber;
    private Long totalValue;
    private boolean paid;

    public Order(){

    }

    public Order(String orderID, LocalDateTime createdDate, String tableNumber, Long totalValue, boolean paid) {
        this.orderID = orderID;
        this.createdDate = createdDate;
        this.tableNumber = tableNumber;
        this.totalValue = totalValue;
        this.paid = paid;
    }

    public Order(String orderID, String tableNumber, Long totalValue, boolean paid) {
        this.orderID = orderID;
        this.tableNumber = tableNumber;
        this.totalValue = totalValue;
        this.paid = paid;
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

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Long getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Long totalValue) {
        this.totalValue = totalValue;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
