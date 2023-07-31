package granicni_prelaz.javaprojekat2023.Vozila;

import java.util.Random;

public class Automobil extends Vozilo{
    static int id = 1;
    Automobil() {
        super("Auto " + id);
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
