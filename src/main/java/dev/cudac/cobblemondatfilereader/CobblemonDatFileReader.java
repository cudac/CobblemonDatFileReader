package dev.cudac.cobblemondatfilereader;

import dev.cudac.cobblemondatfilereader.gui.WindowManager;
import dev.cudac.cobblemondatfilereader.pokemon.PokemonManager;

import java.util.logging.Logger;

public class CobblemonDatFileReader {

    private static final Logger LOGGER = Logger.getLogger(CobblemonDatFileReader.class.getSimpleName());

    public static void main(String[] args) {
        LOGGER.info("Initializing " + getFullName() + "...");

        PokemonManager.init();
        WindowManager.init();

        if (WindowManager.getInstance().getActiveWindow().isEmpty()) {
            LOGGER.severe("Unable to initialize " + getFullName() + "!");
            System.exit(-1);
            return;
        }

        LOGGER.info("Finished initializing " + getFullName() + "...");
    }

    public static String getImplementationTitle() {
        return CobblemonDatFileReader.class.getPackage().getImplementationTitle() != null
            ? CobblemonDatFileReader.class.getPackage().getImplementationTitle()
            : CobblemonDatFileReader.class.getSimpleName();
    }

    public static String getImplementationVersion() {
        return CobblemonDatFileReader.class.getPackage().getImplementationVersion() != null
            ? "v" + CobblemonDatFileReader.class.getPackage().getImplementationVersion()
            : "DEV-SNAPSHOT";
    }

    public static String getFullName() {
        
        return getImplementationTitle() + " " + getImplementationVersion();
    }

    public static Logger getLogger() {
        return LOGGER;
    }

}