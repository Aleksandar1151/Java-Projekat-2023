package granicni_prelaz.javaprojekat2023.vozila;

import granicni_prelaz.javaprojekat2023.gadgets.Suitcase;
import granicni_prelaz.javaprojekat2023.persons.Passenger;

import java.util.List;
import java.util.Random;

public class Bus extends Vehicle {
    static int id = 1;

    public List<Suitcase> koferiPutnika;
    public List<Passenger> putnici;

    Bus() {
        super("B " + id);
        id++;
        kreirajPutnike();

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
