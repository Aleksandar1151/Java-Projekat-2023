package granicni_prelaz.javaprojekat2023.Terminali;

import granicni_prelaz.javaprojekat2023.Vozila.Vozilo;

public abstract class Terminal {

    boolean terminalZauzet;
    boolean terminalUFunkciji;

    Vozilo vozilo;

    public abstract void procesirajVozilo();

    public void setTerminalUFunkciji(boolean terminalUFunkciji) {
        this.terminalUFunkciji = terminalUFunkciji;
    }

    public void setTerminalZauzet(boolean terminalZauzet) {
        this.terminalZauzet = terminalZauzet;
    }

    public void setVozilo(Vozilo vozilo) {
        this.vozilo = vozilo;
    }

    protected boolean provjeriZauzetost()
    {
        return vozilo == null;
    }


}
