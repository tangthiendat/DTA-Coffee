package com.ttd.dtacoffee.dao;

import com.ttd.dtacoffee.model.OrderDetail;
import com.ttd.dtacoffee.utility.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDao {

    public void save(List<OrderDetail> orderDetailList) {
        final String SQL = "INSERT INTO order_details(order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            for(OrderDetail orderDetail : orderDetailList){
                preparedStatement.setString(1, orderDetail.getOrder().getOrderID());
                preparedStatement.setString(2, orderDetail.getProduct().getProductID());
                preparedStatement.setInt(3, orderDetail.getQuantity());
                preparedStatement.setInt(4, orderDetail.getUnitPrice());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
