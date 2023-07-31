package granicni_prelaz.javaprojekat2023.Osobe;

import java.util.Random;

public class IdentifikacioniDokument {

    int ID_osobe;
    boolean jeValidan;

    public IdentifikacioniDokument(int ID_osobe)
    {
        this.ID_osobe = ID_osobe;
        Random rnd = new Random();

        if(rnd.nextInt(100) > 3)
        {
            jeValidan = true;
        }

    }


}
