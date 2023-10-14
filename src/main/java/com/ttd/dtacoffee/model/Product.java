package com.ttd.dtacoffee.model;

public class Product {
    private Integer productID;
    private String productName;
    private String productType;
    private Integer price;
    private String productStatus;

    public Product(String productName, String productType, Integer price, String productStatus) {
        this.productName = productName;
        this.productType = productType;
        this.price = price;
        this.productStatus = productStatus;
    }

    public Product(Integer productID, String productName, String productType, Integer price, String productStatus) {
        this.productID = productID;
        this.productName = productName;
        this.productType = productType;
        this.price = price;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }
}
