package granicni_prelaz.javaprojekat2023.incident;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.exceptions.FileLoadingException;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import granicni_prelaz.javaprojekat2023.util.SimulationLogger;
import granicni_prelaz.javaprojekat2023.util.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

public abstract class IncidentUtil {

    public static String policeFileName, customsFileName;
    public static void writeListOfPunishedPersonsIntoFile(ListOfPunishedPersons listOfPunishedPersons) {

        Utils.createFolderIfNotExists(Constants.LIST_OF_PUNISHED_PERSONS_DIRECTORY);

        //SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        //String fileName = "KaznjeneOsobe" + sdf.format(new Date());

        try (var objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(Constants.LIST_OF_PUNISHED_PERSONS_DIRECTORY + policeFileName))) {
            objectOutputStream.writeObject(listOfPunishedPersons);
        } catch (IOException fileNotFoundException) {
            SimulationLogger.log(IncidentUtil.class, Level.SEVERE, fileNotFoundException.getMessage(), fileNotFoundException);
        }
    }

    public static void writeCustomsIncident() {

        Utils.createFolderIfNotExists(Constants.LIST_OF_PUNISHED_PERSONS_DIRECTORY);

        //SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        //String fileName = "KaznjenaVozila" + sdf.format(new Date());

        try {
            PrintWriter out = new PrintWriter(Constants.LIST_OF_PUNISHED_VEHICLES_DIRECTORY + customsFileName);
            for(String incidentInfo: Simulation.customsRecord) {
                out.println(incidentInfo);
            }
            out.close();
        }
        catch (IOException fileNotFoundException) {
            SimulationLogger.log(IncidentUtil.class, Level.SEVERE, fileNotFoundException.getMessage(), fileNotFoundException);
        }
    }


    public static ListOfPunishedPersons readListOfPunishedPersonsFromFile(String fileName) {

        if(fileName == null) {
            return null;
        }
        ListOfPunishedPersons list = new ListOfPunishedPersons();

        try (var objectInputStream = new ObjectInputStream(new FileInputStream(Constants.LIST_OF_PUNISHED_PERSONS_DIRECTORY + fileName))) {
            list = (ListOfPunishedPersons) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException exception) {
            //SimulationLogger.log(IncidentUtil.class, Level.SEVERE, exception.getMessage(), exception);
        }
        return list;
    }

    public static String readContentFromFile(String fileName) {

        Path path = FileSystems.getDefault().getPath(Constants.LIST_OF_PUNISHED_VEHICLES_DIRECTORY + fileName);
        if(fileName == null) {
            return null;
        }
        List<String> content = new ArrayList<>();
        StringBuilder contentBuilder = new StringBuilder();
        try {
            content = Files.readAllLines(path);
        } catch (IOException exception) {
            SimulationLogger.log(IncidentUtil.class,Level.SEVERE,exception.getMessage(),exception);
        }
        for(String c: content) {
            contentBuilder.append(c).append("\n");
        }
        return contentBuilder.toString();
    }

    public static List<String> loadFilesList(String directory) throws FileLoadingException {

        List<String> listOfFiles = new ArrayList<>();
        File[] files = Paths.get(directory).toFile().listFiles();
        if (files == null) {
            throw new FileLoadingException();
        }
        for (File f : files)
            listOfFiles.add(f.getName());
        return listOfFiles;
    }

    public static void createIncidentFiles()
    {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        policeFileName = "KaznjeneOsobe" + sdf.format(new Date())+".ser";
        customsFileName = "KaznjenaVozila" + sdf.format(new Date())+".txt";

    }

    public static void writePoliceIncidentIntoFile()
    {

        try (var objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(Constants.LIST_OF_PUNISHED_PERSONS_DIRECTORY + policeFileName, false))) {
            objectOutputStream.writeObject(Simulation.policeRecord);
        } catch (IOException fileNotFoundException) {
            SimulationLogger.log(IncidentUtil.class, Level.SEVERE, fileNotFoundException.getMessage(), fileNotFoundException);
        }


/*
        String tempFileName = Constants.LIST_OF_PUNISHED_PERSONS_DIRECTORY + policeFileName + ".temp";

        // Write the new content to a temporary file
        try (var objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFileName))) {
            objectOutputStream.writeObject(Simulation.policeRecord);
        } catch (IOException fileNotFoundException) {
            SimulationLogger.log(IncidentUtil.class, Level.SEVERE, fileNotFoundException.getMessage(), fileNotFoundException);
        }

        // Rename the temporary file to the original filename
        File tempFile = new File(tempFileName);
        File newFile = new File(Constants.LIST_OF_PUNISHED_PERSONS_DIRECTORY + policeFileName);
        if (tempFile.renameTo(newFile)) {
            tempFile.delete();
        }
*/


    }

    public static void writeCustomsIncidentIntoFile()
    {
        try {
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(Constants.LIST_OF_PUNISHED_VEHICLES_DIRECTORY + customsFileName, false),
                                    StandardCharsets.UTF_8)));
            for (String incidentInfo : Simulation.customsRecord) {
                out.println(incidentInfo);
            }
            out.close();
        } catch (IOException fileNotFoundException) {
            SimulationLogger.log(IncidentUtil.class, Level.SEVERE, fileNotFoundException.getMessage(), fileNotFoundException);
        }
    }
}
