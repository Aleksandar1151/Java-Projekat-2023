package granicni_prelaz.javaprojekat2023.controllers;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.exceptions.FileLoadingException;
import granicni_prelaz.javaprojekat2023.map.Field;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import granicni_prelaz.javaprojekat2023.terminals.CustomsTerminal;
import granicni_prelaz.javaprojekat2023.terminals.CustomsTerminalForTrucks;
import granicni_prelaz.javaprojekat2023.terminals.PoliceTerminalForTrucks;
import granicni_prelaz.javaprojekat2023.util.SimulationLogger;
import granicni_prelaz.javaprojekat2023.util.TerminalWatcher;
import granicni_prelaz.javaprojekat2023.util.TimeCounter;
import granicni_prelaz.javaprojekat2023.util.Utils;
import granicni_prelaz.javaprojekat2023.vozila.Bus;
import granicni_prelaz.javaprojekat2023.vozila.Car;
import granicni_prelaz.javaprojekat2023.vozila.Vehicle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

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

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
           // watcher.start();
            setSimulationController();
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
    }
}