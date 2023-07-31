module granicni_prelaz.javaprojekat2023 {
    requires javafx.controls;
    requires javafx.fxml;








    opens granicni_prelaz.javaprojekat2023 to javafx.fxml;
    exports granicni_prelaz.javaprojekat2023;
    exports granicni_prelaz.javaprojekat2023.Kontroleri;
    opens granicni_prelaz.javaprojekat2023.Kontroleri to javafx.fxml;
}