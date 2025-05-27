module address.book.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires jbcrypt;
    requires java.net.http;
    requires org.json;


    opens QUT.CAB302.QuoteMe to javafx.fxml;
    exports QUT.CAB302.QuoteMe;
    exports QUT.CAB302.QuoteMe.controller;
    opens QUT.CAB302.QuoteMe.controller to javafx.fxml;
    exports QUT.CAB302.QuoteMe.model;
    opens QUT.CAB302.QuoteMe.model to javafx.fxml;


}