package dev.cudac.cobblemondatfilereader.gui.frames;

import dev.cudac.cobblemondatfilereader.gui.buttons.HomeButton;
import dev.cudac.cobblemondatfilereader.gui.buttons.PokemonButton;
import dev.cudac.cobblemondatfilereader.gui.buttons.PokemonStorageType;
import dev.cudac.cobblemondatfilereader.pokemon.objects.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PokemonPanel extends JPanel {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 900;

    private final JPanel gridPanel;
    private final JButton prevButton;
    private final JButton nextButton;

    private final PokemonStorageType type;
    private final List<Pokemon> pokemon;
    private int currentPage = 0;

    public PokemonPanel(PokemonStorageType type, List<Pokemon> pokemon) {
        this.type = type;
        this.pokemon = pokemon;

        this.setLayout(new BorderLayout());

        String headerText = type == PokemonStorageType.PARTY ? "Party" : "Box " + currentPage;
        JLabel header = new JLabel(headerText);
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(header, BorderLayout.NORTH);

        this.gridPanel = new JPanel(new GridLayout(type.getRows(), type.getCols(), type.getGap(), type.getGap()));
        this.add(gridPanel, BorderLayout.CENTER);

        JPanel navPanel = new JPanel(new FlowLayout());

        this.prevButton = new JButton("Previous");
        prevButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                updateGrid();
            }
        });

        this.nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            if ((currentPage * getMaxPokesPerPage()) < pokemon.size()) {
                currentPage++;
                updateGrid();
            }
        });

        navPanel.add(prevButton);
        navPanel.add(new HomeButton());
        navPanel.add(nextButton);

        this.add(navPanel, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

//        if (this.getParent() instanceof JFrame frame) {
//            Dimension dimension = new Dimension(WIDTH, HEIGHT);
//            frame.setSize(dimension);
//            frame.setPreferredSize(dimension);
//            frame.setResizable(true);
//            frame.setLocationRelativeTo(null);
//        }

        updateGrid();
        this.setVisible(true);
    }

    private void updateGrid() {
        gridPanel.removeAll();

        int maxPokesPerPage = getMaxPokesPerPage();
        int start = currentPage * maxPokesPerPage;
        int end = Math.min(start + maxPokesPerPage, pokemon.size());

        for (int i = start; i < end; i++) {
            gridPanel.add(new PokemonButton(pokemon.get(i)));
        }

        for (int i = end - start; i < maxPokesPerPage; i++) {
            gridPanel.add(new JLabel()); // empty slots for formatting
        }

        prevButton.setEnabled(currentPage > 0);
        nextButton.setEnabled(end < pokemon.size());
    }

    private int getMaxPokesPerPage() {
        return type.getRows() * type.getCols();
    }

}
