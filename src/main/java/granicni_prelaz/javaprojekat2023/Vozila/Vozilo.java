package granicni_prelaz.javaprojekat2023.Vozila;

import granicni_prelaz.javaprojekat2023.Osobe.Putnik;
import granicni_prelaz.javaprojekat2023.Osobe.Vozac;

import java.util.List;

public abstract class Vozilo extends Thread{
    Vozac vozac;
    List<Putnik> putnici;
    String naziv;

    Vozilo(String naziv)
    {
        this.naziv = naziv;
    }

    public void run()
    {

    }
}
