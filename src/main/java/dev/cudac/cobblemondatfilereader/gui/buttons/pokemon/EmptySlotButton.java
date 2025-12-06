package dev.cudac.cobblemondatfilereader.gui.buttons.pokemon;

import dev.cudac.cobblemondatfilereader.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;

public class EmptySlotButton extends JButton {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    public EmptySlotButton() {
        this.setText("Empty");

        ImageUtils.getResourceImage("sprites/substitute.png").ifPresent(image -> {
            this.setIcon(ImageUtils.scaleImage(new ImageIcon(image), 32, 32));
        });

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);

        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);
    }

}
