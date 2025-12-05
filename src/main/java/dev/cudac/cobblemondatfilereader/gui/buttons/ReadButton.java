package dev.cudac.cobblemondatfilereader.gui.buttons;

import dev.cudac.cobblemondatfilereader.gui.WindowManager;
import dev.cudac.cobblemondatfilereader.gui.frames.MainPanel;
import dev.cudac.cobblemondatfilereader.gui.frames.PokemonPanel;
import dev.cudac.cobblemondatfilereader.pokemon.PokemonManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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

        Optional<File> selectedFile = WindowManager.getInstance().getSelectedFile();

        if (selectedFile.isEmpty()) {
            return;
        }

        Optional<PokemonStorageType> fileSource = WindowManager.getInstance().getStorageType();

        if (fileSource.isEmpty()) {
            return;
        }

        PokemonManager.getInstance().readPokemon();

        if (PokemonManager.getInstance().getActivePokemon().isEmpty()) {
            WindowManager.getInstance().getActiveWindow().ifPresent(window -> {
                if (window.getContentPane() instanceof MainPanel mainPanel) {
                    FileSelectionButton fileSelectionButton = (FileSelectionButton) mainPanel.getFileSelectionButton();
                    fileSelectionButton.getChosenFileLabel().setText("No Pokémon found");
                }
            });

            return;
        }

        PokemonPanel pokemonPanel = new PokemonPanel(fileSource.get(), PokemonManager.getInstance().getActivePokemon());
        WindowManager.getInstance().swapPanel(pokemonPanel);
    }

}
