package granicni_prelaz.javaprojekat2023.terminals;

import granicni_prelaz.javaprojekat2023.vozila.Vehicle;

public abstract class Terminal {

    String terminalName;
    boolean isBusy;
    boolean isInFunction;

    Vehicle vehicle;

    public abstract void procesirajVozilo();

    public void setInFunction(boolean inFunction) {
        this.isInFunction = inFunction;
    }

    public void setBusy(boolean busy) {
        this.isBusy = busy;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    protected boolean checkIfBusy()
    {
        return vehicle == null;
    }


    public String getTerminalName() {
        return terminalName;
    }

}
