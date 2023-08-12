package granicni_prelaz.javaprojekat2023.gadgets;

import java.util.Random;

public class IdentificationDocument {

    int personID;
    boolean isValid;

    public IdentificationDocument(int personID)
    {
        this.personID = personID;
        Random rnd = new Random();

        if(rnd.nextInt(100) > 3)
        {
            isValid = true;
        }

    }


}
