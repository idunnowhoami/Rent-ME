module projeto_final.dias_2023_g2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens Controllers to javafx.fxml, java.base;
    exports Controllers;
    opens Model to javafx.base;


}