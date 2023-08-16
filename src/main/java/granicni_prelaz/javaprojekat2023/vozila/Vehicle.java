package granicni_prelaz.javaprojekat2023.vozila;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.controllers.ColumnOfVehiclesController;
import granicni_prelaz.javaprojekat2023.controllers.SimulationController;
import granicni_prelaz.javaprojekat2023.map.Field;
import granicni_prelaz.javaprojekat2023.persons.Passenger;
import granicni_prelaz.javaprojekat2023.persons.Driver;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import granicni_prelaz.javaprojekat2023.terminals.PoliceTerminal;
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
    boolean isEjected;

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
        List<Field> pathfields = Simulation.pathWithTerminals.getPathFields();
        PoliceTerminal policeTerminal;

        boolean finishedPoliceTerminal = false;


        while(true)
        {
            if(position < Constants.NUMBER_OF_ELEMENTS_AT_FIRST_MAP)
            {
                if(position == Constants.START_INDEX) break;


                if(!pathfields.get(position-1).isHasVehicle())
                {
                    Simulation.pathWithTerminals.setVehicleOnPosition(this,position-1);
                    SimulationController.placeEmptyOnPosition(pathfields.get(position));

                    SimulationController.placeVehicleOnPosition(this,pathfields.get(position-1));

                    position--;
                }


            }else {

                if(!pathfields.get(position-1).isHasVehicle())
                {
                    Simulation.pathWithTerminals.setVehicleOnPosition(this,position-1);
                    ColumnOfVehiclesController.placeEmptyOnPosition(pathfields.get(position));

                    if(position == Constants.NUMBER_OF_ELEMENTS_AT_FIRST_MAP)
                        SimulationController.placeVehicleOnPosition(this,pathfields.get(position-1));
                    else
                        ColumnOfVehiclesController.placeVehicleOnPosition(this,pathfields.get(position-1));



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


        while(!finishedPoliceTerminal)
        {
            System.out.println("1");
            if(this == Simulation.queueVehicles.peek())
            {

                System.out.println("2");
                for (PoliceTerminal pt: Simulation.policeTerminals ) {
                    System.out.println("3");

                    if(!pt.isBusy() && pt.acceptVehicle(this) && pt.isInFunction())
                    {
                        Simulation.queueVehicles.remove();
                        System.out.println("4");
                        policeTerminal = pt;
                        pt.setVehicle(this);
                        try {
                            pt.setBusy(true);
                            pt.processVehicle();
                            pt.setBusy(false);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        finishedPoliceTerminal = true;
                        break;
                    }


                }


            }

        }

        System.out.println(getVehicleName() + " je prosao granicu.");







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
}
