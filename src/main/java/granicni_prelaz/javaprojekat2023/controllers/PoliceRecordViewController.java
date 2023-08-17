package granicni_prelaz.javaprojekat2023.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PoliceRecordViewController {

    @FXML
    private ListView<String> listView;
    @FXML
    private TextArea textArea;
    public static ListView<String> _listView;
    public static TextArea _textArea;

    public void initialize()
    {
        _listView = listView;
        _textArea = textArea;
        File folder = new File("Records" + File.separator + "PoliceRecords");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                _listView.getItems().add(file.getName());
            }
        }
        _listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(_listView.getSelectionModel().getSelectedItem());




                _textArea.setEditable(false);

                Path filePath = Paths.get("Records" + File.separator + "PoliceRecords"+ File.separator  + _listView.getSelectionModel().getSelectedItem());
                Charset charset = StandardCharsets.UTF_8;

                try (BufferedReader bufferedReader = Files.newBufferedReader(filePath, charset)) {
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        _textArea.appendText(line + "\n");
                        System.out.println(line);
                    }
                } catch (IOException ex) {
                    //LoggerClass.log(this.getClass(), Level.SEVERE, ex.getMessage(), ex);
                }




            }
        });
    }

}
