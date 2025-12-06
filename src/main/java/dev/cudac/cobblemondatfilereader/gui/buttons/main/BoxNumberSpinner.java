package dev.cudac.cobblemondatfilereader.gui.buttons.main;

import dev.cudac.cobblemondatfilereader.pokemon.PokemonManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoxNumberSpinner extends JSpinner implements ActionListener {

    public BoxNumberSpinner() {
        this.setModel(new SpinnerNumberModel(100, 1, 999, 1));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(e.getSource() instanceof JSpinner spinner)) {
            return;
        }

        if (spinner != this) {
            return;
        }

        if (!(spinner.getValue() instanceof Integer integerValue)) {
            return;
        }

        PokemonManager.instance().setMaxBoxNumber(integerValue);
    }

}
