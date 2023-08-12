package granicni_prelaz.javaprojekat2023.persons;

import granicni_prelaz.javaprojekat2023.gadgets.IdentificationDocument;
import granicni_prelaz.javaprojekat2023.gadgets.Suitcase;

import java.util.Random;

public abstract class Person {

    static int ID_Counter = 1;
    int ID;
    Suitcase kofer;
    IdentificationDocument identifikacioniDokument;

    Person()
    {
        Random rnd = new Random();
        ID = ID_Counter++;

        identifikacioniDokument = new IdentificationDocument(ID);

    }
}
