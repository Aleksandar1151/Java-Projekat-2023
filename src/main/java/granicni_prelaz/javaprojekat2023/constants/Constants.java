package granicni_prelaz.javaprojekat2023.constants;

import java.io.File;

public abstract class Constants {

    public static final int MAX_CAR_PASSENGER = 4;
    public static final int MAX_BUS_PASSENGER = 51;
    public static final int MAX_TRUCK_PASSENGER = 2;

    public static final int BROJ_AUTOMOBILA = 35;
    public static final int BROJ_AUTOBUSA = 5;
    public static final int BROJ_KAMIONA = 10;

    public static final String FAJL_TERMINALI = "";
    public static final String FAJL_EVIDENCIJA_NEPRELSAKA = "";
    public static final String FAJL_EVIDENCIJA_KAZNJENIH_OSOBA = "";


    //colors
    public static final String WHITE = "#FFFFFF";
    public static final String BLUE = "#00308F";
    public static final String GRAY = "#bbbbbb";
    public static final String RED = "ce3322";
    public static final String GREEN = "85af38";
    public static final String BRONZE = "#CD7F32";
    public static final String ORANGE = "#CC5500";
    public static final String POLICE_BLUE = "#374f6b";
    public static final String POLICE_TRUCK_BLUE = "#082759";


    public static final String PATH_ON_MAP = "." + File.separator + "res" + File.separator + "json" + File.separator + "path.json";
    public static final int NUMBER_OF_VEHICLES = 50;
}
