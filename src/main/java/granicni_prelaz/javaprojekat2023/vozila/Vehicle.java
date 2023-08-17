package granicni_prelaz.javaprojekat2023.vozila;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.controllers.ColumnOfVehiclesController;
import granicni_prelaz.javaprojekat2023.controllers.SimulationController;
import granicni_prelaz.javaprojekat2023.map.Field;
import granicni_prelaz.javaprojekat2023.persons.Passenger;
import granicni_prelaz.javaprojekat2023.persons.Driver;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import granicni_prelaz.javaprojekat2023.terminals.CustomsTerminal;
import granicni_prelaz.javaprojekat2023.terminals.PoliceTerminal;
import granicni_prelaz.javaprojekat2023.util.Utils;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class Vehicle extends Thread {
    Driver driver;
    List<Passenger> passengers = new ArrayList<>();
    String vehicleName;
    boolean isEjected;

    int position;
    List<Field> pathfields;
    Vehicle(String vehicleName, int maxPassengers) {
        this.vehicleName = vehicleName;
        driver = new Driver(vehicleName);

        Random rnd = new Random();
        int numOfPassengers = rnd.nextInt(maxPassengers);
        for (int i = 0; i < numOfPassengers; i++) {
            passengers.add(new Passenger(vehicleName));
        }


    }

    Field field;

    public void run() {
        pathfields = Simulation.pathWithTerminals.getPathFields();
        PoliceTerminal policeTerminal = Simulation.policeTerminals.get(0);
        CustomsTerminal customsTerminal = Simulation.customsTerminals.get(0);

        boolean finishedPoliceTerminal = false;
        boolean finishedCustomsTerminal = false;

        //KRETANJE U KOLONI
        while (true) {

            synchronized (Simulation.pathWithTerminals) {
                if (SimulationController.simulationPaused) {
                    try {
                        Simulation.pathWithTerminals.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (position < Constants.NUMBER_OF_ELEMENTS_AT_FIRST_MAP) {
                if (position == Constants.START_INDEX) break;


                if (!pathfields.get(position - 1).isHasVehicle()) {
                    Simulation.pathWithTerminals.setVehicleOnPosition(this, position - 1);
                    SimulationController.placeEmptyOnPosition(pathfields.get(position));

                    SimulationController.placeVehicleOnPosition(this, pathfields.get(position - 1));

                    position--;
                }


            } else {

                if (!pathfields.get(position - 1).isHasVehicle()) {
                    Simulation.pathWithTerminals.setVehicleOnPosition(this, position - 1);
                    ColumnOfVehiclesController.placeEmptyOnPosition(pathfields.get(position));

                    if (position == Constants.NUMBER_OF_ELEMENTS_AT_FIRST_MAP)
                        SimulationController.placeVehicleOnPosition(this, pathfields.get(position - 1));
                    else
                        ColumnOfVehiclesController.placeVehicleOnPosition(this, pathfields.get(position - 1));


                    position--;
                }

            }
            try {
                Thread.sleep(Constants.SPEED_OF_VEHICLES);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        pathfields.get(position).setVehicle(null);

        try {
            Thread.sleep(Constants.SPEED_OF_VEHICLES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //ULAZAK U POLICIJSKI TERMINAL
        while (!finishedPoliceTerminal) {

            if (this == Simulation.queueVehicles.peek()) {


                for (PoliceTerminal pt : Simulation.policeTerminals) {


                    if (!pt.hasVehicle() && pt.acceptVehicle(this) && pt.isInFunction()) {
                        Field field = pathfields.get(pt.getPosition());

                        synchronized (Simulation.pathWithTerminals) {
                            SimulationController.placeVehicleOnPosition(this, field);
                            SimulationController.placeEmptyOnPosition(pathfields.get(position));
                        }

                        position = pt.getPosition();
                        Simulation.queueVehicles.remove();

                        policeTerminal = pt;
                        //pt.setVehicle(this);
                        try {
                            pt.setVehicle(this);

                            System.out.println("Driver: " + driver);
                            //pt.setBusy(true);
                            pt.processVehicle();
                            //pt.setBusy(false);
                            // pt.setVehicle(null);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        finishedPoliceTerminal = true;
                        break;
                    }


                }


            }

        }


        try {
            if (isEjected)
            {
                Thread.currentThread().interrupt();
            }
            Thread.sleep(Constants.SPEED_OF_VEHICLES);
        } catch (InterruptedException e) {
            //throw new RuntimeException(e);
        }

        while (!finishedCustomsTerminal) {
            for (CustomsTerminal ct : Simulation.customsTerminals) {

                if (!ct.hasVehicle() && ct.acceptVehicle(this) && ct.isInFunction()) {
                    policeTerminal.setVehicle(null);

                    synchronized (Simulation.pathWithTerminals) {
                        SimulationController.placeVehicleOnPosition(this, pathfields.get(ct.getPosition()));
                        SimulationController.placeTerminalOnPosition(policeTerminal, pathfields.get(policeTerminal.getPosition()));
                    }
                    position = ct.getPosition();
                    customsTerminal = ct;


                    try {
                        ct.setVehicle(this);
                        ct.processVehicle();
                        ct.setVehicle(null);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    finishedCustomsTerminal = true;
                    break;

                } else {
                    //System.out.println(this.getVehicleName() + " čeka...");
                }


            }

        }

        try {
            Thread.sleep(Constants.SPEED_OF_VEHICLES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        SimulationController.placeTerminalOnPosition(customsTerminal, pathfields.get(customsTerminal.getPosition()));

        Simulation.numberOfVehiclesFinished++;

    }



    public String getVehicleName() {
        return vehicleName;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Driver getDriver() {
        return driver;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public boolean isEjected() {
        return isEjected;
    }

    public void setEjected(boolean ejected) {
        isEjected = ejected;
    }

    public int getPosition() {
        return position;
    }

    public String toString() {
        String string = vehicleName + "\n";
        string += "Vozac[" + getDriver() + "]\n";
        string += "Putnici:\n";
        for (Passenger passenger : passengers) {
            string += passenger + "\n";
        }

        return string;
    }
}
