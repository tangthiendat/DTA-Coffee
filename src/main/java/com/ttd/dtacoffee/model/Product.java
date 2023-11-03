package com.ttd.dtacoffee.model;

public class Product {
    private String productID;
    private String productName;
    private ProductType productType;
    private Integer unitPrice;
    private String productStatus;

    public Product(String productName, ProductType productType, Integer unitPrice, String productStatus) {
        this.productName = productName;
        this.productType = productType;
        this.unitPrice = unitPrice;
        this.productStatus = productStatus;
    }

    public Product(String productID, String productName, ProductType productType, Integer unitPrice, String productStatus) {
        this.productID = productID;
        this.productName = productName;
        this.productType = productType;
        this.unitPrice = unitPrice;
        this.productStatus = productStatus;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
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

    @Override
    public String toString() {
        return this.productName;
    }
}
