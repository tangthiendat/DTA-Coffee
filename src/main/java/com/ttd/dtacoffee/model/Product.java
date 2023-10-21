package com.ttd.dtacoffee.model;

public class Product {
    private Integer productID;
    private String productName;
    private String productType;
    private Integer unitPrice;
    private String productStatus;

    public Product(String productName, String productType, Integer unitPrice, String productStatus) {
        this.productName = productName;
        this.productType = productType;
        this.unitPrice = unitPrice;
        this.productStatus = productStatus;
    }

    public Product(Integer productID, String productName, String productType, Integer unitPrice, String productStatus) {
        this.productID = productID;
        this.productName = productName;
        this.productType = productType;
        this.unitPrice = unitPrice;
        this.productStatus = productStatus;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }
}
