package com.ttd.dtacoffee.dao;

import com.ttd.dtacoffee.model.Order;
import com.ttd.dtacoffee.utility.DBUtils;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class OrderDao {

    public void save(Order newOrder) {
        final String SQL = "INSERT INTO `order`(order_id, created_date, table_number, total_value, paid) " +
                "VALUES (?,?,?,?,?)";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, newOrder.getOrderID());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(newOrder.getCreatedDate()));
            preparedStatement.setString(3, newOrder.getTableNumber());
            preparedStatement.setLong(4, newOrder.getTotalValue());
            preparedStatement.setBoolean(5, newOrder.getPaid());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUnpaidOrder(Order unpaidOrder) {
        final String SQL = "INSERT INTO `order`(order_id, table_number, total_value, paid) " +
                "VALUES (?,?,?,?)";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, unpaidOrder.getOrderID());
            preparedStatement.setString(2, unpaidOrder.getTableNumber());
            preparedStatement.setLong(3, unpaidOrder.getTotalValue());
            preparedStatement.setBoolean(4, unpaidOrder.getPaid());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Order updatedOrder) {
        final String SQL = "UPDATE `order` SET created_date = ?, paid = ? WHERE order_id = ?";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(updatedOrder.getCreatedDate()));
            preparedStatement.setBoolean(2, updatedOrder.getPaid());
            preparedStatement.setString(3, updatedOrder.getOrderID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> findAll() {
        final String SQL = "SELECT * FROM `order`";
        List<Order> orderList = new ArrayList<>();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                Timestamp createdDate = result.getTimestamp("created_date");
                orderList.add(new Order(result.getString("order_id"), createdDate != null ? createdDate.toLocalDateTime() : null,
                        result.getString("table_number"), result.getLong("total_value"),
                        result.getBoolean("paid")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList;
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

    public long findTotalSales(){
        final String SQL = "SELECT SUM(total_value) AS total_sales FROM `order` WHERE paid = true";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return result.getLong("total_sales");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int countAllPaidOrder(){
        final String SQL = "SELECT COUNT(order_id) AS paid_order_num FROM `order` WHERE paid = true";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return result.getInt("paid_order_num");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


    public TreeMap<Integer, Long> findCurrentMonthSalesReport(){
        final String SQL = """
                SELECT DAY (created_date) day, SUM(total_value) sales
                FROM `order` WHERE MONTH(created_date) = MONTH(CURRENT_DATE)
                GROUP BY DAY (created_date)""";
        TreeMap<Integer, Long> currentMonthSalesReport = new TreeMap<>();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()){
                currentMonthSalesReport.put(result.getInt("day"), result.getLong("sales"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currentMonthSalesReport;
    }

    public TreeMap<Integer, Long> findCurrentYearSalesReport(){
        final String SQL = """
                SELECT MONTH (created_date) month, SUM(total_value) sales
                FROM `order` WHERE YEAR(created_date) = YEAR(CURRENT_DATE)
                GROUP BY MONTH (created_date)""";
        TreeMap<Integer, Long> currentYearSalesReport = new TreeMap<>();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()){
                currentYearSalesReport.put(result.getInt("month"), result.getLong("sales"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currentYearSalesReport;
    }

}
