package dev.cudac.cobblemondatfilereader.gui.frames;

import dev.cudac.cobblemondatfilereader.CobblemonDatFileReader;
import dev.cudac.cobblemondatfilereader.gui.buttons.*;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;

    private final JPanel mainPanel;
    private final JButton fileSelectionButton;
    private final JCheckBox partySelectionBox;
    private final JCheckBox pcSelectionBox;

    public MainPanel() {
        this.mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.add(mainPanel);

        JLabel titleText = new JLabel(CobblemonDatFileReader.getImplementationTitle(), SwingConstants.CENTER);
        titleText.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleText.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleText.setAlignmentY(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(titleText);

        JLabel versionText = new JLabel(CobblemonDatFileReader.getImplementationVersion(), SwingConstants.CENTER);
        versionText.setFont(new Font("SansSerif", Font.BOLD, 16));
        versionText.setAlignmentX(Component.CENTER_ALIGNMENT);
        versionText.setAlignmentY(Component.CENTER_ALIGNMENT);

        mainPanel.add(versionText);
        mainPanel.add(Box.createVerticalStrut(20));

        JLabel chosenFileLabel = new JLabel("No file selected");
        chosenFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        chosenFileLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.fileSelectionButton = new FileSelectionButton(chosenFileLabel);
        mainPanel.add(fileSelectionButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(chosenFileLabel);

        this.partySelectionBox = new SourceSelectionButton(PokemonStorageType.PARTY);
        this.pcSelectionBox = new SourceSelectionButton(PokemonStorageType.PC);

        ButtonGroup checkBoxGroup = new ButtonGroup();
        checkBoxGroup.add(partySelectionBox);
        checkBoxGroup.add(pcSelectionBox);

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.add(partySelectionBox);
        checkBoxPanel.add(pcSelectionBox);

        mainPanel.add(checkBoxPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        JButton readButton = new ReadButton();
        mainPanel.add(readButton);
        mainPanel.add(Box.createVerticalGlue());

        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(new CreditsButton());

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

//        if (this.getParent() instanceof JFrame frame) {
//            Dimension dimension = new Dimension(WIDTH, HEIGHT);
//            frame.setSize(dimension);
//            frame.setPreferredSize(dimension);
//            frame.setResizable(false);
//            frame.setLocationRelativeTo(null);
//        }

        this.setVisible(true);
    }

    public JButton getFileSelectionButton() {
        return fileSelectionButton;
    }

    public JCheckBox getPartySelectionBox() {
        return partySelectionBox;
    }

    public JCheckBox getPcSelectionBox() {
        return pcSelectionBox;
    }

}
