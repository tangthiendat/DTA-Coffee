package com.ttd.dtacoffee.dao;

import com.ttd.dtacoffee.model.Product;
import com.ttd.dtacoffee.model.ProductType;
import com.ttd.dtacoffee.utility.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    public List<Product> findAll() {
        final String SQL = "SELECT * FROM product JOIN dtacoffee.product_type pt on pt.prodtype_id = product.prodtype_id";
        List<Product> productList = new ArrayList<>();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                productList.add(new Product(result.getString("product_id"), result.getString("product_name"),
                        new ProductType(result.getString("product.prodtype_id"), result.getString("prodtype_name")),
                        result.getInt("unit_price"), result.getString("status")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    public void save(Product newProduct) {
        final String SQL = "INSERT INTO product(product_id,product_name, prodtype_id, unit_price, status) VALUES (?, ?, ?, ?, ?)";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, newProduct.getProductID());
            preparedStatement.setString(2, newProduct.getProductName());
            preparedStatement.setString(3, newProduct.getProductType().getProductTypeID());
            preparedStatement.setInt(4, newProduct.getUnitPrice());
            preparedStatement.setString(5, newProduct.getProductStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Product selectedProduct) {
        final String SQL = "UPDATE product SET product_name = ?, prodtype_id = ?, unit_price = ?, status = ? WHERE product_id = ?";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, selectedProduct.getProductName());
            preparedStatement.setString(2, selectedProduct.getProductType().getProductTypeID());
            preparedStatement.setInt(3, selectedProduct.getUnitPrice());
            preparedStatement.setString(4, selectedProduct.getProductStatus());
            preparedStatement.setString(5, selectedProduct.getProductID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Product selectedProduct) {
        final String SQL = "DELETE FROM product WHERE product_id = ?";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, selectedProduct.getProductID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public List<String> findNameByTypeID(String productTypeID) {
        final String SQL = "SELECT product_name FROM product WHERE prodtype_id = ?";
        List<String> productNameList = new ArrayList<>();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, productTypeID);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                productNameList.add(result.getString("product_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productNameList;
    }

    public Product findByProductName(String productName){
        final String SQL = "SELECT * FROM product WHERE product_name = ?";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, productName);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return new Product(result.getString("product_id"), result.getString("product_name"),
                        new ProductType(result.getString("product.prodtype_id"), result.getString("prodtype_name")),
                        result.getInt("unit_price"), result.getString("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int countAll(){
        final String SQL = "SELECT COUNT(*) productQuantity FROM product";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getInt("productQuantity");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
