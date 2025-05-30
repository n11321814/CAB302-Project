module address.book.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jbcrypt;
    requires java.net.http;
    requires org.json;
    requires org.xerial.sqlitejdbc;


    opens QUT.CAB302.fortunecookie to javafx.fxml;
    exports QUT.CAB302.fortunecookie;
    exports QUT.CAB302.fortunecookie.Controller;
    opens QUT.CAB302.fortunecookie.Controller to javafx.fxml;
    exports QUT.CAB302.fortunecookie.Model;
    opens QUT.CAB302.fortunecookie.Model to javafx.fxml;


}