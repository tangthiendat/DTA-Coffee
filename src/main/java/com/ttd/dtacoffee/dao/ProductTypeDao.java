package com.ttd.dtacoffee.dao;

import com.ttd.dtacoffee.model.ProductType;
import com.ttd.dtacoffee.utility.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductTypeDao {

    public void save(ProductType newProductType) {
        final String SQL = "INSERT INTO product_type VALUES (?, ?)";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, newProductType.getProductTypeID());
            preparedStatement.setString(2, newProductType.getProductTypeName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(ProductType updatedProductType) {
        final String SQL = "UPDATE product_type SET prodtype_name = ? WHERE prodtype_id = ?";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, updatedProductType.getProductTypeName());
            preparedStatement.setString(2, updatedProductType.getProductTypeID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<ProductType> findAll() {
        final String SQL = "SELECT * FROM product_type";
        List<ProductType> typetList = new ArrayList<>();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                typetList.add(new ProductType(result.getString("prodtype_id"), result.getString("prodtype_name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return typetList;
    }


    public List<ProductType> findAllAvailableTypes() {
        final String SQL = "SELECT DISTINCT product_type.prodtype_id , prodtype_name FROM product_type JOIN product ON product_type.prodtype_id = product.prodtype_id " +
                "WHERE status = 'Available'";
        List<ProductType> typetList = new ArrayList<>();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                typetList.add(new ProductType(result.getString("product_type.prodtype_id"),
                        result.getString("prodtype_name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return typetList;
    }

    public int countAll() {
        final String SQL = "SELECT COUNT(*) AS product_type_num FROM product_type";
        List<ProductType> typetList = new ArrayList<>();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return result.getInt("product_type_num");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
