package granicni_prelaz.javaprojekat2023.vozila;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.controllers.SimulationController;
import granicni_prelaz.javaprojekat2023.map.Field;
import granicni_prelaz.javaprojekat2023.persons.Passenger;
import granicni_prelaz.javaprojekat2023.persons.Driver;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import granicni_prelaz.javaprojekat2023.util.Utils;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public abstract class Vehicle extends Thread{
    Driver driver;
    List<Passenger> passengers = new ArrayList<>();
    String vehicleName;
    boolean kickedOut;

    int position;

    Vehicle(String vehicleName, int maxPassengers)
    {
        this.vehicleName = vehicleName;
        driver = new Driver(vehicleName);

        Random rnd = new Random();
        int numOfPassengers = rnd.nextInt(maxPassengers);
        for(int i = 0; i<numOfPassengers;i++)
        {
            passengers.add(new Passenger(vehicleName));
        }
    }
    Field field;
    public void run()
    {
        List<Field> pathfields;
        pathfields = Simulation.pathWithTerminals.getPathFields();


        System.out.println("SIZE: " + Simulation.columnOfVehicles.size());

        for(int i = 20; i>0;i--)
        {
            Simulation.columnOfVehicles.add(new Car());
        }

        for(int i = 10; i>0;i--)
        {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            final int positionToUpdate = i;

            Platform.runLater(() -> {
                Simulation.columnOfVehicles.set(positionToUpdate, this); // Update the list
                Field field = pathfields.get(positionToUpdate); // Get the field
                SimulationController.placeVehicleOnPosition(this, field.getYPosition(), field.getXPosition()); // Place the label
            });


        }


    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
