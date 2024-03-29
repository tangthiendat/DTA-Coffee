package com.ttd.dtacoffee.controller;

import com.ttd.dtacoffee.dao.ProductDao;
import com.ttd.dtacoffee.dao.ProductTypeDao;
import com.ttd.dtacoffee.model.OrderDetail;
import com.ttd.dtacoffee.model.Product;
import com.ttd.dtacoffee.model.ProductType;
import com.ttd.dtacoffee.utility.CurrencyUtils;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderDetailEditorController implements Initializable {
    @FXML
    private ComboBox<Product> nameField;

    @FXML
    private Spinner<Integer> quantityField;

    @FXML
    private Button saveBtn;

    @FXML
    private ComboBox<ProductType> typeField;

    @FXML
    private TextField unitPriceField;

    private final ProductDao productDao = new ProductDao();
    private final ProductTypeDao productTypeDao = new ProductTypeDao();

    private OrderDetail updatedOrderDetail;


    public void showSelectedOrderDetail(OrderDetail selectedOrderDetail){
        typeField.getSelectionModel().select(selectedOrderDetail.getProduct().getProductType());
        nameField.getSelectionModel().select(selectedOrderDetail.getProduct());
        unitPriceField.setText(CurrencyUtils.format(selectedOrderDetail.getUnitPrice()));
        quantityField.getValueFactory().setValue(selectedOrderDetail.getQuantity());
        updatedOrderDetail = new OrderDetail(selectedOrderDetail.getOrder(), selectedOrderDetail.getProduct(),
                selectedOrderDetail.getQuantity(), selectedOrderDetail.getUnitPrice());
    }

    @FXML
    public void saveChange(){
        Product newProduct = nameField.getValue();
        Integer newUnitPrice = CurrencyUtils.getValue(unitPriceField.getText());
        Integer newQuantity = quantityField.getValue();
        updatedOrderDetail.setProduct(newProduct);
        updatedOrderDetail.setUnitPrice(newUnitPrice);
        updatedOrderDetail.setQuantity(newQuantity);
        updatedOrderDetail.setTotal(((long) newUnitPrice *newQuantity));
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }

    public OrderDetail getUpdatedOrderDetail() {
        return updatedOrderDetail;
    }

    private void makeArrowPointUpwards(ComboBox<?>... comboBoxList){
        for(ComboBox<?> comboBox : comboBoxList){
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
    private void setShoppingType(){
        typeField.setItems(FXCollections.observableArrayList(productTypeDao.findAllAvailableTypes()));
    }

    //Display product name according to type
    private void setShoppingProduct(){
        nameField.itemsProperty().bind(Bindings.createObjectBinding(() ->{
            ProductType productType = typeField.getValue();
            if(productType == null){
                return null;
            }
            return FXCollections.observableArrayList(productDao.findByProductTypeID(productType.getProductTypeID()));
        },typeField.valueProperty()));
    }

    //Show shopping price after choosing product name
    @FXML
    public void showShoppingPrice(){
       Product selectedProduct = nameField.getValue();
        if(selectedProduct != null){
            unitPriceField.setText(CurrencyUtils.format(selectedProduct.getUnitPrice()));
        } else{
           unitPriceField.setText("");
        }
    }

    private void setShoppingQuantity(){
        quantityField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeArrowPointUpwards(typeField, nameField);
        setShoppingType();
        setShoppingProduct();
        setShoppingQuantity();
    }
}
