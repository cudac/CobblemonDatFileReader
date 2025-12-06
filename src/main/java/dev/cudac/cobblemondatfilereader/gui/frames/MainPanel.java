package dev.cudac.cobblemondatfilereader.gui.frames;

import dev.cudac.cobblemondatfilereader.CobblemonDatFileReader;
import dev.cudac.cobblemondatfilereader.gui.buttons.main.*;
import dev.cudac.cobblemondatfilereader.gui.buttons.pokemon.PokemonStorageType;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;

    private final JButton fileSelectionButton;

    private final JCheckBox partySelectionBox;
    private final JCheckBox pcSelectionBox;

    private final JPanel boxNumberPanel;

    public MainPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JLabel titleText = new JLabel(CobblemonDatFileReader.implementationTitle(), SwingConstants.CENTER);
        titleText.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleText.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleText.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.add(Box.createVerticalStrut(15));
        this.add(titleText);

        JLabel versionText = new JLabel(CobblemonDatFileReader.implementationVersion(), SwingConstants.CENTER);
        versionText.setFont(new Font("SansSerif", Font.BOLD, 16));
        versionText.setAlignmentX(Component.CENTER_ALIGNMENT);
        versionText.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.add(versionText);
        this.add(Box.createVerticalStrut(20));

        JLabel chosenFileLabel = new JLabel("No file selected");
        chosenFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        chosenFileLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.fileSelectionButton = new FileSelectionButton(chosenFileLabel);
        this.add(fileSelectionButton);
        this.add(Box.createVerticalStrut(10));
        this.add(chosenFileLabel);

        this.partySelectionBox = new SourceSelectionButton(PokemonStorageType.PARTY);
        this.pcSelectionBox = new SourceSelectionButton(PokemonStorageType.PC);

        ButtonGroup checkBoxGroup = new ButtonGroup();
        checkBoxGroup.add(partySelectionBox);
        checkBoxGroup.add(pcSelectionBox);

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.add(partySelectionBox);
        checkBoxPanel.add(pcSelectionBox);
        this.add(checkBoxPanel);

        this.boxNumberPanel = new JPanel();
        boxNumberPanel.setVisible(false);

        JLabel boxNumberLabel = new JLabel("Select # of boxes:");
        boxNumberPanel.add(boxNumberLabel);

        JSpinner boxNumberSpinner = new BoxNumberSpinner();
        boxNumberPanel.add(boxNumberSpinner);

        this.add(boxNumberPanel);
        this.add(Box.createVerticalStrut(5));

        this.add(new ReadButton());
        this.add(Box.createVerticalStrut(10));

        this.add(new CreditsButton());
        this.add(Box.createVerticalGlue());

        this.setVisible(true);
    }

    public JButton fileSelectionButton() {
        return fileSelectionButton;
    }

    public JPanel boxNumberPanel() {
        return boxNumberPanel;
    }

    public JCheckBox partySelectionBox() {
        return partySelectionBox;
    }

    public JCheckBox pcSelectionBox() {
        return pcSelectionBox;
    }

}
