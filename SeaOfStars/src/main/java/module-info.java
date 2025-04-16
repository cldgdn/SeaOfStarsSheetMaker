module com.github.cldgdn.seaofstars {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.cldgdn.seaofstars to javafx.fxml;
    exports com.github.cldgdn.seaofstars;
}