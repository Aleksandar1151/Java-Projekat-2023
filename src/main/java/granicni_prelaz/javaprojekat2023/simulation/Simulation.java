package granicni_prelaz.javaprojekat2023.simulation;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.exceptions.MapLoadingException;
import granicni_prelaz.javaprojekat2023.json.PathJsonParser;
import granicni_prelaz.javaprojekat2023.map.PathOnMap;
import granicni_prelaz.javaprojekat2023.util.SimulationLogger;
import granicni_prelaz.javaprojekat2023.vozila.Vehicle;

import java.lang.invoke.VarHandle;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;

public class Simulation {

    public static final PathOnMap pathWithTerminals = initPath(Constants.PATH_ON_MAP);
    public static List<Vehicle> columnOfVehicles;
    Queue<Vehicle> redVozila;
    //List<Polje> listaPolja;

    private static PathOnMap initPath(String pathName) {
        PathOnMap pathOnMap = null;
        try {
            pathOnMap = PathJsonParser.getPathFromJson(pathName);
        } catch (MapLoadingException exception) {
            SimulationLogger.log(Simulation.class, Level.SEVERE, exception.getMessage(), exception);
        }
        return pathOnMap;
    }
}
