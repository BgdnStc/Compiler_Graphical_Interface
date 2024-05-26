module org.bgdnstc.compiler_graphical_interface {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.bgdnstc to javafx.fxml;
    exports org.bgdnstc;
}