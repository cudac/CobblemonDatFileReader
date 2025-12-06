package dev.cudac.cobblemondatfilereader.pokemon;

import dev.cudac.cobblemondatfilereader.CobblemonDatFileReader;
import dev.cudac.cobblemondatfilereader.gui.buttons.pokemon.PokemonStorageType;
import dev.cudac.cobblemondatfilereader.pokemon.objects.Pokemon;
import dev.cudac.cobblemondatfilereader.pokemon.objects.StoredPokemon;
import dev.cudac.cobblemondatfilereader.utils.ImageUtils;
import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class PokemonManager {

    private static final String POKE_API_ENDPOINT = "https://pokeapi.co/api/v2/pokemon/%pokemon%";
    private static final Pattern SLOT_PATTERN = Pattern.compile("Slot\\d+");
    private static final Pattern BOX_PATTERN = Pattern.compile("Box\\d+");

    private static PokemonManager instance;

    private final List<StoredPokemon> activePokemon = new ArrayList<>();

    private final Map<String, BufferedImage> spriteCache = new ConcurrentHashMap<>();
    private final Map<String, CompletableFuture<BufferedImage>> ongoingSpriteFetches = new ConcurrentHashMap<>();

    private HttpClient httpClient;
    private int maxBoxNumber;

    private PokemonManager() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public void readPokemon(File file, PokemonStorageType storage) {
        if (!activePokemon.isEmpty()) {
            activePokemon.clear();
        }

        CobblemonDatFileReader.logger().info("Reading the following file: {}...", file.getName());

        try {
            NamedTag result = NBTUtil.read(file);
            CompoundTag tag = (CompoundTag) result.getTag();

            Pattern firstPattern, secondPattern;

            if (storage == PokemonStorageType.PARTY) {
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

                        Integer boxNumber;

                        try {
                            boxNumber = Integer.parseInt(key.replace("Box", ""));
                        } catch (NumberFormatException e) {
                            boxNumber = null;

                            CobblemonDatFileReader.logger().warn("Unable to get pc box number from key {}!", innerKey);
                            CobblemonDatFileReader.printStacktrace(e.getStackTrace());
                        }

                        if (boxNumber == null) {
                            return;
                        }

                        Integer boxSlot;

                        try {
                            boxSlot = Integer.parseInt(innerKey.replace("Slot", ""));
                        } catch (NumberFormatException e) {
                            boxSlot = null;

                            CobblemonDatFileReader.logger().warn("Unable to get pc box slot number from key {}!", innerKey);
                            CobblemonDatFileReader.printStacktrace(e.getStackTrace());
                        }

                        if (boxSlot == null) {
                            return;
                        }

                        CompoundTag pokemonTag = innerTag.getCompoundTag(innerKey);
                        Pokemon pokemon = Pokemon.fromCompoundTag(pokemonTag);
                        loadSlot(StoredPokemon.pcPokemon(pokemon, boxNumber, boxSlot));
                    }
                } else {
                    Integer partySlot;

                    try {
                        partySlot = Integer.parseInt(key.replace("Slot", ""));
                    } catch (NumberFormatException e) {
                        partySlot = null;
                        CobblemonDatFileReader.logger().warn("Unable to get party slot number from key {}!", key);
                        CobblemonDatFileReader.printStacktrace(e.getStackTrace());
                    }

                    if (partySlot == null) {
                        return;
                    }

                    CompoundTag pokemonTag = tag.getCompoundTag(key);
                    Pokemon pokemon = Pokemon.fromCompoundTag(pokemonTag);
                    loadSlot(StoredPokemon.partyPokemon(pokemon, partySlot));
                }
            }
        } catch (IOException e) {
            CobblemonDatFileReader.logger().error("Unable to read the following file: {}!", file.getName());
            CobblemonDatFileReader.printStacktrace(e.getStackTrace());
        }
    }

    private void loadSlot(StoredPokemon pokemon) {
        this.activePokemon.add(pokemon);
        CobblemonDatFileReader.logger().info("Found the following pokemon: {}!", pokemon.pokemon().species());
    }

    public void setSpriteAsync(JButton button, Pokemon pokemon) {
        if (spriteCache.containsKey(pokemon.species()) && spriteCache.get(pokemon.species()) != null) {
            button.setIcon(ImageUtils.scaleImage(new ImageIcon(spriteCache.get(pokemon.species())), 32, 32));
            return;
        }

        ongoingSpriteFetches.computeIfAbsent(pokemon.species(), key -> {
            String pokemonRequest;

            if (!pokemon.form().equals("normal")) {
                pokemonRequest = pokemon.species() + "-" + pokemon.form();
            } else {
                pokemonRequest = pokemon.species();
            }

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(POKE_API_ENDPOINT.replace("%pokemon%", pokemonRequest)))
                .GET()
                .build();

            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() != 200) {
                        CobblemonDatFileReader.logger().warn("Received status code {}!", response.statusCode());
                        return ImageUtils.getResourceImage("sprites/substitute.png").get();
                    }

                    JsonNode json = new ObjectMapper().readTree(response.body());
                    int dexId = json.path("id").asInt();

                    Optional<BufferedImage> foundImage = ImageUtils.getResourceImage("sprites/" + (pokemon.shiny() ? "shiny" : "") + "/" + dexId + ".png");

                    if (foundImage.isEmpty()) {
                        return ImageUtils.getResourceImage("sprites/substitute.png").get();
                    }

                    return foundImage.get();
                });
        }).thenAccept(image -> {
            if (image == null) {
                return;
            }

            spriteCache.putIfAbsent(pokemon.species(), image);
            SwingUtilities.invokeLater(() -> button.setIcon(ImageUtils.scaleImage(new ImageIcon(image), 32, 32)));

            ongoingSpriteFetches.remove(pokemon.species());
            CobblemonDatFileReader.logger().info("Successfully loaded fetched image for the following pokemon: {}!", pokemon.species());
        }).exceptionally(e -> {
            CobblemonDatFileReader.logger().error("Unable to load fetched image for the following pokemon: {}!", pokemon.species());
            CobblemonDatFileReader.printStacktrace(e.getStackTrace());

            ongoingSpriteFetches.remove(pokemon.species());
            return null;
        });
    }

    public void setMaxBoxNumber(int maxBoxNumber) {
        this.maxBoxNumber = maxBoxNumber;
    }

    public int getMaxBoxNumber() {
        return maxBoxNumber;
    }

    public List<StoredPokemon> getActivePokemon() {
        return activePokemon;
    }

    public static PokemonManager instance() {
        if (instance == null) {
            instance = new PokemonManager();
        }

        return instance;
    }

}
