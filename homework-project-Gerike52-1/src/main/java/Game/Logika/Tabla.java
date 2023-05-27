package Game.Logika;

import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.ArrayList;

import org.tinylog.Logger;

/**
 * A {@code Tabla}  az egész játékot reprezentálja és egyuttal egy {@link Class}
 */

public class Tabla {

    /**
     * A tábla méretét adja meg
     */
    public static final int Meret = 4;

    private Jatekosok kezdo;

    private ArrayList<Helyzet> kivalasztott;

    private ReadOnlyObjectWrapper<Allapot>[][] mezo = new ReadOnlyObjectWrapper[Meret][Meret];


    /**
     * A {@code abla} a tábla inicializálása
     * Átadja a kiválasztott pontokaz egy üres {@link  ArrayList}
     */
    public Tabla() {
        Logger.info("A tábla előkészítése a játékhoz!");
        kezdo = Jatekosok.JATEKOS_EGY;
        for (var sor = 0; sor < Meret; sor++) {
            for (var oszlop = 0; oszlop < Meret; oszlop++) {
                mezo[sor][oszlop] = new ReadOnlyObjectWrapper<Allapot>(Allapot.KAVICS);
            }
        }
        kivalasztott = new ArrayList<Helyzet>();
    }

    /**
     * A {@code kivalasztottPont} átadja a kivalasztottPont pozícióját a {@link ArrayList<Helyzet>}
     *
     * @param helyzet tartalmazza azt a sort és oszlopot, amelyik hozzá lesz adva a kivalasztottPont ha nem ad vissza IllegalArgumentException
     */

    public void kivalasztottPont(Helyzet helyzet) {
        Logger.trace("A kiválsztott pont hozzáadása: " + helyzet);
        for (var Jelolt : kivalasztott) {
            if (Jelolt.equals(helyzet)) throw new IllegalArgumentException("Ez a Kavics már ki van választva! ");
        }
        kivalasztott.add(helyzet);
    }

    /**
     * Eltávolítsa a kiválasztott pontot hogyha az már ki volt választva
     *
     * @param helyzet egy pozízió sorát és oszlopát jelöli.
     */
    public void torles(Helyzet helyzet) {
        Logger.trace("A kiválsztott pont törlése: " + helyzet);
        for (var Jelolt : kivalasztott) {
            if (Jelolt.equals(helyzet)) {
                kivalasztott.remove(helyzet);
                return;
            }
        }
    }


    /**
     * {@link  ArrayList<Helyzet>} a kiválasztott pontokat kappja meg.
     *
     * @return a kiválasztott pozíció másolatával.
     */
    public ArrayList<Helyzet> getKivalasztott() {
        return (ArrayList<Helyzet>) kivalasztott.clone();
    }

    /**
     * Kiüríti a kivalasztott pontokat és ujra kiválaszthatóvá teszi
     */
    public void tisztit() {
        kivalasztott.clear();
    }

    /**
     * Ez a {@code azonosSor} megnézi hogy a kiválasztott sor pozíciók az elsre kiválasztott pontal megeggyezik-e.
     *
     * @return az összes olyan sor, amely a kiválasztott pozíciókkal azonos sorban van.
     */

    public boolean azonosSor() {
        var Sorok = new ArrayList<Integer>();

        Logger.info("Egyező sorok vizsgálata");
        for (var Sorkordi : kivalasztott) {
            Sorok.add(Sorkordi.sor());
        }
        return Sorok.stream().allMatch(Sorok.get(0)::equals);
    }

    /**
     * Ez a {@code azonosOszlop} megnézi hogy a kiválasztott oszlop pozíciók az elsőre kiválasztott pontal megeggyezik-e.
     *
     * @return az összes olyan oszlop, amely a kiválasztott pozíciókkal azonos oszlopbaban van.
     */
    public boolean azonosOszlop() {
        var Oszlopok = new ArrayList<Integer>();

        Logger.info("Egyező oszlopok vizsgálata");
        for (var Oszlopkordi : kivalasztott) {
            Oszlopok.add(Oszlopkordi.oszlop());
        }
        return Oszlopok.stream().allMatch(Oszlopok.get(0)::equals);
    }

    /**
     * @return igazzal tér vissza ha akiválasztott pontok sora vagy az oszlopai egyeznek minden más esetben hasmis.
     */
    public boolean sorOszlop() {
        if (kivalasztott.size() == 1) {
            return true;
        } else if (kivalasztott.size() < 1 || kivalasztott.size() > 4) {
            return false;
        } else if (azonosSor()) {
            return true;
        } else if (azonosOszlop()) {
            return true;
        }
        return false;
    }

    /**
     * Megkapja a következő játékost a következő körre
     */
    public void kovetkezoj() {
        this.kezdo = this.kezdo.kovetkezo();
    }

    /**
     * @return visszatér a kezdővel
     */
    public Jatekosok getKezdo() {
        return kezdo;
    }
}