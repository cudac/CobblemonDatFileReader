package dev.cudac.cobblemondatfilereader.gui.buttons.pokemon;

import dev.cudac.cobblemondatfilereader.gui.WindowManager;
import dev.cudac.cobblemondatfilereader.gui.frames.PokemonPanel;
import dev.cudac.cobblemondatfilereader.pokemon.PokemonManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextButton extends JButton implements ActionListener {

    public NextButton(PokemonStorageType storage) {
        this.setText("Next");
        this.setVisible(storage == PokemonStorageType.PC);
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

        WindowManager.instance().activeWindow().ifPresent(window -> {
            if (window.getContentPane() instanceof PokemonPanel pokemonPanel) {
                int currentPage = pokemonPanel.getCurrentPage();

                if (currentPage >= PokemonManager.instance().getMaxBoxNumber()) {
                    this.setEnabled(false);
                    return;
                }

                ++currentPage;

                pokemonPanel.getHeaderLabel().setText("Box " + currentPage);
                pokemonPanel.setCurrentPage(currentPage);
                pokemonPanel.updateGrid();
            }
        });
    }

}
