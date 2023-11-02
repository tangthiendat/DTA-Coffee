package com.ttd.dtacoffee.model;

public class ProductType {
    String productTypeID;
    String productTypeName;

    public ProductType(String productTypeID, String productTypeName) {
        this.productTypeID = productTypeID;
        this.productTypeName = productTypeName;
    }

    public String getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(String productTypeID) {
        this.productTypeID = productTypeID;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    @Override
    public String toString() {
        return this.productTypeName;
    }
}
