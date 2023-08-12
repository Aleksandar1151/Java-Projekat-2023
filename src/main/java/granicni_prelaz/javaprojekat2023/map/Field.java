package granicni_prelaz.javaprojekat2023.map;

import granicni_prelaz.javaprojekat2023.terminals.Terminal;
import granicni_prelaz.javaprojekat2023.vozila.Vehicle;

public class Field {
    private final int xPosition;
    private final int yPosition;

    private boolean hasTerminal;
    private Terminal terminal;

    private boolean hasVehicle;
    private Vehicle vehicle;


    public Field(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        this.hasTerminal = false;
        this.hasVehicle = false;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public boolean isHasTerminal() {
        return hasTerminal;
    }

    public void setHasTerminal(boolean hasTerminal) {
        this.hasTerminal = hasTerminal;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public boolean isHasVehicle() {
        return hasVehicle;
    }

    public void setHasVehicle(boolean hasVehicle) {
        this.hasVehicle = hasVehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            this.vehicle = null;
            this.hasVehicle = false;
        } else {
            this.vehicle = vehicle;
            this.setHasVehicle(true);
        }
    }
}
