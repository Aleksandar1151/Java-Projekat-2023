package granicni_prelaz.javaprojekat2023.vozila;

import granicni_prelaz.javaprojekat2023.persons.Passenger;
import granicni_prelaz.javaprojekat2023.persons.Driver;

import java.util.List;

public abstract class Vehicle extends Thread{
    Driver driver;
    List<Passenger> passengers;
    String vehicleName;

    Vehicle(String vehicleName)
    {
        this.vehicleName = vehicleName;
    }

    public void run()
    {

    }

    public String getVehicleName() {
        return vehicleName;
    }
}
