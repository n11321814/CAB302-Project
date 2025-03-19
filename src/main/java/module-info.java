module address.book.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens address.book.addressbook to javafx.fxml;
    exports address.book.addressbook;
}