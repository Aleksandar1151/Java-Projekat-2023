package granicni_prelaz.javaprojekat2023.terminals;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.controllers.SimulationController;
import granicni_prelaz.javaprojekat2023.gadgets.IdentificationDocument;
import granicni_prelaz.javaprojekat2023.map.Field;
import granicni_prelaz.javaprojekat2023.persons.Passenger;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import granicni_prelaz.javaprojekat2023.util.Utils;
import granicni_prelaz.javaprojekat2023.vozila.Bus;
import granicni_prelaz.javaprojekat2023.vozila.Truck;
import granicni_prelaz.javaprojekat2023.vozila.Vehicle;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PoliceTerminal extends Terminal {

    public PoliceTerminal(String terminalName, int position) {
        super(terminalName,  position);
    }




    @Override
    public void processVehicle() throws InterruptedException {

        Vehicle vehicle = this.vehicle;
        printInfo("Obrađuje vozilo: " + vehicle.getVehicleName());


        if (vehicle instanceof Bus)
            Thread.sleep(100);
        else
            Thread.sleep(500);

        IdentificationDocument identificationDocument = vehicle.getDriver().getIdentificationDocument();

        if (!identificationDocument.isValid())
            ejectVehicle();
        else {

            for (Passenger passenger : new ArrayList<>(vehicle.getPassengers()) ) {
                identificationDocument = passenger.getIdentificationDocument();
                if (!identificationDocument.isValid())
                    ejectPassenger(passenger);
            }


        }
        printInfo("Završena obrada vozila: " + vehicle.getVehicleName());

    }

    @Override
    public boolean acceptVehicle(Vehicle vehicleType) {
        return (vehicleType instanceof Truck) ? false : true;
    }

    @Override
    void ejectPassenger(Passenger passenger) {
        vehicle.getPassengers().remove(passenger);
        Simulation.policeRecord.addPerson(passenger);



    }

    @Override
    void ejectVehicle() {


        vehicle.setEjected(true);
        Simulation.policeRecord.addPerson(vehicle.getDriver());
        for (Passenger passenger: new ArrayList<>(vehicle.getPassengers()) ) {
            ejectPassenger(passenger);
        }
        setVehicle(null);
        if(Simulation.queueVehicles.peek() == vehicle)
            Simulation.queueVehicles.remove();
    }
    @Override
    public void printInfo(String text) {
        SimulationController.changeTextOfTerminal(this, text);
    }


    @Override
    public void setInFunction(boolean inFunction) {
        super.setInFunction(inFunction);
        printInfo((inFunction) ? "U funkciji" : "Nije u funkciji.");
    }
}
