package dev.cudac.cobblemondatfilereader;

import dev.cudac.cobblemondatfilereader.gui.WindowManager;
import dev.cudac.cobblemondatfilereader.pokemon.PokemonManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class CobblemonDatFileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CobblemonDatFileReader.class);

    public static void main(String[] args) {
        String fullName = projectName();
        LOGGER.info("Initializing {}...", fullName);

        WindowManager.init();

        if (WindowManager.instance().activeWindow().isEmpty()) {
            LOGGER.error("Unable to initialize {}!", fullName);
            System.exit(-1);
            return;
        }

        LOGGER.info("Finished initializing {}...", fullName);
    }

    public static String implementationTitle() {
        return CobblemonDatFileReader.class.getPackage().getImplementationTitle() != null
            ? CobblemonDatFileReader.class.getPackage().getImplementationTitle()
            : CobblemonDatFileReader.class.getSimpleName();
    }

    public static String implementationVersion() {
        return CobblemonDatFileReader.class.getPackage().getImplementationVersion() != null
            ? "v" + CobblemonDatFileReader.class.getPackage().getImplementationVersion()
            : "DEV-SNAPSHOT";
    }

    public static String projectName() {
        return implementationTitle() + " " + implementationVersion();
    }

    public static Logger logger() {
        return LOGGER;
    }

    public static void printStacktrace(StackTraceElement[] stackTrace) {
        LOGGER.error("Stacktrace:");

        for (StackTraceElement stackTraceElement : stackTrace) {
            LOGGER.error(stackTraceElement.toString());
        }
    }


}