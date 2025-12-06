package dev.cudac.cobblemondatfilereader.gui;

import dev.cudac.cobblemondatfilereader.CobblemonDatFileReader;
import dev.cudac.cobblemondatfilereader.gui.buttons.pokemon.PokemonStorageType;
import dev.cudac.cobblemondatfilereader.gui.frames.MainPanel;

import javax.swing.*;
import java.io.File;
import java.util.Optional;

public class WindowManager {

    private static WindowManager instance;

    private JFrame activeWindow;

    private File selectedFile;
    private PokemonStorageType storageType;

    private WindowManager() {
        this.activeWindow = new JFrame();

        final String TITLE = CobblemonDatFileReader.projectName();
        activeWindow.setTitle(TITLE);

//        activeWindow.setSize(300, 300);
//        activeWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        activeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainPanel mainPanel = new MainPanel();
        activeWindow.setContentPane(mainPanel);
        activeWindow.pack();

        activeWindow.setLocationRelativeTo(null);
        activeWindow.setResizable(false);
        activeWindow.setVisible(true);
    }

    public static void init() {
        instance();
    }

    public void swapPanel(JPanel panel) {
        activeWindow().ifPresent(window -> {
            window.setContentPane(panel);
            window.revalidate();
            window.repaint();
            window.pack();
            window.setLocationRelativeTo(null);
        });
    }

    public Optional<JFrame> activeWindow() {
        return Optional.ofNullable(activeWindow);
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
        activeWindow().ifPresent(window -> {
            window.revalidate();
            window.repaint();
        });
    }

    public Optional<File> selectedFile() {
        return Optional.ofNullable(selectedFile);
    }

    public void setStorageType(PokemonStorageType storageType) {
        this.storageType = storageType;
    }

    public Optional<PokemonStorageType> storageType() {
        return Optional.ofNullable(storageType);
    }

    public static WindowManager instance() {
        if (instance == null) {
            instance = new WindowManager();
        }

        return instance;
    }

}
