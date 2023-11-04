package com.ttd.dtacoffee.dao;

import com.ttd.dtacoffee.model.Order;
import com.ttd.dtacoffee.model.OrderDetail;
import com.ttd.dtacoffee.model.Product;
import com.ttd.dtacoffee.model.ProductType;
import com.ttd.dtacoffee.utility.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public List<OrderDetail> findByOrderID(String orderID){
        final String SQL = """
                SELECT * FROM order_details od JOIN dtacoffee.`order` o on o.order_id = od.order_id
                    JOIN dtacoffee.product p on p.product_id = od.product_id
                    JOIN dtacoffee.product_type pt on pt.prodtype_id = p.prodtype_id
                    WHERE o.order_id = ?""";
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, orderID);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()){
                ProductType productType = new ProductType(result.getString("pt.prodtype_id"),
                        result.getString("prodtype_name"));
                Product product = new Product(result.getString("p.product_id"),
                        result.getString("product_name"), productType, result.getInt("p.unit_price"),
                        result.getString("status"));
                Order order = new Order(result.getString("o.order_id"),
                        result.getString("table_number"),
                        result.getLong("total_value"), result.getBoolean("paid"));
                orderDetailList.add(new OrderDetail(order, product, result.getInt("quantity"),
                        result.getInt("od.unit_price")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderDetailList;
    }

}
