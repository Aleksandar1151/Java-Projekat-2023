package granicni_prelaz.javaprojekat2023.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class IncidentsWatcherViewController implements Initializable {

    @FXML
    private TextArea _cTextArea;

    @FXML
    private TextArea _pTextArea;

    public static TextArea cTextArea;
    public static TextArea pTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        cTextArea = _cTextArea;
        pTextArea = _pTextArea;

    }



}
