package dev.cudac.cobblemondatfilereader.pokemon;

import dev.cudac.cobblemondatfilereader.CobblemonDatFileReader;
import dev.cudac.cobblemondatfilereader.gui.WindowManager;
import dev.cudac.cobblemondatfilereader.gui.buttons.PokemonStorageType;
import dev.cudac.cobblemondatfilereader.pokemon.objects.Pokemon;
import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class PokemonManager {

    private static final int MAX_BOX_SIZE = 100;
    private static final int MAX_SLOT_SIZE = 30;
    private static final Pattern SLOT_PATTERN = Pattern.compile("Slot\\d+");
    private static final Pattern BOX_PATTERN = Pattern.compile("Box\\d+");

    private static final String POKE_API_ENDPOINT = "https://pokeapi.co/api/v2/pokemon/%species%";

    private static PokemonManager instance;
    private final Path spriteDirectory;

    private final List<Pokemon> activePokemon;

    private PokemonManager() {
        this.spriteDirectory = Paths.get("sprites");
        this.activePokemon = new ArrayList<>();
    }

    public static void init() {
        getInstance();
    }

    public void readPokemon() {
        Optional<File> file = WindowManager.getInstance().getSelectedFile();

        if (file.isEmpty()) {
            CobblemonDatFileReader.getLogger().severe("Unable to find file!");
            return;
        }

        Optional<PokemonStorageType> storageType = WindowManager.getInstance().getStorageType();

        if (storageType.isEmpty()) {
            CobblemonDatFileReader.getLogger().severe("Unable to find storage type!");
            return;
        }

        if (!activePokemon.isEmpty()) {
            activePokemon.clear();
        }

        CobblemonDatFileReader.getLogger().info("Reading .dat file " + file.get().getName() + "...");

        try {
            NamedTag result = NBTUtil.read(file.get());
            CompoundTag tag = (CompoundTag) result.getTag();

            System.out.println(tag.keySet());

            Pattern firstPattern, secondPattern;

            if (storageType.get() == PokemonStorageType.PARTY) {
                firstPattern = SLOT_PATTERN;
                secondPattern = null;
            } else {
                firstPattern = BOX_PATTERN;
                secondPattern = SLOT_PATTERN;
            }

            for (String key : tag.keySet()) {
                if (!firstPattern.matcher(key).matches()) {
                    continue;
                }

                if (secondPattern != null) {
                    CompoundTag innerTag = tag.getCompoundTag(key);

                    for (String innerKey : innerTag.keySet()) {
                        if (!secondPattern.matcher(innerKey).matches()) {
                            continue;
                        }

                        CompoundTag pokemonTag = innerTag.getCompoundTag(innerKey);
                        Pokemon pokemon = Pokemon.fromCompoundTag(pokemonTag);

                        activePokemon.add(pokemon);
                        CobblemonDatFileReader.getLogger().info("Found " + pokemon.species() + "!");
                    }

                    continue;
                }

                CompoundTag pokemonTag = tag.getCompoundTag(key);
                Pokemon pokemon = Pokemon.fromCompoundTag(pokemonTag);

                activePokemon.add(pokemon);
                CobblemonDatFileReader.getLogger().info("Found " + pokemon.species() + "!");
            }
        } catch (IOException e) {
            CobblemonDatFileReader.getLogger().severe("Unable to read .dat file " + file.get().getName() + "!");
            e.printStackTrace();
        }
    }

    public Optional<ImageIcon> getSpriteImage(Pokemon pokemon) {
//        try (HttpClient client = HttpClient.newHttpClient()) {
//            HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(POKE_API_ENDPOINT.replace("%species%", pokemon.species())))
//                .build();
//
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            JsonNode json = new ObjectMapper().readTree(response.body());
//
//            String url = json.path("sprites")
//                .path("other")
//                .path("official-artwork")
//                .path("front_default")
//                .asText();
//
//            return Optional.of(new ImageIcon(url));
//        } catch (IOException | InterruptedException e) {
//            PokemonVisualizer.getLogger().severe("Unable to get sprite image for " + pokemon.species());
//            e.printStackTrace();
//            return Optional.empty();
//        }

        ImageIcon imageIcon = new ImageIcon("sprites/pokemon/1.png");
        imageIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);

        return Optional.of(imageIcon);
    }

    public List<Pokemon> getActivePokemon() {
        return activePokemon;
    }

    public static PokemonManager getInstance() {
        if (instance == null) {
            instance = new PokemonManager();
        }

        return instance;
    }

}
