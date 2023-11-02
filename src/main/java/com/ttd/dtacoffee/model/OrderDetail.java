package com.ttd.dtacoffee.model;

public class OrderDetail {
    Order order;
    Product product;
    Integer quantity;
    Integer unitPrice;
    Long total;

    public OrderDetail(Order order, Product product, Integer quantity, Integer unitPrice) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = (long) quantity * unitPrice;
    }

    public OrderDetail(Product product, Integer quantity, Integer unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = (long) quantity * unitPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrderID(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
