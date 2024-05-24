module org.bgdnstc.compiler_graphical_interface {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.bgdnstc.compiler_graphical_interface to javafx.fxml;
    exports org.bgdnstc.compiler_graphical_interface;
}