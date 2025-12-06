package dev.cudac.cobblemondatfilereader.gui.frames;

import dev.cudac.cobblemondatfilereader.gui.buttons.pokemon.*;
import dev.cudac.cobblemondatfilereader.pokemon.PokemonManager;
import dev.cudac.cobblemondatfilereader.pokemon.objects.StoredPokemon;
import dev.cudac.cobblemondatfilereader.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class PokemonPanel extends JPanel {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private final JPanel gridPanel;
    private final JLabel headerLabel;

    private final PokemonStorageType storage;
    private final List<StoredPokemon> pokemon;
    private int currentPage = 0;

    public PokemonPanel(PokemonStorageType storage, List<StoredPokemon> pokemon) {
        this.storage = storage;
        this.pokemon = pokemon;

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        String headerText = storage == PokemonStorageType.PARTY ? "Party" : "Box " + currentPage;
        this.headerLabel = new JLabel(headerText);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(headerLabel, BorderLayout.NORTH);

        this.gridPanel = new JPanel(new GridLayout(storage.rows(), storage.columns(), storage.gap(), storage.gap()));
        this.add(gridPanel, BorderLayout.CENTER);

        JPanel navPanel = new JPanel(new FlowLayout());

        navPanel.add(new PreviousButton(storage));
        navPanel.add(new HomeButton());
        navPanel.add(new NextButton(storage));

        this.add(navPanel, BorderLayout.SOUTH);

        updateGrid();
        this.setVisible(true);
    }

    public void updateGrid() {
        gridPanel.removeAll();

        if (storage == PokemonStorageType.PARTY) {
            List<StoredPokemon> partyPokemon = pokemon.stream()
                .filter(p -> p.storage() == PokemonStorageType.PARTY)
                .sorted(Comparator.comparingInt(StoredPokemon::slot))
                .toList();

            for (int partySlot = 0; partySlot < 6; partySlot++) {
                if (partySlot >= partyPokemon.size()) {
                    gridPanel.add(new EmptySlotButton()); // empty slots for formatting
                    continue;
                }

                StoredPokemon storedPokemon = partyPokemon.get(partySlot);

                if (storedPokemon == null) {
                    gridPanel.add(new EmptySlotButton()); // empty slots for formatting
                    continue;
                }

                gridPanel.add(new PokemonButton(storedPokemon.pokemon()), storedPokemon.slot());
            }

            return;
        }

        List<StoredPokemon> boxPokemon = pokemon.stream()
            .filter(p -> p.storage() == PokemonStorageType.PC)
            .filter(p -> p.boxNumber() == currentPage)
            .sorted(Comparator.comparingInt(StoredPokemon::slot))
            .toList();

        final int MAX_BOX_SLOT = 30; // 5x5 total pokes in box page
        for (int boxSlot = 0; boxSlot < MAX_BOX_SLOT; boxSlot++) {
            if (boxSlot >= boxPokemon.size()) {
                gridPanel.add(new EmptySlotButton()); // empty slots for formatting
                continue;
            }

            StoredPokemon storedPokemon = boxPokemon.get(boxSlot);

            if (storedPokemon == null) {
                gridPanel.add(new EmptySlotButton()); // empty slots for formatting
                continue;
            }

            gridPanel.add(new PokemonButton(storedPokemon.pokemon()));
        }
    }

    public JLabel getHeaderLabel() {
        return headerLabel;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

}
