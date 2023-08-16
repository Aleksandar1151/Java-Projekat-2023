package granicni_prelaz.javaprojekat2023.controllers;

import granicni_prelaz.javaprojekat2023.HelloApplication;
import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.exceptions.FileLoadingException;
import granicni_prelaz.javaprojekat2023.map.Field;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import granicni_prelaz.javaprojekat2023.terminals.*;
import granicni_prelaz.javaprojekat2023.util.SimulationLogger;
import granicni_prelaz.javaprojekat2023.util.TerminalWatcher;
import granicni_prelaz.javaprojekat2023.util.TimeCounter;
import granicni_prelaz.javaprojekat2023.util.Utils;
import granicni_prelaz.javaprojekat2023.vozila.Bus;
import granicni_prelaz.javaprojekat2023.vozila.Car;
import granicni_prelaz.javaprojekat2023.vozila.Truck;
import granicni_prelaz.javaprojekat2023.vozila.Vehicle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;

public class SimulationController  implements Initializable {
    private Simulation simulation;
    public static ColumnOfVehiclesController columnOfVehiclesController;
    private final TerminalWatcher watcher = new TerminalWatcher();

    public static boolean simulationStarted;
    public static boolean simulationPaused;
    public static boolean simulationFinished;

    public static TimeCounter timeCounter;
    @FXML
    private Label welcomeText;
    @FXML
    private ListView<Label> lvVehicles;
    @FXML
    private Label lblTerminalDescription;

    @FXML
    private GridPane gpColumnOfVehiclesWithTerminals;
    @FXML
    private Label lblTime;


    public static GridPane _gpColumnOfVehiclesWithTerminals;

    Stage stage = new Stage();

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        _gpColumnOfVehiclesWithTerminals = gpColumnOfVehiclesWithTerminals;

        try {
           // watcher.start();
            setSimulationController();
            LoadColumnOfVehiclesView();
        } catch (FileLoadingException exception) {
            SimulationLogger.log(this.getClass(), Level.SEVERE, exception.getMessage(), exception);
            System.exit(1);
        }
    }

    private void setSimulationController() throws FileLoadingException {
        simulationStarted = false;
        simulationFinished = false;
        simulationPaused = false;

        simulation = new Simulation();
        lblTime.setText("0s");
        //lblTerminalDescription.setText("");
        initPath();
        //setListViewOfVehicles();
    }
    public void initPath() {
        List<Field> pathfields;
        pathfields = Simulation.pathWithTerminals.getPathFields();



        Utils.setWhiteLabelsIntoMapWithTerminals(pathfields, gpColumnOfVehiclesWithTerminals);
        Field field;
        for (int i = 0; i < 10; i++) {
            field = pathfields.get(i);

            Label fieldLabel = Utils.createLabel();






            if (field.isHasVehicle()) {
                if (field.getVehicle() instanceof Bus) {
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getVehicle().getVehicleName(), Constants.COLOR_BUS);
                } else if (field.getVehicle() instanceof Car)
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getVehicle().getVehicleName(), Constants.COLOR_CAR);
                else {
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getVehicle().getVehicleName(), Constants.COLOR_TRUCK);
                }
            }
             else {
                Utils.setLableBackgroundAndBorderColor(fieldLabel, "", Constants.GRAY);

            }

             // POSTAVLJANJE TERMINALA NA MAPU
             switch (i)
             {
                 case 0:
                 case 1:
                     Utils.setLableBackgroundAndBorderColor(fieldLabel, Simulation.customsTerminals.get(i).getTerminalName(), Constants.COLOR_CUSTOMS_TERMINAL);
                     break;
                 case 2:
                     Utils.setLableBackgroundAndBorderColor(fieldLabel, Simulation.policeTerminals.get(0).getTerminalName(), Constants.COLOR_POLICE_TERMINAL);
                     break;
                 case 3:
                     Utils.setLableBackgroundAndBorderColor(fieldLabel, Simulation.policeTerminals.get(1).getTerminalName(), Constants.COLOR_POLICE_TERMINAL);
                     break;
                 case 4:
                     Utils.setLableBackgroundAndBorderColor(fieldLabel, Simulation.policeTerminals.get(2).getTerminalName(), Constants.COLOR_POLICE_TERMINAL);
                     break;
             }







            gpColumnOfVehiclesWithTerminals.add(fieldLabel, field.getYPosition(), field.getXPosition());
        }

    }

    private void setListViewOfVehicles() {
        lvVehicles.getItems().clear();
        for (int i = 0; i < Constants.NUMBER_OF_VEHICLES; i++) {
            Label labelVehicle = new Label("lblVehicle" + i + 1);
            Vehicle vehicle = Simulation.columnOfVehicles.get(i);
            Utils.setLabelListView(labelVehicle, vehicle.getVehicleName(), vehicle instanceof Car ? Constants.RED : (vehicle instanceof Bus ? Constants.GREEN : Constants.BLUE));
            lvVehicles.getItems().add(labelVehicle);
        }

    }


    public void dugmePrikazListeClick(ActionEvent actionEvent) {
    }

    public void startButtonClicked(ActionEvent actionEvent) {

            for(int i = 0; i<Simulation.columnOfVehicles.size();i++)
            {
                Simulation.columnOfVehicles.get(i).start();
            }

    }
    public static void placeEmptyOnPosition(Field field) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label fieldLabel = Utils.createLabel();
                fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.GRAY), null, null)));
                _gpColumnOfVehiclesWithTerminals.add(fieldLabel, field.getYPosition(), field.getXPosition());
            }
        });
    }

    public static void placeVehicleOnPosition(Vehicle vehicle , Field field)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label fieldLabel = Utils.createLabel();
                fieldLabel.setText(vehicle.getVehicleName());
                fieldLabel.setTextFill(Paint.valueOf("white"));

                if(vehicle instanceof Car)
                    fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.COLOR_CAR), null, null)));
                if(vehicle instanceof Bus)
                    fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.COLOR_BUS), null, null)));
                if(vehicle instanceof Truck)
                    fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.COLOR_TRUCK), null, null)));



                _gpColumnOfVehiclesWithTerminals.add(fieldLabel, field.getYPosition(), field.getXPosition());
            }
        });

    }

    public static void placeTerminalOnPosition(Terminal terminal , Field field)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label fieldLabel = Utils.createLabel();
                fieldLabel.setText(terminal.getTerminalName());
                fieldLabel.setTextFill(Paint.valueOf("white"));

                if(terminal instanceof PoliceTerminal)
                    fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.COLOR_POLICE_TERMINAL), null, null)));
               else
                    fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.COLOR_CUSTOMS_TERMINAL), null, null)));



                _gpColumnOfVehiclesWithTerminals.add(fieldLabel, field.getYPosition(), field.getXPosition());
            }
        });

    }



    private void LoadColumnOfVehiclesView()
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("columnOfVehiclesView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root1, 360, 430));
        } catch (IOException exception) {
            SimulationLogger.logAsync(getClass(), exception);
        }
    }
    @FXML
    void onBtnColumnOfVehiclesClicked(ActionEvent event) {
        stage.show();
    }


}