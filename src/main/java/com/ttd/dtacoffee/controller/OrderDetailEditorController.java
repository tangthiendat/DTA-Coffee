package com.ttd.dtacoffee.controller;

import com.ttd.dtacoffee.dao.ProductDao;
import com.ttd.dtacoffee.model.OrderDetail;
import com.ttd.dtacoffee.model.Product;
import com.ttd.dtacoffee.utility.CurrencyUtils;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderDetailEditorController implements Initializable {
    @FXML
    private ComboBox<String> orderDetailEditor_nameField;

    @FXML
    private Spinner<Integer> orderDetailEditor_quantityField;

    @FXML
    private Button orderDetailEditor_saveBtn;

    @FXML
    private ComboBox<String> orderDetailEditor_typeField;

    @FXML
    private TextField orderDetailEditor_unitPriceField;
    private final ProductDao productDao = new ProductDao();

    private OrderDetail updatedOrderDetail;

    public void showSelectedOrderDetail(OrderDetail selectedOrderDetail){
        orderDetailEditor_typeField.getSelectionModel().select(selectedOrderDetail.getProduct().getProductType());
        orderDetailEditor_nameField.getSelectionModel().select(selectedOrderDetail.getProduct().getProductName());
        orderDetailEditor_unitPriceField.setText(CurrencyUtils.format(selectedOrderDetail.getUnitPrice()));
        orderDetailEditor_quantityField.getValueFactory().setValue(selectedOrderDetail.getQuantity());
    }

    public OrderDetail saveChange(){
        String newProductName = orderDetailEditor_nameField.getSelectionModel().getSelectedItem();
        Product newProduct = productDao.findByProductName(newProductName);
        Integer newUnitPrice = CurrencyUtils.getValue(orderDetailEditor_unitPriceField.getText());
        Integer newQuantity = orderDetailEditor_quantityField.getValue();
        Stage stage = (Stage) orderDetailEditor_saveBtn.getScene().getWindow();
        stage.close();
        return new OrderDetail(newProduct, newQuantity, newUnitPrice);
    }


    @SafeVarargs
    public final void makeArrowPointUpwards(ComboBox<String>... comboBoxList){
        for(ComboBox<String> comboBox : comboBoxList){
            comboBox.showingProperty().addListener(((observable, notShowing, isNowShowing) -> {
                if(isNowShowing){
                    comboBox.getStyleClass().add("combobox-up");
                } else {
                    comboBox.getStyleClass().remove("combobox-up");
                }
            }));
        }
    }

    //Show all available types
    public void setShoppingType(){
        orderDetailEditor_typeField.setItems(FXCollections.observableArrayList(productDao.findAllAvailableTypes()));
    }

    //Display product name according to type
    public void setShoppingProduct(){
        orderDetailEditor_nameField.itemsProperty().bind(Bindings.createObjectBinding(() ->{
            String type = orderDetailEditor_typeField.getSelectionModel().getSelectedItem();
            if(type == null){
                return null;
            }
            return FXCollections.observableArrayList(productDao.findNameByType(type));
        },orderDetailEditor_typeField.valueProperty()));
    }

    //Show shopping price after choosing product name
    public void showShoppingPrice(){
        String productName = orderDetailEditor_nameField.getSelectionModel().getSelectedItem();
        if(productName != null){
            Product selectedProduct = productDao.findByProductName(productName);
            orderDetailEditor_unitPriceField.setText(CurrencyUtils.format(selectedProduct.getUnitPrice()));
        } else{
            orderDetailEditor_unitPriceField.setText("");
        }
    }

    public void setShoppingQuantity(){
        orderDetailEditor_quantityField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeArrowPointUpwards(orderDetailEditor_typeField, orderDetailEditor_nameField);
        setShoppingType();
        setShoppingProduct();
        setShoppingQuantity();
    }
}
