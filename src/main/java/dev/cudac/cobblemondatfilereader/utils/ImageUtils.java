package dev.cudac.cobblemondatfilereader.utils;

import javax.swing.*;
import java.awt.*;

public class ImageUtils {

    public static ImageIcon getGitHubIcon() {
        ImageIcon imageIcon = new ImageIcon("github-mark/github-mark-white.png");
        imageIcon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);

        return imageIcon;
    }

}
