package com.ttd.dtacoffee.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductType that = (ProductType) o;
        return Objects.equals(productTypeID, that.productTypeID) && Objects.equals(productTypeName, that.productTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productTypeID, productTypeName);
    }
}
