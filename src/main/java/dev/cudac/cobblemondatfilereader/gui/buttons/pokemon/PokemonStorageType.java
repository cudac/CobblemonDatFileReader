package dev.cudac.cobblemondatfilereader.gui.buttons.pokemon;

public enum PokemonStorageType {
    PARTY("Party", 2, 3, 10),
    PC("PC Box", 5, 5, 5);

    private final String buttonName;
    private final int rows;
    private final int columns;
    private final int gap;

    PokemonStorageType(String buttonName, int rows, int columns, int gap) {
        this.buttonName = buttonName;
        this.rows = rows;
        this.columns = columns;
        this.gap = gap;
    }

    public String buttonName() {
        return buttonName;
    }

    public int rows() {
        return rows;
    }

    public int columns() {
        return columns;
    }

    public int gap() {
        return gap;
    }

}
