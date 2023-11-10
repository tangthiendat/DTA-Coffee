package com.ttd.dtacoffee.controller;

import com.ttd.dtacoffee.dao.ProductDao;
import com.ttd.dtacoffee.dao.ProductTypeDao;
import com.ttd.dtacoffee.model.Product;
import com.ttd.dtacoffee.model.ProductType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductEditorController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<ProductType> typeField;

    @FXML
    private TextField unitPriceField;

    @FXML
    private ComboBox<String> statusField;

    @FXML
    private Button saveBtn;

    private final ProductDao productDao = new ProductDao();
    private final ProductTypeDao productTypeDao = new ProductTypeDao();

    private Product updatedProduct;

    public void showSelectedProduct(Product selectedProduct){
        nameField.setText(selectedProduct.getProductName());
        typeField.getSelectionModel().select(selectedProduct.getProductType());
        unitPriceField.setText(String.valueOf(selectedProduct.getUnitPrice()));
        statusField.getSelectionModel().select(selectedProduct.getProductStatus());
        updatedProduct = new Product(selectedProduct.getProductID(), selectedProduct.getProductName(),
                selectedProduct.getProductType(), selectedProduct.getUnitPrice(), selectedProduct.getProductStatus());
    }

    @FXML
    public void saveChange(){
        updatedProduct.setProductName(nameField.getText());
        updatedProduct.setProductType(typeField.getValue());
        updatedProduct.setUnitPrice(Integer.parseInt(unitPriceField.getText()));
        updatedProduct.setProductStatus(statusField.getValue());
        //Update product in database and return the updated value to app controller
        productDao.update(updatedProduct);
        //Close the window
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }

    public Product getUpdatedProduct(){
        return updatedProduct;
    }

    //Make the arrow point upwards when user click on combobox to show dropdown list
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

    private void setTypeData(){
        typeField.setItems(FXCollections.observableList(productTypeDao.findAll()));
    }

    private void setStatusData(){
        String[] productStatusList = {"Available", "Unavailable"};
        statusField.setItems(FXCollections.observableArrayList(productStatusList));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeArrowPointUpwards(typeField, statusField);
        setTypeData();
        setStatusData();
    }
}
