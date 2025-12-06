package dev.cudac.cobblemondatfilereader.utils;

import dev.cudac.cobblemondatfilereader.CobblemonDatFileReader;
import dev.cudac.cobblemondatfilereader.pokemon.objects.Pokemon;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class ImageUtils {

    public static ImageIcon scaleImage(ImageIcon imageIcon, int width, int height) {
        return new ImageIcon(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    public static Optional<BufferedImage> getResourceImage(String resourcePath) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (classLoader == null) {
            return Optional.empty();
        }

        URL resource = classLoader.getResource(resourcePath);

        if (resource == null) {
            return Optional.empty();
        }

        try {
            BufferedImage image = ImageIO.read(resource);

            if (image == null) {
                return Optional.empty();
            }

            return Optional.of(image);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
