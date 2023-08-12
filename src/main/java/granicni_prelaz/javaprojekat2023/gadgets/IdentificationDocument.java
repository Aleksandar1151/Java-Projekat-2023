package granicni_prelaz.javaprojekat2023.gadgets;

import java.util.Random;

public class IdentificationDocument {

    int ID_osobe;
    boolean jeValidan;

    public IdentificationDocument(int ID_osobe)
    {
        this.ID_osobe = ID_osobe;
        Random rnd = new Random();

        if(rnd.nextInt(100) > 3)
        {
            jeValidan = true;
        }

    }


}
