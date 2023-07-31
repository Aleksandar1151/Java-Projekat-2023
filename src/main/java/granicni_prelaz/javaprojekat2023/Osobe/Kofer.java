package granicni_prelaz.javaprojekat2023.Osobe;

import java.util.Random;

public class Kofer {

    int ID_putnika;
    boolean imaNedozvoljeneStvari;

    Kofer(int ID_putnika)
    {
        this.ID_putnika = ID_putnika;
        Random rnd = new Random();

        if(rnd.nextInt(100) < 10)
        {
            imaNedozvoljeneStvari = true;
        }
    }

    public boolean getImaNedozvoljeneStvari()
    {
        return imaNedozvoljeneStvari;
    }

}
