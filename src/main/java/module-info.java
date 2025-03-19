module address.book.addressbook {
    requires javafx.controls;
    requires javafx.fxml;


    opens address.book.addressbook to javafx.fxml;
    exports address.book.addressbook;
}