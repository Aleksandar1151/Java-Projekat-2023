package granicni_prelaz.javaprojekat2023.gadgets;

import java.util.Random;

public class Suitcase {

    int ID_putnika;
    boolean imaNedozvoljeneStvari;

    Suitcase(int ID_putnika)
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
