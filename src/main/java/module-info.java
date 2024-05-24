module org.bgdnstc.compiler_graphical_interface {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.bgdnstc to javafx.fxml;
    exports org.bgdnstc;
}