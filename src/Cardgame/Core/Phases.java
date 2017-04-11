package Cardgame.Core;


public enum Phases {
    DRAW("draw"), UNTAP("untap"), COMBAT("combat"), MAIN("main"), END("end"), NULL("null");
    private static final Phases[] vals = values();
    private final String name;

    Phases(String n) {
        this.name = n;
    }

    public static Phases idx(int i) {
        return vals[i];
    }

    public String toString() {
        return name;
    }

    public Phases next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }

    public Phases prev() {
        return vals[(this.ordinal() + vals.length - 1) % vals.length];
    }

    public int get_idx() {
        return this.ordinal();
    }


}