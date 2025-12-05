package dev.cudac.cobblemondatfilereader.gui.buttons;

import dev.cudac.cobblemondatfilereader.gui.WindowManager;
import dev.cudac.cobblemondatfilereader.gui.frames.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileSelectionButton extends JButton implements ActionListener {

    private final JLabel chosenFileLabel;

    public FileSelectionButton(JLabel chosenFileLabel) {
        this.chosenFileLabel = chosenFileLabel;

        final String TEXT = "Select .dat Pokémon file";
        this.setText(TEXT);

        this.setAlignmentX(Component.CENTER_ALIGNMENT);
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

        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setCurrentDirectory(new File("/home/cudac/dev/projects/testserver_cobblemon/world/pokemon/playerpartystore/af"));

        int response = fileChooser.showOpenDialog(null);

        if (response != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();

        if (!file.getName().endsWith(".dat")) {
            throw new IllegalStateException("You must select a '.dat' file!");
        }

        File twoDirsUp = file.getParentFile().getParentFile();

        if (twoDirsUp.isDirectory()) {
            switch (twoDirsUp.getName()) {
                case "pcstore" -> {
                    WindowManager.getInstance().setStorageType(PokemonStorageType.PC);
                    WindowManager.getInstance().getActiveWindow().ifPresent(window -> {
                        if (window.getContentPane() instanceof MainPanel mainPanel) {
                            mainPanel.getPcSelectionBox().setSelected(true);
                        }
                    });
                }
                case "playerpartystore" -> {
                    WindowManager.getInstance().setStorageType(PokemonStorageType.PARTY);
                    WindowManager.getInstance().getActiveWindow().ifPresent(window -> {
                        if (window.getContentPane() instanceof MainPanel mainPanel) {
                            mainPanel.getPartySelectionBox().setSelected(true);
                        }
                    });
                }
            }
        }

        WindowManager.getInstance().setSelectedFile(file);
        chosenFileLabel.setText(file.getName());
    }

    public JLabel getChosenFileLabel() {
        return chosenFileLabel;
    }

}
