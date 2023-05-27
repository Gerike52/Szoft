package Game.Logika;

import Game.Logika.Jatekosok;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JatekosokTeszt {

    Jatekosok jatekosEgy;
    Jatekosok jatekosKetto;

    @BeforeEach
    void init(){
        jatekosEgy = Jatekosok.JATEKOS_EGY;
        jatekosKetto = Jatekosok.JATEKOS_KETTO;
    }

    @Test
    void kovetkezoTeszt(){
        this.jatekosEgy = this.jatekosEgy.kovetkezo();
        this.jatekosKetto = this. jatekosKetto.kovetkezo();

    }
}
