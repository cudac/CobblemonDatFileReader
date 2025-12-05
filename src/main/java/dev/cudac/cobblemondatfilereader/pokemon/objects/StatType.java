package dev.cudac.cobblemondatfilereader.pokemon.objects;

public enum StatType {
    HP,
    ATTACK,
    DEFENCE,
    SPECIAL_ATTACK,
    SPECIAL_DEFENCE,
    SPEED;

    public String toString(int value) {
        return name().toLowerCase() + "=" + value;
    }

}
