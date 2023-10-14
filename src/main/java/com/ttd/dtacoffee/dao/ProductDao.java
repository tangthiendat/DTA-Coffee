package com.ttd.dtacoffee.dao;

import com.ttd.dtacoffee.model.Product;
import com.ttd.dtacoffee.utility.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    public List<Product> findAll() {
        final String SQL = "SELECT * FROM product";
        List<Product> productList = new ArrayList<>();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                productList.add(new Product(result.getInt("product_id"), result.getString("product_name"),
                        result.getString("type"), result.getInt("price"), result.getString("status")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    public void save(Product newProduct) {
        final String SQL = "INSERT INTO product(product_name, type, price, status) VALUES (?, ?, ?, ?)";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, newProduct.getProductName());
            preparedStatement.setString(2, newProduct.getProductType());
            preparedStatement.setInt(3, newProduct.getPrice());
            preparedStatement.setString(4, newProduct.getProductStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Product selectedProduct) {
        final String SQL = "UPDATE product SET product_name = ?, type = ?, price = ?, status = ? WHERE product_id = ?";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, selectedProduct.getProductName());
            preparedStatement.setString(2, selectedProduct.getProductType());
            preparedStatement.setInt(3, selectedProduct.getPrice());
            preparedStatement.setString(4, selectedProduct.getProductStatus());
            preparedStatement.setInt(5, selectedProduct.getProductID());
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
            preparedStatement.setInt(1, selectedProduct.getProductID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
