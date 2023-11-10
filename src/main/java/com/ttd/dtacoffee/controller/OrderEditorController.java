package com.ttd.dtacoffee.controller;

import com.ttd.dtacoffee.dao.OrderDao;
import com.ttd.dtacoffee.dao.OrderDetailDao;
import com.ttd.dtacoffee.model.BillDetail;
import com.ttd.dtacoffee.model.Order;
import com.ttd.dtacoffee.model.OrderDetail;
import com.ttd.dtacoffee.utility.CurrencyUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderEditorController implements Initializable {

    @FXML
    private Label orderID;

    @FXML
    private Label orderCreatedDate;

    @FXML
    private Label tableNumber;

    @FXML
    private TableView<OrderDetail> orderDetailTable;

    @FXML
    private TableColumn<OrderDetail, String> productNameCol;

    @FXML
    private TableColumn<OrderDetail, String> quantityCol;

    @FXML
    private TableColumn<OrderDetail, String> unitPriceCol;

    @FXML
    private TableColumn<OrderDetail, String> totalCol;

    @FXML
    private Label orderTotalValue;

    @FXML
    private TextField customerCashField;

    @FXML
    private Label orderChange;

    @FXML
    private Button exportOrderBtn;


    private final OrderDetailDao orderDetailDao = new OrderDetailDao();
    private final OrderDao orderDao = new OrderDao();
    private Order updatedOrder;
    private List<OrderDetail> orderDetailList;
    private final List<BillDetail> billData = new ArrayList<>();

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        Image errorImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/ic_error.png")));
        ImageView errorIcon = new ImageView(errorImg);
        errorIcon.setFitHeight(48);
        errorIcon.setFitWidth(48);
        alert.getDialogPane().setGraphic(errorIcon);
        alert.setContentText("Số tiền nhập vào chưa hợp lệ.");
        alert.showAndWait();
    }

    @FXML
    public void removeErrorEffect(MouseEvent event) {
        if (event.getSource().equals(customerCashField)) {
            customerCashField.getStyleClass().removeAll("error-field");
        }
    }

    private void setClearSelectionOnDoubleClick(){
        orderDetailTable.setRowFactory(tableView ->{
            TableRow<OrderDetail> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    orderDetailTable.getSelectionModel().clearSelection();
                }
            });
            return row;
        });
    }

    @FXML
    public void showOrderChange(){
        String customerCash = customerCashField.getText();
        long total = CurrencyUtils.getValue(orderTotalValue.getText().replace("đ", ""));
        if(!customerCash.matches("\\d+") || Long.parseLong(customerCash) < total){
            customerCashField.getStyleClass().add("error-field");
            showErrorAlert();
        } else {
            orderChange.setText(CurrencyUtils.format(Long.parseLong(customerCash) - total ) + "đ");
        }
    }

    public void showFullOrder(Order selectedOrder){
        updatedOrder = new Order(selectedOrder.getOrderID(), selectedOrder.getCreatedDate(), selectedOrder.getTableNumber(),
                selectedOrder.getTotalValue(), selectedOrder.getPaid());
        orderID.setText(selectedOrder.getOrderID());
        LocalDateTime createdDate = selectedOrder.getCreatedDate();
        if(createdDate == null){
            orderCreatedDate.setText("");
        }else {
            orderCreatedDate.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss").format(selectedOrder.getCreatedDate()));
        }
        tableNumber.setText(selectedOrder.getTableNumber());
        orderTotalValue.setText(CurrencyUtils.format(selectedOrder.getTotalValue()) + "đ");
        showOrderDetailTable(selectedOrder);
    }


    private void showOrderDetailTable(Order selectedOrder){
        orderDetailList = orderDetailDao.findByOrderID(selectedOrder.getOrderID());
        productNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getProductName()));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(CurrencyUtils.format(cellData.getValue().getUnitPrice())));
        totalCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(CurrencyUtils.format(cellData.getValue().getTotal())));
        orderDetailTable.setItems(FXCollections.observableList(orderDetailList));
    }

    @FXML
    public void exportOrder() throws JRException {
        for(OrderDetail orderDetail : orderDetailList){
            billData.add(new BillDetail(orderDetail.getProduct().getProductName(), CurrencyUtils.format(orderDetail.getUnitPrice()),
                    orderDetail.getQuantity(), CurrencyUtils.format(orderDetail.getTotal())));
        }
        updatedOrder.setCreatedDate(LocalDateTime.now());
        updatedOrder.setPaid(true);
        JasperReport billReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/print/order.jrxml"));
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("order_id", updatedOrder.getOrderID());
        parameters.put("created_date", Timestamp.valueOf(updatedOrder.getCreatedDate()));
        parameters.put("table_number", updatedOrder.getTableNumber());
        parameters.put("total", CurrencyUtils.format(updatedOrder.getTotalValue()) + "đ");
        parameters.put("cash", CurrencyUtils.format(Long.parseLong(customerCashField.getText())) + "đ");
        parameters.put("change", CurrencyUtils.format(Long.parseLong(customerCashField.getText()) - updatedOrder.getTotalValue()) + "đ");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(billData);
        JasperPrint print = JasperFillManager.fillReport(billReport, parameters, dataSource);
        JasperViewer.viewReport(print, false);
        //Update order created date and paid in database
        orderDao.update(updatedOrder);
        //Close the window
        Stage stage = (Stage) exportOrderBtn.getScene().getWindow();
        stage.close();
    }

    public Order getUpdatedOrder(){
        return updatedOrder;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setClearSelectionOnDoubleClick();
    }
}
