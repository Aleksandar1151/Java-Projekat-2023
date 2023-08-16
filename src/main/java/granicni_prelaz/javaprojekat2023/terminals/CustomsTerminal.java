package granicni_prelaz.javaprojekat2023.terminals;

import granicni_prelaz.javaprojekat2023.persons.Passenger;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import granicni_prelaz.javaprojekat2023.vozila.Car;
import granicni_prelaz.javaprojekat2023.vozila.Truck;
import granicni_prelaz.javaprojekat2023.vozila.Vehicle;

public class CustomsTerminal extends Terminal{


    public CustomsTerminal(String terminalName , int position)
    {
        super(terminalName,  position);
    }
    @Override
    public void processVehicle() throws InterruptedException {

        if(vehicle instanceof Car) Thread.sleep(2000);
        else {
            for (Passenger passenger: vehicle.getPassengers() ) {
                if(passenger.getSuitcase().getHasIlligalThings())
                    ejectPassenger(passenger);
            }
        }

    }

    @Override
    public boolean acceptVehicle(Vehicle vehicleType) {
        return (vehicleType instanceof Truck) ? false : true;
    }

    @Override
    void ejectPassenger(Passenger passenger) {
        vehicle.getPassengers().remove(passenger);
        Simulation.customsRecord.add(passenger.toString());

    }

    @Override
    void ejectVehicle() {

    }


}
