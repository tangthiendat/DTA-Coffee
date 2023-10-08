package com.ttd.dtacoffee.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private HBox searchContainer;

    @FXML
    private FontAwesomeIcon searchIcon;

    @FXML
    private TextField searchField;

    @FXML
    private Button product_addBtn;

    @FXML
    private Button product_clearBtn;

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
            nav_productBtn.getStyleClass().removeAll("active");
            nav_shoppingBtn.getStyleClass().removeAll("active");
        });

        nav_productBtn.setOnMouseClicked(event -> {
            nav_dashboardBtn.getStyleClass().removeAll("active");
            nav_productBtn.getStyleClass().add("active");
            nav_shoppingBtn.getStyleClass().removeAll("active");
        });

        nav_shoppingBtn.setOnMouseClicked(event -> {
            nav_dashboardBtn.getStyleClass().removeAll("active");
            nav_productBtn.getStyleClass().removeAll("active");
            nav_shoppingBtn.getStyleClass().add("active");
        });
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

    //Set focus status to container when user click on the textfield
    public void setFocusStatusForSearchBar(){
        searchField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue){
                searchContainer.setStyle("-fx-border-color: #039be5");
                searchIcon.setFill(Color.web("#039be5"));
            } else {
                searchContainer.setStyle("-fx-border-color: #bababa");
                searchIcon.setFill(Color.web("#bababa"));
            }
        }));
    }

    public void setUpProductSection(){
        setFocusStatusForSearchBar();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpDashboardSection();
        setUpProductSection();
    }
}
