package granicni_prelaz.javaprojekat2023.Osobe;

import javafx.scene.paint.RadialGradient;

import java.util.Random;

public abstract class Osoba {

    static int ID_Counter = 1;
    int ID;
    Kofer kofer;
    IdentifikacioniDokument identifikacioniDokument;

    Osoba()
    {
        Random rnd = new Random();
        ID = ID_Counter++;

        identifikacioniDokument = new IdentifikacioniDokument(ID);

    }
}
