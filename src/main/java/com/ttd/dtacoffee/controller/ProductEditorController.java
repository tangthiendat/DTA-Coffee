package com.ttd.dtacoffee.controller;

import com.ttd.dtacoffee.dao.ProductDao;
import com.ttd.dtacoffee.model.Product;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductEditorController implements Initializable {

    @FXML
    private TextField productEditor_nameField;

    @FXML
    private ComboBox<String> productEditor_typeField;

    @FXML
    private TextField productEditor_unitPriceField;

    @FXML
    private ComboBox<String> productEditor_statusField;

    @FXML
    private Button productEditor_saveBtn;

    private final ProductDao productDao = new ProductDao();

    private Product updatedProduct;

    public void showSelectedProduct(Product selectedProduct){
        productEditor_nameField.setText(selectedProduct.getProductName());
        productEditor_typeField.getSelectionModel().select(selectedProduct.getProductType());
        productEditor_unitPriceField.setText(String.valueOf(selectedProduct.getUnitPrice()));
        productEditor_statusField.getSelectionModel().select(selectedProduct.getProductStatus());
        updatedProduct = new Product(selectedProduct.getProductID(), selectedProduct.getProductName(),
                selectedProduct.getProductType(), selectedProduct.getUnitPrice(), selectedProduct.getProductStatus());
    }

    public Product saveChange(){
        updatedProduct.setProductName(productEditor_nameField.getText());
        updatedProduct.setProductType(productEditor_typeField.getSelectionModel().getSelectedItem());
        updatedProduct.setUnitPrice(Integer.parseInt(productEditor_unitPriceField.getText()));
        updatedProduct.setProductStatus(productEditor_statusField.getSelectionModel().getSelectedItem());
        //Update product in database and return the updated value to app controller
        productDao.update(updatedProduct);
        //Close the window
        Stage stage = (Stage) productEditor_saveBtn.getScene().getWindow();
        stage.close();

        return updatedProduct;
    }

    //Make the arrow point upwards when user click on combobox to show dropdown list
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

    public void setTypeData(){
        String[] productTypeList = {"Common Drinks", "Fruits Tea", "Favorite Drinks", "Soda", "Herbal Tea", "Topping"};
        productEditor_typeField.setItems(FXCollections.observableArrayList(productTypeList));
    }

    public void setStatusData(){
        String[] productStatusList = {"Available", "Unavailable"};
        productEditor_statusField.setItems(FXCollections.observableArrayList(productStatusList));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeArrowPointUpwards(productEditor_typeField, productEditor_statusField);
        setTypeData();
        setStatusData();
    }
}
