package granicni_prelaz.javaprojekat2023.Vozila;

import granicni_prelaz.javaprojekat2023.Osobe.Kofer;
import granicni_prelaz.javaprojekat2023.Osobe.Putnik;

import java.util.List;
import java.util.Random;

public class Autobus extends Vozilo{
    static int id = 1;

    public List<Kofer> koferiPutnika;
    public List<Putnik> putnici;

    Autobus() {
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
