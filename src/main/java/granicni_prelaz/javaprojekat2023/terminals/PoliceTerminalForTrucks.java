package granicni_prelaz.javaprojekat2023.terminals;

import granicni_prelaz.javaprojekat2023.vozila.Truck;
import granicni_prelaz.javaprojekat2023.vozila.Vehicle;

public class PoliceTerminalForTrucks extends PoliceTerminal {

    public PoliceTerminalForTrucks(String terminalName, int position)
    {
        super(terminalName,  position);
    }

    @Override
    public boolean acceptVehicle(Vehicle vehicleType) {
        return (vehicleType instanceof Truck) ? true : false;
    }
}
