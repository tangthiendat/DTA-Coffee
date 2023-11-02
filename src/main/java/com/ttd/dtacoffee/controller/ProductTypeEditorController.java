package com.ttd.dtacoffee.controller;

import com.ttd.dtacoffee.dao.ProductTypeDao;
import com.ttd.dtacoffee.model.Order;
import com.ttd.dtacoffee.model.OrderDetail;
import com.ttd.dtacoffee.model.Product;
import com.ttd.dtacoffee.model.ProductType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProductTypeEditorController implements Initializable {


    @FXML
    private TableColumn<ProductType, String> ordinalCol;

    @FXML
    private TableColumn<ProductType, String> productTypeNameCol;

    @FXML
    private TextField productTypeNameField;

    @FXML
    private TableView<ProductType> productTypeTable;

    private final ProductTypeDao productTypeDao = new ProductTypeDao();
    private List<ProductType> productTypeList;

    public void showErrorAlert(String contextText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        Image errorImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/ic_error.png")));
        ImageView errorIcon = new ImageView(errorImg);
        errorIcon.setFitHeight(48);
        errorIcon.setFitWidth(48);
        alert.getDialogPane().setGraphic(errorIcon);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    public void setClearSelectionOnDoubleClick(){
        productTypeTable.setRowFactory(tableView ->{
            TableRow<ProductType> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    productTypeTable.getSelectionModel().clearSelection();
                }
            });
            return row;
        });
    }

    private String generateProductTypeID(){
        return "PT" + String.format("%02d", productTypeDao.countAll()+1);
    }

    private ProductType findExistType(ProductType newProductType){
        for(ProductType productType : productTypeList){
            if(newProductType.getProductTypeName().equals(productType.getProductTypeName())){
                return productType;
            }
        }
        return null;
    }

    @FXML
    public void addOrUpdateProductType(){
        String productTypeName = productTypeNameField.getText();
        if(productTypeName.isBlank()){
            productTypeNameField.getStyleClass().add("error-field");
            showErrorAlert("Tên loại sản phẩm không được để trống");
        } else {
            ProductType newProductType = new ProductType(generateProductTypeID(), productTypeName);
            productTypeList.add(newProductType);
            productTypeDao.save(newProductType);
            clearInfo();
            showProductTypeTable();
        }
    }


    @FXML
    public void clearInfo(){
        productTypeNameField.setText("");
    }


    private void showProductTypeTable(){
        productTypeList = productTypeDao.findAll();
        ordinalCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(productTypeList.indexOf(cellData.getValue())+1)));
        productTypeNameCol.setCellValueFactory(new PropertyValueFactory<>("productTypeName"));
        //Set editable column
        productTypeNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        productTypeNameCol.setOnEditCommit(event -> {
            //Get new product type name
            String newProductTypeName = event.getNewValue();
            ProductType row = event.getTableView().getItems().get(event.getTablePosition().getRow());
            row.setProductTypeName(newProductTypeName);
            productTypeDao.update(row);
            productTypeTable.refresh();
        });
        productTypeTable.setItems(FXCollections.observableList(productTypeList));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showProductTypeTable();
        setClearSelectionOnDoubleClick();
    }
}
