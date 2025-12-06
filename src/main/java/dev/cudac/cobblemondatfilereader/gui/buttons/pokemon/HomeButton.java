package dev.cudac.cobblemondatfilereader.gui.buttons.pokemon;

import dev.cudac.cobblemondatfilereader.gui.WindowManager;
import dev.cudac.cobblemondatfilereader.gui.frames.MainPanel;
import dev.cudac.cobblemondatfilereader.pokemon.PokemonManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeButton extends JButton implements ActionListener {

    public HomeButton() {
        this.setText("Home");
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

        WindowManager.instance().setSelectedFile(null);
        WindowManager.instance().setStorageType(null);
        PokemonManager.instance().getActivePokemon().clear();

        MainPanel mainPanel = new MainPanel();
        WindowManager.instance().swapPanel(mainPanel);
    }

}
