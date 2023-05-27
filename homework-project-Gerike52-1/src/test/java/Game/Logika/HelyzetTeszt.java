package Game.Logika;

import Game.Logika.Helyzet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HelyzetTeszt {

    Helyzet helyzet;

    @BeforeEach
    void init(){
        helyzet = new Helyzet(2,3);
    }

    @Test
    void szomszedTeszt() { assertTrue(helyzet.szomszed(new Helyzet(2,2))); }

    @Test
    void toStringTest() {
        assertEquals(" (2, 3) ", helyzet.toString());
    }

    @Test
    void equalsTest() {
        assertFalse(helyzet.equals(new Helyzet(3, 3)));

        assertTrue(helyzet.equals(new Helyzet(2, 3)));
        assertTrue(helyzet.equals(helyzet));
    }

}
