package com.ttd.dtacoffee.dao;

import com.ttd.dtacoffee.model.Order;
import com.ttd.dtacoffee.utility.DBUtils;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderDao {

    public void save(Order newOrder) {
        final String SQL = "INSERT INTO `order`(order_id, created_date, table_number, total_value, payment_status) " +
                "VALUES (?,?,?,?,?)";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, newOrder.getOrderID());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(newOrder.getCreatedDate()));
            preparedStatement.setString(3, newOrder.getTableNumber());
            preparedStatement.setLong(4, newOrder.getTotalValue());
            preparedStatement.setString(5, newOrder.getPaymentStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUnpaidOrder(Order unpaidOrder) {
        final String SQL = "INSERT INTO `order`(order_id, table_number, total_value, payment_status) " +
                "VALUES (?,?,?,?)";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, unpaidOrder.getOrderID());
            preparedStatement.setString(2, unpaidOrder.getTableNumber());
            preparedStatement.setLong(3, unpaidOrder.getTotalValue());
            preparedStatement.setString(4, unpaidOrder.getPaymentStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void update(Order updatedOrder) {
        final String SQL = "UPDATE `order` SET created_date = ?, payment_status = ? WHERE order_id = ?";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(updatedOrder.getCreatedDate()));
            preparedStatement.setString(2, updatedOrder.getPaymentStatus());
            preparedStatement.setString(3, updatedOrder.getOrderID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findLatestOrderCounter(){
        final String SQL = "SELECT MAX(CAST(SUBSTRING(order_id, 7) AS UNSIGNED)) AS latest_order_counter FROM `order` " +
                "WHERE SUBSTRING(order_id, 1, 6) = ?";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, DateTimeFormatter.ofPattern("yyMMdd").format(LocalDate.now()));
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return result.getInt("latest_order_counter");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

}
