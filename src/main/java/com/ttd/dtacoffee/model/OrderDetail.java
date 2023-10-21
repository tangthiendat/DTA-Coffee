package com.ttd.dtacoffee.model;

public class OrderDetail {
    Integer orderID;
    Product product;
    Integer quantity;
    Integer unitPrice;
    Long totalValue;

    public OrderDetail(Integer orderID, Product product, Integer quantity, Integer unitPrice) {
        this.orderID = orderID;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalValue = (long) quantity * unitPrice;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Product getProductID() {
        return product;
    }

    public void setProductID(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }
}