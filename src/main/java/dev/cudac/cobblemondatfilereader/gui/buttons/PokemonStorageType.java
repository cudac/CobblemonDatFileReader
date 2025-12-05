package dev.cudac.cobblemondatfilereader.gui.buttons;

public enum PokemonStorageType {
    PARTY("Party", 2, 3, 10),
    PC("PC Box", 5, 5, 5);

    private final String buttonName;
    private final int rows;
    private final int cols;
    private final int gap;

    PokemonStorageType(String buttonName, int rows, int cols, int gap) {
        this.buttonName = buttonName;
        this.rows = rows;
        this.cols = cols;
        this.gap = gap;
    }

    public String getButtonName() {
        return buttonName;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getGap() {
        return gap;
    }

}
