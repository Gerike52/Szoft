package Game.Logika;

import org.tinylog.Logger;

import static Game.Logika.Tabla.Meret;

public record Helyzet(int sor, int oszlop) {

    public Helyzet(int sor, int oszlop) {
        Logger.info("Sorork és oszlopok értékei: " + sor + " " + oszlop);
        if (sor >= Meret || sor < 0) {
            throw new IllegalArgumentException("Nem megfelelő a sor kordinára! Kerlek adj meg újat.");
        } else if (oszlop >= Meret || oszlop < 0){
            throw new IllegalArgumentException("Nem megfelelő a oszlop kordinára! Kerlek adj meg újat.");
        }else{
            this.oszlop = oszlop;
            this.sor = sor;
        }

    }

    public boolean szomszed(Helyzet Pozicio) {
        if (Math.abs(this.sor - Pozicio.sor) <= 1
                && Math.abs(this.oszlop - Pozicio.oszlop) <= 1
                && !(this.sor == Pozicio.sor() && this.oszlop == Pozicio.oszlop()))
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return String.format(" (%d, %d) ", sor, oszlop);
    }


    @Override
    public boolean equals(Object obj){
        if ( obj== this)
            return true;

        return (obj instanceof Helyzet helyzet) && helyzet.sor == sor && helyzet.oszlop == oszlop;
    }

}
