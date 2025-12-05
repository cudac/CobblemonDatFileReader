package dev.cudac.cobblemondatfilereader.gui.buttons;

import dev.cudac.cobblemondatfilereader.gui.WindowManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SourceSelectionButton extends JCheckBox implements ActionListener {

    private final PokemonStorageType type;

    public SourceSelectionButton(PokemonStorageType type) {
        this.type = type;
        this.setText(type.getButtonName());
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

        this.setSelected(true);

        WindowManager.getInstance().setStorageType(type);
    }

}
