package granicni_prelaz.javaprojekat2023.map;

import granicni_prelaz.javaprojekat2023.map.Field;

import java.util.ArrayList;
import java.util.List;

public class PathOnMap {
    private final List<Field> pathFields;

    public PathOnMap() {
        pathFields = new ArrayList<>();
    }

    public void addPathField(Field field) {
        pathFields.add(field);
    }

    public List<Field> getPathFields() {
        return pathFields;
    }

}
