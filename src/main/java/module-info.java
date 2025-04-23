module address.book.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens QUT.CAB203.fortunecookie to javafx.fxml;
    exports QUT.CAB203.fortunecookie;
    exports QUT.CAB203.fortunecookie.controller;
    opens QUT.CAB203.fortunecookie.controller to javafx.fxml;
    exports QUT.CAB203.fortunecookie.model;
    opens QUT.CAB203.fortunecookie.model to javafx.fxml;
}