package granicni_prelaz.javaprojekat2023.simulation;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.exceptions.MapLoadingException;
import granicni_prelaz.javaprojekat2023.incident.ListOfPunishedPersons;
import granicni_prelaz.javaprojekat2023.json.PathJsonParser;
import granicni_prelaz.javaprojekat2023.map.Field;
import granicni_prelaz.javaprojekat2023.map.PathOnMap;
import granicni_prelaz.javaprojekat2023.terminals.CustomsTerminal;
import granicni_prelaz.javaprojekat2023.terminals.CustomsTerminalForTrucks;
import granicni_prelaz.javaprojekat2023.terminals.PoliceTerminal;
import granicni_prelaz.javaprojekat2023.terminals.PoliceTerminalForTrucks;
import granicni_prelaz.javaprojekat2023.util.SimulationLogger;
import granicni_prelaz.javaprojekat2023.vozila.Bus;
import granicni_prelaz.javaprojekat2023.vozila.Car;
import granicni_prelaz.javaprojekat2023.vozila.Truck;
import granicni_prelaz.javaprojekat2023.vozila.Vehicle;

import java.lang.invoke.VarHandle;
import java.util.*;
import java.util.logging.Level;

public class Simulation {

    public static final PathOnMap pathWithTerminals = initPath(Constants.PATH_ON_MAP);
    public static Queue<Vehicle> queueVehicles = new LinkedList<>();
    public static List<Vehicle> columnOfVehicles;
    public static List<PoliceTerminal> policeTerminals;
    public static List<CustomsTerminal> customsTerminals;
    public static ListOfPunishedPersons policeRecord = new ListOfPunishedPersons();
    public static List<String> customsRecord = new ArrayList<>();

    private static PathOnMap initPath(String pathName) {
        PathOnMap pathOnMap = null;
        try {
            pathOnMap = PathJsonParser.getPathFromJson(pathName);
        } catch (MapLoadingException exception) {
            SimulationLogger.log(Simulation.class, Level.SEVERE, exception.getMessage(), exception);
        }
        return pathOnMap;
    }

    public Simulation() {
        createVehicles(new Car());
        createVehicles(new Bus());
        createVehicles(new Truck());
        createTerminals();
        shuffleQueue();

        System.out.println("Prvo vozilo:" + queueVehicles.peek().getVehicleName());

    }

    private void createTerminals() {
        policeTerminals = new ArrayList<>();
        customsTerminals = new ArrayList<>();

        customsTerminals.add(new CustomsTerminal("C",0));
        customsTerminals.add(new CustomsTerminalForTrucks("CK",1));
        policeTerminals.add(new PoliceTerminal("P1",2));
        policeTerminals.add(new PoliceTerminal("P2",3));
        policeTerminals.add(new PoliceTerminalForTrucks("PK",4));


    }


    public void createVehicles(Vehicle vehicleType) {

        if (vehicleType instanceof Car)
            for (int i = 0; i < Constants.NUMBER_OF_CARS; i++)
                queueVehicles.add(new Car());


        if (vehicleType instanceof Bus)
            for (int i = 0; i < Constants.NUUMBER_OF_BUSES; i++)
                queueVehicles.add(new Bus());

        if (vehicleType instanceof Truck)
            for (int i = 0; i < Constants.NUMBER_OF_TRUCKS; i++)
                queueVehicles.add(new Truck());

    }

    void shuffleQueue()
    {
        columnOfVehicles = new java.util.ArrayList<>(queueVehicles);
        Collections.shuffle(columnOfVehicles);
        addVehiclesToPath(columnOfVehicles);
        queueVehicles.clear();
        queueVehicles.addAll(columnOfVehicles);

    }

    private void addVehiclesToPath(List<Vehicle> list) {

        List<Field> pathfields = pathWithTerminals.getPathFields();

        for(int i = 0; i<list.size();i++)
        {
            Vehicle vehicle = list.get(i);
            pathfields.get(i+Constants.START_INDEX).setVehicle(vehicle);
            vehicle.setPosition(i+Constants.START_INDEX);


        }

    }
}
