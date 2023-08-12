package granicni_prelaz.javaprojekat2023.vozila;

import java.util.Random;

public class Car extends Vehicle {
    static int id = 1;
    Car() {
        super("A " + id);
        id++;
    }

    void kreirajPutnike()
    {
        Random rnd = new Random();
        if(rnd.nextInt(100) > 30)
        {
            //kofer = new Kofer();
        }

    }
}
