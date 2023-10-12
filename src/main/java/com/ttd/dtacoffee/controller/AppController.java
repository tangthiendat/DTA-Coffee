package com.ttd.dtacoffee.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


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
    private TableView<?> productTable;

    @FXML
    private TableColumn<?, ?> product_nameCol;

    @FXML
    private TableColumn<?, ?> product_typeCol;

    @FXML
    private TableColumn<?, ?> product_unitPriceCol;

    @FXML
    private TableColumn<?, ?> product_statusCol;

    @FXML
    private TableColumn<?, ?> product_deleteCol;


    //SHOPPING SECTION



    //GLOBAL
    public void switchSection(ActionEvent event){
        if(event.getSource() == nav_dashboardBtn){
            dashboardSection.setVisible(true);
            productSection.setVisible(false);
            shoppingSection.setVisible(false);
        } else if (event.getSource() == nav_productBtn) {
            dashboardSection.setVisible(false);
            productSection.setVisible(true);
            shoppingSection.setVisible(false);
        } else if (event.getSource() == nav_shoppingBtn){
            dashboardSection.setVisible(false);
            productSection.setVisible(false);
            shoppingSection.setVisible(true);
        }
    }

    //Set active effect for navbar buttons
    public void setNavbarActiveEffect(){
        nav_dashboardBtn.setOnMouseClicked(event -> {
            nav_dashboardBtn.getStyleClass().add("active");
            removeActiveEffect(nav_productBtn, nav_shoppingBtn, nav_orderBtn);
        });

        nav_productBtn.setOnMouseClicked(event -> {
            nav_productBtn.getStyleClass().add("active");
            removeActiveEffect(nav_dashboardBtn, nav_shoppingBtn, nav_orderBtn);
        });

        nav_shoppingBtn.setOnMouseClicked(event -> {
            nav_shoppingBtn.getStyleClass().add("active");
            removeActiveEffect(nav_dashboardBtn, nav_productBtn, nav_orderBtn);
        });

        nav_orderBtn.setOnMouseClicked(event -> {
            nav_orderBtn.getStyleClass().add("active");
            removeActiveEffect(nav_dashboardBtn, nav_productBtn, nav_shoppingBtn);
        });
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

    /* DASHBOARD SECTION CONTROLLER */
    //Set active effect for chart type buttons
    public void setChartTypeActiveEffect(){
        weeklyChartType.setOnMouseClicked(event -> {
            weeklyChartType.getStyleClass().add("active-chart-type");
            monthlyChartType.getStyleClass().removeAll("active-chart-type");
        });
        monthlyChartType.setOnMouseClicked(event -> {
            weeklyChartType.getStyleClass().removeAll("active-chart-type");
            monthlyChartType.getStyleClass().add("active-chart-type");
        });

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
        setNavbarActiveEffect();
        setDefaultNavBar();
        setChartTypeActiveEffect();
        setDefaultChartType();
    }

    /* PRODUCT SECTION CONTROLLER */

    //Set focus status to search container when user click on the text field
    public void setFocusStatusForSearchBar(){
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
        setFocusStatusForSearchBar();
        makeArrowPointUpwards(product_typeField, product_statusField);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpDashboardSection();
        setUpProductSection();
    }
}
