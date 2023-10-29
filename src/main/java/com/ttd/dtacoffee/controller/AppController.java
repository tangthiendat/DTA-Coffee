package com.ttd.dtacoffee.controller;

import com.ttd.dtacoffee.dao.ProductDao;
import com.ttd.dtacoffee.model.Order;
import com.ttd.dtacoffee.model.OrderDetail;
import com.ttd.dtacoffee.model.Product;
import com.ttd.dtacoffee.utility.CurrencyUtils;
import com.ttd.dtacoffee.utility.LanguageUtils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class AppController implements Initializable {

    //GLOBAL
    @FXML
    private Button nav_dashboardBtn;

    @FXML
    private Button nav_productBtn;

    @FXML
    private Button nav_shoppingBtn;

    @FXML
    private Button nav_orderBtn;

    @FXML
    private AnchorPane dashboardSection;

    @FXML
    private AnchorPane productSection;

    @FXML
    private AnchorPane shoppingSection;

    @FXML
    private AnchorPane orderSection;

    //DASH BOARD SECTION
    @FXML
    private Button weeklyChartType;

    @FXML
    private Button monthlyChartType;

    @FXML
    private LineChart<String, Number> weeklyChart;

    @FXML
    private LineChart<String, Number> monthlyChart;


    //PRODUCT SECTION
    @FXML
    private HBox product_searchContainer;

    @FXML
    private FontAwesomeIcon product_searchIcon;

    @FXML
    private TextField product_searchField;

    @FXML
    private TextField product_nameField;

    @FXML
    private ComboBox<String> product_statusField;

    @FXML
    private ComboBox<String> product_typeField;

    @FXML
    private TextField product_unitPriceField;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> product_nameCol;

    @FXML
    private TableColumn<Product, String> product_typeCol;

    @FXML
    private TableColumn<Product, String> product_unitPriceCol;

    @FXML
    private TableColumn<Product, String> product_statusCol;

    @FXML
    private TableColumn<Product, Void> product_actionCol;


    //SHOPPING SECTION
    @FXML
    private ComboBox<String> shopping_typeField;

    @FXML
    private ComboBox<String> shopping_nameField;

    @FXML
    private TextField shopping_unitPriceField;

    @FXML
    private Spinner<Integer> shopping_quantityField;

    @FXML
    private TableView<OrderDetail> shoppingTable;

    @FXML
    private TableColumn<OrderDetail, String>  shopping_nameCol;

    @FXML
    private TableColumn<OrderDetail, String>  shopping_quantityCol;

    @FXML
    private TableColumn<OrderDetail, String>  shopping_unitPriceCol;

    @FXML
    private TableColumn<OrderDetail, String>  shopping_totalCol;

    @FXML
    private TableColumn<OrderDetail, Void>  shopping_actionCol;

    @FXML
    private Label orderTotalValue;

    @FXML
    private TextField customerCashField;

    @FXML
    private Label change;

    //ORDER SECTION
    @FXML
    private HBox order_searchContainer;

    @FXML
    private FontAwesomeIcon order_searchIcon;

    @FXML
    private TextField order_searchField;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<?,?> order_NoCol;

    @FXML
    private TableColumn<?,?> order_numberCol;

    @FXML
    private TableColumn<?,?> order_createdDateCol;

    @FXML
    private TableColumn<?,?> order_totalValueCol;

    @FXML
    private TableColumn<?,?> order_showDetailsCol;

    //DAO
    private final ProductDao productDao = new ProductDao();

    //GLOBAL DATA
    private List<Product> productList;
    private final List<OrderDetail> orderDetailList = new ArrayList<>();
    private List<Order> orderList;
    private Long totalValue = 0L;

    /* GLOBAL CONTROLLER */
    @FXML
    public void switchSection(ActionEvent event){
        if(event.getSource().equals(nav_dashboardBtn)){
            dashboardSection.setVisible(true);
            hideOtherSections(productSection, shoppingSection, orderSection);
        } else if (event.getSource().equals(nav_productBtn)) {
            productSection.setVisible(true);
            hideOtherSections(dashboardSection, shoppingSection, orderSection);
        } else if (event.getSource().equals(nav_shoppingBtn)){
            shoppingSection.setVisible(true);
            hideOtherSections(dashboardSection, productSection, orderSection);
        } else if (event.getSource().equals(nav_orderBtn)) {
            orderSection.setVisible(true);
            hideOtherSections(dashboardSection, productSection, shoppingSection);
        }
    }

    public void hideOtherSections(AnchorPane... sectionList){
        for(AnchorPane section : sectionList){
            section.setVisible(false);
        }
    }

    //Set active effect for navbar buttons
    @FXML
    public void setNavbarActiveEffect(MouseEvent event){
        if(event.getSource().equals(nav_dashboardBtn)){
            nav_dashboardBtn.getStyleClass().add("active");
            removeActiveEffect(nav_productBtn, nav_shoppingBtn, nav_orderBtn);
        }
        if(event.getSource().equals(nav_productBtn)){
            nav_productBtn.getStyleClass().add("active");
            removeActiveEffect(nav_dashboardBtn, nav_shoppingBtn, nav_orderBtn);
        }
        if(event.getSource().equals(nav_shoppingBtn)){
            nav_shoppingBtn.getStyleClass().add("active");
            removeActiveEffect(nav_dashboardBtn, nav_productBtn, nav_orderBtn);
        }
        if(event.getSource().equals(nav_orderBtn)){
            nav_orderBtn.getStyleClass().add("active");
            removeActiveEffect(nav_dashboardBtn, nav_productBtn, nav_shoppingBtn);
        }

    }

    //Remove active effect for navbar buttons
    public void removeActiveEffect(Button... buttonList){
        for(Button button : buttonList){
            button.getStyleClass().removeAll("active");
        }
    }

    public void setDefaultNavBar(){
        nav_dashboardBtn.getStyleClass().add("active");
    }

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

    public Optional<ButtonType> showConfirmationAlert(String contextText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        Image errorImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/ic_confirm.png")));
        ImageView errorIcon = new ImageView(errorImg);
        errorIcon.setFitHeight(48);
        errorIcon.setFitWidth(48);
        alert.getDialogPane().setGraphic(errorIcon);
        alert.setContentText(contextText);
        return alert.showAndWait();
    }

    /* DASHBOARD SECTION CONTROLLER */
    //Set active effect for chart type buttons
    @FXML
    public void setChartTypeActiveEffect(MouseEvent event){
        if(event.getSource().equals(weeklyChartType)){
            weeklyChartType.getStyleClass().add("active-chart-type");
            monthlyChartType.getStyleClass().removeAll("active-chart-type");
        } else if (event.getSource().equals(monthlyChartType)) {
            weeklyChartType.getStyleClass().removeAll("active-chart-type");
            monthlyChartType.getStyleClass().add("active-chart-type");
        }
    }

    public void setDefaultChartType(){
        weeklyChartType.getStyleClass().add("active-chart-type");
    }

    public void switchChart(ActionEvent event){
        if(event.getSource() == weeklyChartType){
            weeklyChart.setVisible(true);
            monthlyChart.setVisible(false);
        } else if (event.getSource() == monthlyChartType) {
            weeklyChart.setVisible(false);
            monthlyChart.setVisible(true);
        }
    }

    public void setUpDashboardSection(){

        setDefaultChartType();
    }

    /* PRODUCT SECTION CONTROLLER */

    //Set focus status to search container when user click on the text field
    public void setFocusStatusForProductSearchBar(){
        product_searchField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue){
                product_searchContainer.setStyle("-fx-border-color: #039be5");
                product_searchIcon.setFill(Color.web("#039be5"));
            } else {
                product_searchContainer.setStyle("-fx-border-color: #bababa");
                product_searchIcon.setFill(Color.web("#bababa"));
            }
        }));
    }

    public void setTypeData(){
        String[] productTypeList = {"Common Drinks", "Fruits Tea", "Favorite Drinks", "Soda", "Herbal Tea", "Topping"};
        product_typeField.setItems(FXCollections.observableArrayList(productTypeList));
    }

    public void setStatusData(){
        String[] productStatusList = {"Available", "Unavailable"};
        product_statusField.setItems(FXCollections.observableArrayList(productStatusList));
    }

    //Clear selection when user double-click on selected row
    public void setClearSelectionOnDoubleClick(){
        productTable.setRowFactory(tableView ->{
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    productTable.getSelectionModel().clearSelection();
                }
            });
            return row;
        });
        shoppingTable.setRowFactory(tableView ->{
            TableRow<OrderDetail> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    shoppingTable.getSelectionModel().clearSelection();
                }
            });
            return row;
        });
        orderTable.setRowFactory(tableView ->{
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    orderTable.getSelectionModel().clearSelection();
                }
            });
            return row;
        });
    }

    //Show data to product
    public void showProductTable(){
        productList = productDao.findAll();
        product_nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        product_typeCol.setCellValueFactory(new PropertyValueFactory<>("productType"));
        //Display price with formatted currency
        product_unitPriceCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(CurrencyUtils.format(cellData.getValue().getUnitPrice())));

        product_statusCol.setCellValueFactory(new PropertyValueFactory<>("productStatus"));
        //Display edit and delete icon in action column
        product_actionCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Image editImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/ic_edit.png")));
                    Image deleteImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/ic_delete.png")));

                    ImageView editIcon = new ImageView(editImg);
                    editIcon.setFitWidth(25);
                    editIcon.setFitHeight(25);
                    editIcon.setPreserveRatio(true);
                    editIcon.setStyle("-fx-cursor: hand");
                    Tooltip.install(editIcon, new Tooltip("Chỉnh sửa"));

                    ImageView deleteIcon = new ImageView(deleteImg);
                    deleteIcon.setFitWidth(25);
                    deleteIcon.setFitHeight(25);
                    deleteIcon.setPreserveRatio(true);
                    deleteIcon.setStyle("-fx-cursor: hand");
                    Tooltip.install(deleteIcon, new Tooltip("Xóa"));

                    editIcon.setOnMouseClicked(event -> {
                        try {
                            //Load product editor form
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/productEditorView.fxml"));
                            Scene scene = new Scene(fxmlLoader.load());
                            ProductEditorController productEditorController = fxmlLoader.getController();
                            getTableView().getSelectionModel().select(getIndex());
                            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
                            productEditorController.showSelectedProduct(selectedProduct);
                            Stage stage = new Stage();
                            stage.initStyle(StageStyle.UTILITY);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setScene(scene);
                            stage.showAndWait();
                            Product updatedProduct = productEditorController.saveChange();
                            if (updatedProduct != null) {
                                productList.set(productList.indexOf(selectedProduct), updatedProduct);
                                productTable.getSelectionModel().clearSelection();
                                productTable.refresh();
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    });

                    deleteIcon.setOnMouseClicked(event -> {
                        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
                        Optional<ButtonType> option = showConfirmationAlert("Bạn có chắc chắn muốn XÓA sản phẩm này không?");
                        if (Objects.requireNonNull(option.orElse(null)).equals(ButtonType.OK)) {
                            productList.remove(selectedProduct);
                            productDao.delete(selectedProduct);
                            productTable.refresh();
                        }
                    });
                    //Add icon to be displayed in the column
                    HBox action = new HBox(editIcon, deleteIcon);
                    action.setStyle("-fx-alignment: center");
                    action.setPrefWidth(65);
                    action.setPrefHeight(15);
                    action.setSpacing(15);
                    setGraphic(action);
                }
            }

        });
        productTable.setItems(FXCollections.observableList(productList));
        showProductSearchResult();
    }

    public void showProductSearchResult(){
        FilteredList<Product> filteredData = new FilteredList<>(FXCollections.observableList(productList), e -> true);
        product_searchField.setOnKeyTyped(event -> {
            product_searchField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return LanguageUtils.normalize(product.getProductName()).toLowerCase().contains(newValue.toLowerCase());
            }));
            SortedList<Product> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(productTable.comparatorProperty());
            productTable.setItems(sortedData);
        });
    }


    @FXML
    public void addProduct(){
        String productName = product_nameField.getText();
        String productType = product_typeField.getSelectionModel().getSelectedItem();
        String unitPrice = product_unitPriceField.getText();
        String productStatus = product_statusField.getSelectionModel().getSelectedItem();

        if(productName.isEmpty() || productType == null || unitPrice.isEmpty() || productStatus == null){
            if(productName.isEmpty()){
                product_nameField.getStyleClass().add("error-field");
            }
            if(productType == null){
                product_typeField.getStyleClass().add("error-field");
            }
            if(unitPrice.isEmpty()){
                product_unitPriceField.getStyleClass().add("error-field");
            }
            if(productStatus == null){
                product_statusField.getStyleClass().add("error-field");
            }
            showErrorAlert("Hãy điền thông tin vào các ô còn trống.");
        } else if (!unitPrice.matches("\\d+")) {
            //Check if unit price does not contain numbers
            product_unitPriceField.getStyleClass().add("error-field");
            showErrorAlert("Đơn giá chỉ bao gồm các chữ số từ 0-9.");
        } else {
            Product newProduct = new Product(productName, productType, Integer.parseInt(unitPrice), productStatus);
            productList.add(newProduct);
            productDao.save(newProduct);
            showProductTable();
            clearAllProductInfo();
        }
    }

    //Clear all info that user entered
    public void clearAllProductInfo(){
        product_nameField.setText("");
        product_typeField.valueProperty().set(null);
        product_unitPriceField.setText("");
        product_statusField.valueProperty().set(null);
    }

    //Remove error effect when user click on the field
    @FXML
    public void removeErrorEffect(MouseEvent event){
        if (event.getSource().equals(product_nameField)) {
            product_nameField.getStyleClass().removeAll("error-field");
        }
        if (event.getSource().equals(product_typeField)) {
            product_typeField.getStyleClass().removeAll("error-field");
        }
        if (event.getSource().equals(product_unitPriceField)) {
            product_unitPriceField.getStyleClass().removeAll("error-field");
        }
        if (event.getSource().equals(product_statusField)) {
            product_statusField.getStyleClass().removeAll("error-field");
        }
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

    public void setUpProductSection(){
        //Style
        setFocusStatusForProductSearchBar();
        makeArrowPointUpwards(product_typeField, product_statusField);
        //Load data
        setTypeData();
        setStatusData();
        showProductTable();
    }

    /* SHOPPING SECTION CONTROLLER */
    //Show all available types
    public void setShoppingType(){
        shopping_typeField.setItems(FXCollections.observableArrayList(productDao.findAllAvailableTypes()));
    }

    //Display product name according to type
    public void setShoppingProduct(){
        shopping_nameField.itemsProperty().bind(Bindings.createObjectBinding(() ->{
            String type = shopping_typeField.getSelectionModel().getSelectedItem();
            if(type == null){
                return null;
            }
            return FXCollections.observableArrayList(productDao.findNameByType(type));
        },shopping_typeField.valueProperty()));
    }

    //Show shopping price after choosing product name
    public void showShoppingPrice(){
        String productName = shopping_nameField.getSelectionModel().getSelectedItem();
        if(productName != null){
            Product selectedProduct = productDao.findByProductName(productName);
            shopping_unitPriceField.setText(CurrencyUtils.format(selectedProduct.getUnitPrice()));
        } else{
            shopping_unitPriceField.setText("");
        }
    }

    public void setShoppingQuantity(){
        shopping_quantityField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
    }

    public void showShoppingTable(){
        shopping_nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getProductName()));
        shopping_quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        shopping_unitPriceCol.setCellValueFactory(cellData -> new SimpleStringProperty(CurrencyUtils.format(cellData.getValue().getProduct().getUnitPrice())));
        shopping_totalCol.setCellValueFactory(cellData -> new SimpleStringProperty(CurrencyUtils.format(cellData.getValue().getTotalValue())));
        shopping_actionCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Image editImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/ic_edit.png")));
                    Image deleteImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/ic_delete.png")));

                    ImageView editIcon = new ImageView(editImg);
                    editIcon.setFitWidth(25);
                    editIcon.setFitHeight(25);
                    editIcon.setPreserveRatio(true);
                    editIcon.setStyle("-fx-cursor: hand");
                    Tooltip.install(editIcon, new Tooltip("Chỉnh sửa"));

                    ImageView deleteIcon = new ImageView(deleteImg);
                    deleteIcon.setFitWidth(25);
                    deleteIcon.setFitHeight(25);
                    deleteIcon.setPreserveRatio(true);
                    deleteIcon.setStyle("-fx-cursor: hand");
                    Tooltip.install(deleteIcon, new Tooltip("Xóa"));

                    editIcon.setOnMouseClicked(event -> {
                        try {
                            //Load product editor form
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/orderDetailEditorView.fxml"));
                            Scene scene = new Scene(fxmlLoader.load());
                            OrderDetailEditorController orderDetailEditorController = fxmlLoader.getController();
                            getTableView().getSelectionModel().select(getIndex());
                            OrderDetail selectedOrderDetail = shoppingTable.getSelectionModel().getSelectedItem();
                            orderDetailEditorController.showSelectedOrderDetail(selectedOrderDetail);
                            Stage stage = new Stage();
                            stage.initStyle(StageStyle.UTILITY);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setScene(scene);
                            stage.showAndWait();
                           OrderDetail updatedOrderDetail = orderDetailEditorController.saveChange();
                            if (updatedOrderDetail != null) {
                                orderDetailList.set(orderDetailList.indexOf(selectedOrderDetail), updatedOrderDetail);
                                shoppingTable.getSelectionModel().clearSelection();
                                shoppingTable.refresh();
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    });

                    deleteIcon.setOnMouseClicked(event -> {
                        OrderDetail selectedOrderDetail = shoppingTable.getSelectionModel().getSelectedItem();
                        Optional<ButtonType> option = showConfirmationAlert("Bạn có chắc chắn muốn XÓA sản phẩm này không?");
                        if (Objects.requireNonNull(option.orElse(null)).equals(ButtonType.OK)) {
                            orderDetailList.remove(selectedOrderDetail);
                            shoppingTable.refresh();
                        }
                    });
                    //Add icon to be displayed in the column
                    HBox action = new HBox(editIcon, deleteIcon);
                    action.setStyle("-fx-alignment: center");
                    action.setPrefWidth(65);
                    action.setPrefHeight(15);
                    action.setSpacing(10);
                    setGraphic(action);
                }
            }

        });
        shoppingTable.setItems(FXCollections.observableList(orderDetailList));
    }

    public boolean checkExistOrderDetail(String productName){
        for(OrderDetail orderDetail : orderDetailList){
            if(orderDetail.getProduct().getProductName().equals(productName)){
                return true;
            }
        }
        return false;
    }

    public void clearAllOrderDetailInfo(){
        shopping_typeField.valueProperty().set(null);
        shopping_nameField.valueProperty().set(null);
        shopping_quantityField.getValueFactory().setValue(0);
    }

    public void addToCart(){
        String productName = shopping_nameField.getSelectionModel().getSelectedItem();
        Product selectedProduct = productDao.findByProductName(productName);
        int quantity = shopping_quantityField.getValue();
        if(quantity == 0){
            showErrorAlert("Số lượng phải lớn hơn 0.");
        } else {
            if(checkExistOrderDetail(productName)){
                showErrorAlert("Sản phẩm này đã tồn tại trong giỏ hàng. Vui lòng chọn sản phẩm khác.");
            } else {
                OrderDetail newOrderDetail = new OrderDetail(selectedProduct, quantity, selectedProduct.getUnitPrice());
                totalValue += newOrderDetail.getTotalValue();
                orderDetailList.add(newOrderDetail);
                showShoppingTable();
                //Show order total value
                orderTotalValue.setText(CurrencyUtils.format(totalValue) + "đ");
                clearAllOrderDetailInfo();
            }
        }
    }



    public void setUpShoppingSection(){
        //Style
        makeArrowPointUpwards(shopping_typeField, shopping_nameField);
        //Data
        setShoppingType();
        setShoppingProduct();
        setShoppingQuantity();
        showShoppingTable();
    }

    public void setFocusStatusForOrderSearchBar(){
        order_searchField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue){
                order_searchContainer.setStyle("-fx-border-color: #039be5");
                order_searchIcon.setFill(Color.web("#039be5"));
            } else {
                order_searchContainer.setStyle("-fx-border-color: #bababa");
                order_searchIcon.setFill(Color.web("#bababa"));
            }
        }));
    }

    public void setUpOrderSection(){
        setFocusStatusForOrderSearchBar();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDefaultNavBar();
        setClearSelectionOnDoubleClick();
        setUpDashboardSection();
        setUpProductSection();
        setUpShoppingSection();
        setUpOrderSection();
    }
}
