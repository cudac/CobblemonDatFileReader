package dev.cudac.cobblemondatfilereader.gui.buttons;

import dev.cudac.cobblemondatfilereader.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;

public class CreditsButton extends JButton implements ActionListener {

    private static final String REPO_URL = "https://github.com/cudac/CobblemonDatFileReader";

    public CreditsButton() {
        this.setIcon(ImageUtils.getGitHubIcon());

        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setMargin(new Insets(0, 0, 0, 0));

        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.setToolTipText(REPO_URL);
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

        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(URI.create(REPO_URL));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
