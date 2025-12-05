package dev.cudac.cobblemondatfilereader.pokemon.objects;

import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Pokemon {

    private final String species;
    private final String form;
    private final Map<StatType, Integer> ivs;
    private final Map<StatType, Integer> evs;
    private final int level;
    private final Gender gender;
    private final int friendship;
    private final boolean shiny;
    private final String nature;
    private final List<String> moveSet;
    private final String originalTrainer;
    private final String ability;
    private final String caughtBall;
    private final String heldItem;

    public Pokemon(String species, String form, Map<StatType, Integer> ivs, Map<StatType, Integer> evs, int level, Gender gender, int friendship,
                   boolean shiny, String nature, List<String> moveSet, String originalTrainer, String ability,
                   String caughtBall, String heldItem) {
        this.species = species;
        this.form = form;
        this.ivs = ivs;
        this.evs = evs;
        this.level = level;
        this.gender = gender;
        this.friendship = friendship;
        this.shiny = shiny;
        this.nature = nature;
        this.moveSet = moveSet;
        this.originalTrainer = originalTrainer;
        this.ability = ability;
        this.caughtBall = caughtBall;
        this.heldItem = heldItem;
    }

    public static Pokemon fromCompoundTag(CompoundTag tag) {
        String species = tag.getString("Species").replace("cobblemon:", "");
        String form = tag.getString("FormId");

        Map<StatType, Integer> ivs = new HashMap<>();
        CompoundTag ivsTag = tag.getCompoundTag("IVs").getCompoundTag("Base");

        for (String ivKey : ivsTag.keySet()) {
            int value = ivsTag.getInt(ivKey);
            ivs.put(StatType.valueOf(ivKey.replace("cobblemon:", "").toUpperCase()), value);
        }

        Map<StatType, Integer> evs = new HashMap<>();
        CompoundTag evsTag = tag.getCompoundTag("EVs");

        for (String evKey : evsTag.keySet()) {
            int value = evsTag.getInt(evKey);
            evs.put(StatType.valueOf(evKey.replace("cobblemon:", "").toUpperCase()), value);
        }

        int level = tag.getInt("Level");

        String genderString = tag.getString("Gender").replace("cobblemon:", "");
        Gender gender = null;
        try {
            gender = Gender.valueOf(genderString);
        } catch (IllegalArgumentException ignored) {}

        int friendship = tag.getInt("Friendship");
        boolean shiny = tag.getBoolean("Shiny");
        String nature = tag.getString("Nature").replace("cobblemon:", "");

        List<String> moveSet = new ArrayList<>();
        ListTag<?> moveSetTag = tag.getListTag("MoveSet");

        for (Tag<?> moveTag : moveSetTag) {
            CompoundTag compoundTag = (CompoundTag) moveTag;
            String moveName = compoundTag.getString("MoveName").replace("cobblemon:", "");
            moveSet.add(moveName);
        }

        String originalTrainer = tag.getString("PokemonOriginalTrainer").replace("cobblemon:", "");
        String ability = tag.getCompoundTag("Ability").getString("AbilityName").replace("cobblemon:", "");
        String caughtBall = tag.getString("CaughtBall").replace("cobblemon:", "");

        String heldItem;
        try {
            heldItem = tag.getCompoundTag("HeldItem").getString("id");
        } catch (NullPointerException e) {
            heldItem = "none";
        }

        return new Pokemon(species, form, ivs, evs, level, gender, friendship, shiny, nature, moveSet, originalTrainer, ability, caughtBall, heldItem);
    }

    public String species() {
        return species;
    }

    public String form() {
        return form;
    }

    public Map<StatType, Integer> ivs() {
        return ivs;
    }

    public int iv(StatType type) {
        return ivs.getOrDefault(type, 0);
    }

    public double ivPercentage() {
        int ivTotal = ivs.values().stream().mapToInt(Integer::intValue).sum();
        return (ivTotal / 186.0) * 100;
    }

    public Map<StatType, Integer> evs() {
        return evs;
    }

    public int ev(StatType type) {
        return evs.getOrDefault(type, 0);
    }

    public double evPercentage() {
        int evTotal = evs.values().stream().mapToInt(Integer::intValue).sum();
        return (evTotal / 510.0) * 100;
    }

    public int level() {
        return level;
    }

    public Gender gender() {
        return gender;
    }

    public int friendship() {
        return friendship;
    }

    public boolean shiny() {
        return shiny;
    }

    public String nature() {
        return nature;
    }

    public List<String> moveSet() {
        return moveSet;
    }

    public String move(int index) {
        try {
            return moveSet.get(index);
        } catch (IndexOutOfBoundsException e) {
            return "none";
        }
    }

    public String originalTrainer() {
        return originalTrainer;
    }

    public String ability() {
        return ability;
    }

    public String caughtBall() {
        return caughtBall;
    }

    public String heldItem() {
        return heldItem;
    }

    public String toCommandFormat() {
        StringBuilder command = new StringBuilder("pokegive ").append(species.replace("cobblemon:", ""));

        if (!form.equals("normal")) {
            command.append(" ").append(form);
        }

        if (shiny) {
            command.append(" ").append("shiny");
        }

        if (!ivs.isEmpty()) {
            for (Map.Entry<StatType, Integer> entry : ivs.entrySet()) {
                command.append(" ").append(entry.getKey().toString(entry.getValue()));
            }
        }

        if (!evs.isEmpty()) {
            for (Map.Entry<StatType, Integer> entry : evs.entrySet()) {
                command.append(" ").append(entry.getKey().toString(entry.getValue()));
            }
        }

        command.append(" ").append("level=").append(level);

        if (gender != null) {
            command.append(" ").append("gender=").append(gender);
        }

        command.append(" ").append("friendship=").append(friendship);
        command.append(" ").append("nature=").append(nature);
        command.append(" ").append("originaltrainer=").append(originalTrainer);
        command.append(" ").append("ability=").append(ability);
        command.append(" ").append("pokeball=").append(caughtBall);
        command.append(" ").append("helditem=").append(heldItem);

        return command.toString();
    }

}
