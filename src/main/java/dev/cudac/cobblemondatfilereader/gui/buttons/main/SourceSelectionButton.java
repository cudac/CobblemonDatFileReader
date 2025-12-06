package dev.cudac.cobblemondatfilereader.gui.buttons.main;

import dev.cudac.cobblemondatfilereader.gui.WindowManager;
import dev.cudac.cobblemondatfilereader.gui.buttons.pokemon.PokemonStorageType;
import dev.cudac.cobblemondatfilereader.gui.frames.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SourceSelectionButton extends JCheckBox implements ActionListener {

    private final PokemonStorageType type;

    public SourceSelectionButton(PokemonStorageType type) {
        this.type = type;
        this.setText(type.buttonName());
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(e.getSource() instanceof JCheckBox checkBox)) {
            return;
        }

        if (checkBox != this) {
            return;
        }

        WindowManager.instance().activeWindow().ifPresent(window -> {
            if (window.getContentPane() instanceof MainPanel mainPanel) {
                boolean isPCSelected = type == PokemonStorageType.PC;

                mainPanel.boxNumberPanel().setVisible(isPCSelected);
            }
        });

        this.setSelected(true);
        WindowManager.instance().setStorageType(type);
    }

}
