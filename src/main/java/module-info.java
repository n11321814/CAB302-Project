module address.book.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens QUT.CAB203.fortunecookie to javafx.fxml;
    exports QUT.CAB203.fortunecookie;
    exports QUT.CAB203.fortunecookie.LegacyFiles;
    opens QUT.CAB203.fortunecookie.LegacyFiles to javafx.fxml;
}