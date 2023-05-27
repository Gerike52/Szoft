package Game.Logika;

import Game.Logika.Helyzet;
import Game.Logika.Tabla;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TablaTeszt {
    Tabla tabla;

    @BeforeEach
    void init(){tabla =new Tabla();}

    @Test
    void kivalasztottPontTeszt(){
        assertThrows(IllegalArgumentException.class, () -> tabla.kivalasztottPont(new Helyzet(5, 5)));

        tabla.kivalasztottPont(new Helyzet(1, 3));
        assertEquals(1, tabla.getKivalasztott().size());

        assertThrows(IllegalArgumentException.class, () -> tabla.kivalasztottPont(new Helyzet(1, 3)));
    }

    @Test
    void torlesTest() {
        tabla.kivalasztottPont(new Helyzet(1, 3));
        assertEquals(1, tabla.getKivalasztott().size());
        tabla.torles(new Helyzet(1, 3));
        assertEquals(0, tabla.getKivalasztott().size());

        tabla.tisztit();

        tabla.kivalasztottPont(new Helyzet(1, 3));
        tabla.kivalasztottPont(new Helyzet(2, 3));;
        tabla.torles(new Helyzet(2, 3));
        assertEquals(1, tabla.getKivalasztott().size());
    }

    @Test
    void tistitasTest() {
        tabla.kivalasztottPont(new Helyzet(1, 3));
        assertEquals(1, tabla.getKivalasztott().size());
        tabla.tisztit();
        assertEquals(0, tabla.getKivalasztott().size());
    }

    @Test
    void sorOszlopTest() {
        tabla.kivalasztottPont(new Helyzet(0, 0));
        assertTrue(tabla.sorOszlop());
        tabla.tisztit();

        tabla.kivalasztottPont(new Helyzet(2, 2));
        tabla.kivalasztottPont(new Helyzet(1, 2));
        tabla.kivalasztottPont(new Helyzet(3, 2));
        assertTrue(tabla.sorOszlop());

        tabla.tisztit();

        tabla.kivalasztottPont(new Helyzet(2, 2));
        tabla.kivalasztottPont(new Helyzet(1, 1));
        tabla.kivalasztottPont(new Helyzet(3, 3));
        assertFalse(tabla.sorOszlop());
    }

    @Test
    void azonosSorTest() {
        tabla.kivalasztottPont(new Helyzet(2, 3));
        tabla.kivalasztottPont(new Helyzet(2, 2));
        tabla.kivalasztottPont(new Helyzet(2, 1));
        assertTrue(tabla.azonosSor());
    }

    @Test
    void azonosOszlopTest() {
        tabla.kivalasztottPont(new Helyzet(1, 3));
        tabla.kivalasztottPont(new Helyzet(2, 3));
        tabla.kivalasztottPont(new Helyzet(0, 3));
        assertTrue(tabla.azonosOszlop());
    }
}

