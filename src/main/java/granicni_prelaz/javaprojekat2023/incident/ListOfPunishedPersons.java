package granicni_prelaz.javaprojekat2023.incident;

import granicni_prelaz.javaprojekat2023.persons.Person;

import java.util.ArrayList;
import java.util.List;

public class ListOfPunishedPersons {

    List<Person> persons = new ArrayList<>();

    public void addPerson(Person person)
    {
        persons.add(person);
    }

}
