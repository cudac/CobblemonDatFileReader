package dev.cudac.cobblemondatfilereader.gui;

import dev.cudac.cobblemondatfilereader.CobblemonDatFileReader;
import dev.cudac.cobblemondatfilereader.gui.buttons.PokemonStorageType;
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

        final String TITLE = CobblemonDatFileReader.getFullName();
        activeWindow.setTitle(TITLE);

        activeWindow.setSize(300, 300);
//        activeWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        activeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        activeWindow.setLocationRelativeTo(null);
        activeWindow.setResizable(false);

        MainPanel mainPanel = new MainPanel();
        activeWindow.setContentPane(mainPanel);

        activeWindow.pack();
        activeWindow.setVisible(true);
    }

    public static void init() {
        getInstance();
    }

    public void swapPanel(JPanel panel) {
        getActiveWindow().ifPresent(window -> {
            window.setContentPane(panel);
            window.revalidate();
            window.repaint();
        });
    }

    public Optional<JFrame> getActiveWindow() {
        return Optional.ofNullable(activeWindow);
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
        getActiveWindow().ifPresent(window -> {
            window.revalidate();
            window.repaint();
        });
    }

    public Optional<File> getSelectedFile() {
        return Optional.ofNullable(selectedFile);
    }

    public void setStorageType(PokemonStorageType storageType) {
        this.storageType = storageType;
    }

    public Optional<PokemonStorageType> getStorageType() {
        return Optional.ofNullable(storageType);
    }

    public static WindowManager getInstance() {
        if (instance == null) {
            instance = new WindowManager();
        }

        return instance;
    }

}
