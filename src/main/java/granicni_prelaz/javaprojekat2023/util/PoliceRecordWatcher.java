package granicni_prelaz.javaprojekat2023.util;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.controllers.IncidentsWatcherViewController;
import granicni_prelaz.javaprojekat2023.controllers.PoliceRecordViewController;
import granicni_prelaz.javaprojekat2023.incident.IncidentUtil;
import granicni_prelaz.javaprojekat2023.incident.ListOfPunishedPersons;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import javafx.application.Platform;

import java.io.IOException;
import java.nio.file.*;
import java.util.logging.Level;

import static granicni_prelaz.javaprojekat2023.incident.IncidentUtil.policeFileName;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class PoliceRecordWatcher extends Thread{

    private static final Path directory = Paths.get(Constants.LIST_OF_PUNISHED_PERSONS_DIRECTORY);

    public PoliceRecordWatcher()
    {
        Platform.runLater(() -> IncidentsWatcherViewController.pTextArea.setText(""));
    }

    @Override
    public void run(){

        IncidentUtil.writePoliceIncidentIntoFile();
        try
        {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            directory.register(watcher, ENTRY_MODIFY,ENTRY_CREATE);
            WatchKey key;
            while (true){
                try {
                    key = watcher.take();
                } catch (InterruptedException ex) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()){
                    WatchEvent.Kind<?> kind = event.kind();
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();

                    //String desiredFileName = Constants.LIST_OF_PUNISHED_PERSONS_DIRECTORY + policeFileName;
                    String desiredFileName = policeFileName;



                    if (fileName.toString().equals(desiredFileName) && (kind.equals(ENTRY_MODIFY) || kind.equals(ENTRY_CREATE)))
                    {
                        ListOfPunishedPersons punishedPersons = IncidentUtil.readListOfPunishedPersonsFromFile(policeFileName);
                        if (punishedPersons != null)
                        {
                            Platform.runLater(() -> IncidentsWatcherViewController.pTextArea.setText(punishedPersons.toString()));

                        }
                    }

                    /*
                    ListOfPunishedPersons punishedPersons = IncidentUtil.readListOfPunishedPersonsFromFile(policeFileName);
                    if (punishedPersons != null)
                    {
                        Platform.runLater(() -> IncidentsWatcherViewController.pTextArea.setText(punishedPersons.toString()));

                    }*/
                }
                key.reset();
            }

        } catch (IOException exception) {
            SimulationLogger.log(TerminalWatcher.class, Level.SEVERE, exception.getMessage(), exception);
        }
    }
}
