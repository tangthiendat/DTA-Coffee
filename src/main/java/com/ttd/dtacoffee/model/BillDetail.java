package com.ttd.dtacoffee.model;

public class BillDetail {
    String productName;
    String unitPrice;
    Integer quantity;
    String total;

    public BillDetail(String productName, String unitPrice, Integer quantity, String total) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.total = total;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
