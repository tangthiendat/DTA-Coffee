module dtacoffee {
    requires javafx.graphics;
    requires fontawesomefx;
    requires javafx.fxml;
    requires javafx.controls;
    requires jasperreports;
    requires java.sql;
    requires mysql.connector.j;

    opens com.ttd.dtacoffee.controller to javafx.fxml;
    opens com.ttd.dtacoffee.model to javafx.base;
    exports com.ttd.dtacoffee.model;
    exports com.ttd.dtacoffee;
}