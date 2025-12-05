package dev.cudac.cobblemondatfilereader.pokemon.objects;

public enum Gender {
    MALE,
    FEMALE,
    GENDERLESS;

    public String toString() {
        return "gender=" + name().toLowerCase();
    }

}
