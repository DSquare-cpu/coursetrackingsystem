module com.example.coursetrackingsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.coursetrackingsystem to javafx.fxml;
    exports com.example.coursetrackingsystem;
}