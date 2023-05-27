package Game.Logika;

/**
 *  {@link Enum} ez az enum tartalmazza a két játékos közti váltást.
 */
public enum Jatekosok {

    /**
     * Ez az első játékos
     */
    JATEKOS_EGY,

    /**
     * Ez a második játékos
     */
    JATEKOS_KETTO;

    /**
     * A {@code kovetkezo} lehetővé teszi a váltást a két játékos között
     * @return visszatér a Jatekos_Kettovel ha az érték a játékos_Egy egyépként pedig a Jatekos_Eggyel tér vissza
     */
    public Jatekosok kovetkezo(){
        return switch (this){
            case JATEKOS_EGY -> JATEKOS_KETTO;
            case JATEKOS_KETTO -> JATEKOS_EGY;
        };
    }
}
