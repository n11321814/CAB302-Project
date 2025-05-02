module address.book.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens QUT.CAB302.fortunecookie to javafx.fxml;
    exports QUT.CAB302.fortunecookie;
    exports QUT.CAB302.fortunecookie.LegacyFiles;
    opens QUT.CAB302.fortunecookie.LegacyFiles to javafx.fxml;
}