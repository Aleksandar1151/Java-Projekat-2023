package granicni_prelaz.javaprojekat2023.terminals;

import granicni_prelaz.javaprojekat2023.vozila.Vehicle;

public abstract class Terminal {

    String terminalName;
    boolean terminalZauzet;
    boolean terminalUFunkciji;

    Vehicle vozilo;

    public abstract void procesirajVozilo();

    public void setTerminalUFunkciji(boolean terminalUFunkciji) {
        this.terminalUFunkciji = terminalUFunkciji;
    }

    public void setTerminalZauzet(boolean terminalZauzet) {
        this.terminalZauzet = terminalZauzet;
    }

    public void setVozilo(Vehicle vozilo) {
        this.vozilo = vozilo;
    }

    protected boolean provjeriZauzetost()
    {
        return vozilo == null;
    }


    public String getTerminalName() {
        return terminalName;
    }

}
