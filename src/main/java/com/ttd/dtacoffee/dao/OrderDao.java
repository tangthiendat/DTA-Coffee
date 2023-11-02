package com.ttd.dtacoffee.dao;

import com.ttd.dtacoffee.model.Order;
import com.ttd.dtacoffee.utility.DBUtils;

import java.sql.*;

public class OrderDao {

    public void save(Order newOrder) {
        final String SQL = "INSERT INTO `order`(order_id, created_date, total_value) VALUES (?,?,?)";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, newOrder.getOrderID());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(newOrder.getCreatedDate()));
            preparedStatement.setLong(3, newOrder.getTotalValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Order findLatestOrder(){
        final String SQL = "SELECT * FROM `order` WHERE created_date = (SELECT MAX(created_date) FROM `order`)";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return new Order(result.getString("order_id"), result.getTimestamp("created_date").toLocalDateTime(),
                        result.getInt("table_number"), result.getLong("total_value"),
                        result.getString("payment_status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
