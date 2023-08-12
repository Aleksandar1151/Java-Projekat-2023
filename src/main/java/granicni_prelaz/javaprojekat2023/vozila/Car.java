package granicni_prelaz.javaprojekat2023.vozila;

import granicni_prelaz.javaprojekat2023.constants.Constants;

import java.util.Random;

public class Car extends Vehicle {
    static int ID_Counter = 0;
    int id;
    public Car() {
        super("A " + ID_Counter, Constants.MAX_CAR_PASSENGER);
        id = ID_Counter++;
    }

}
