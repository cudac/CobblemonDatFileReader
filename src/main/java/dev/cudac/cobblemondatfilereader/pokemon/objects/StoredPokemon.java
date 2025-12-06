package dev.cudac.cobblemondatfilereader.pokemon.objects;

import dev.cudac.cobblemondatfilereader.gui.buttons.pokemon.PokemonStorageType;

public record StoredPokemon(Pokemon pokemon, PokemonStorageType storage, Integer boxNumber, int slot) {

    public static StoredPokemon partyPokemon(Pokemon pokemon, int partySlot) {
        return new StoredPokemon(pokemon, PokemonStorageType.PARTY, null, partySlot);
    }

    public static StoredPokemon pcPokemon(Pokemon pokemon, int boxNumber, int boxSlot) {
        return new StoredPokemon(pokemon, PokemonStorageType.PC, boxNumber, boxSlot);
    }

}
