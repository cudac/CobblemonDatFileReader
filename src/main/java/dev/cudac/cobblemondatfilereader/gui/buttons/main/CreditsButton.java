package dev.cudac.cobblemondatfilereader.gui.buttons.main;

import dev.cudac.cobblemondatfilereader.CobblemonDatFileReader;
import dev.cudac.cobblemondatfilereader.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;

public class CreditsButton extends JButton implements ActionListener {

    private static final String REPO_URL = "https://github.com/cudac/CobblemonDatFileReader";
    private static final String GITHUB_IMAGE_PATH = "github-mark/github-mark.png";

    public CreditsButton() {
        ImageUtils.getResourceImage(GITHUB_IMAGE_PATH).ifPresent(image -> {
            ImageIcon icon = ImageUtils.scaleImage(new ImageIcon(image), 16, 16);
            this.setIcon(icon);

            Dimension size = new Dimension(icon.getIconWidth(), icon.getIconHeight());
            this.setSize(size);
            this.setPreferredSize(size);
            this.setMaximumSize(size);
        });

        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setMargin(new Insets(0, 0, 0, 0));

        this.setAlignmentX(Component.CENTER_ALIGNMENT);
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
                CobblemonDatFileReader.logger().error("This platform does not support the BROWSE action! Copying to clipboard...");
            }
        } else {
            StringSelection selection = new StringSelection(REPO_URL);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
    }

}
