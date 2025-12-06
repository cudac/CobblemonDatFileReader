package dev.cudac.cobblemondatfilereader.gui.buttons.main;

import dev.cudac.cobblemondatfilereader.CobblemonDatFileReader;
import dev.cudac.cobblemondatfilereader.gui.WindowManager;
import dev.cudac.cobblemondatfilereader.gui.buttons.pokemon.PokemonStorageType;
import dev.cudac.cobblemondatfilereader.gui.frames.MainPanel;
import dev.cudac.cobblemondatfilereader.gui.frames.PokemonPanel;
import dev.cudac.cobblemondatfilereader.pokemon.PokemonManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Optional;

public class ReadButton extends JButton implements ActionListener {

    public ReadButton() {
        final String TEXT = "Read File";
        this.setText(TEXT);

        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(e.getSource() instanceof JButton button)) {
            return;
        }

        if (button != this) {
            return;
        }

        Optional<File> selectedFile = WindowManager.instance().selectedFile();

        if (selectedFile.isEmpty()) {
            CobblemonDatFileReader.logger().warn("You must specify a file that ends in .dat!");
            return;
        }

        Optional<PokemonStorageType> fileSource = WindowManager.instance().storageType();

        if (fileSource.isEmpty()) {
            CobblemonDatFileReader.logger().warn("You must specify a storage type: {}!", Arrays.toString(PokemonStorageType.values()));
            return;
        }

        PokemonManager.instance().readPokemon(selectedFile.get(), fileSource.get());

        if (PokemonManager.instance().getActivePokemon().isEmpty()) {
            WindowManager.instance().activeWindow().ifPresent(window -> {
                if (window.getContentPane() instanceof MainPanel mainPanel) {
                    FileSelectionButton fileSelectionButton = (FileSelectionButton) mainPanel.fileSelectionButton();
                    fileSelectionButton.chosenFileLabel().setText("No Pokémon found");
                }
            });

            return;
        }

        PokemonPanel pokemonPanel = new PokemonPanel(fileSource.get(), PokemonManager.instance().getActivePokemon());
        WindowManager.instance().swapPanel(pokemonPanel);
    }

}
