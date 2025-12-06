package dev.cudac.cobblemondatfilereader.gui.buttons.pokemon;

import dev.cudac.cobblemondatfilereader.gui.WindowManager;
import dev.cudac.cobblemondatfilereader.gui.frames.PokemonPanel;
import dev.cudac.cobblemondatfilereader.pokemon.PokemonManager;
import dev.cudac.cobblemondatfilereader.pokemon.objects.Pokemon;
import dev.cudac.cobblemondatfilereader.pokemon.objects.StatType;
import dev.cudac.cobblemondatfilereader.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PokemonButton extends JButton implements MouseListener {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private final Pokemon pokemon;

    public PokemonButton(Pokemon pokemon) {
        this.pokemon = pokemon;

        PokemonManager.instance().setSpriteAsync(this, pokemon);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.setText(StringUtils.capitalizeString(pokemon.species()));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);

        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);

        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        StringSelection selection = new StringSelection(pokemon.toCommandFormat());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        setToolTipText("<html><b>Ability</b>: " + pokemon.ability()
            + "<br><b>Nature</b>: " + pokemon.nature()
            + "<br><b>Gender</b>: " + pokemon.gender().name()
            + "<br><br><b>IVs</b>: (" + StringUtils.formatDouble(pokemon.ivPercentage()) + "%)"
            + "<br>" + pokemon.iv(StatType.HP) + " HP / " + pokemon.iv(StatType.ATTACK) + " Atk / " + pokemon.iv(StatType.DEFENCE)
            + "<br>" + pokemon.iv(StatType.SPECIAL_ATTACK) + " SpA / " + pokemon.iv(StatType.SPECIAL_DEFENCE) + " SpD / " + pokemon.iv(StatType.SPEED) + " Spe"
            + "<br><br><b>EVs</b>: (" + StringUtils.formatDouble(pokemon.evPercentage()) + "%)"
            + "<br>" + pokemon.ev(StatType.HP) + " HP / " + pokemon.ev(StatType.ATTACK) + " Atk / " + pokemon.ev(StatType.DEFENCE)
            + "<br>" + pokemon.ev(StatType.SPECIAL_ATTACK) + " SpA / " + pokemon.ev(StatType.SPECIAL_DEFENCE) + " SpD / " + pokemon.ev(StatType.SPEED) + " Spe"
            + "<br><br><b>Moves</b>:"
            + "<br>" + pokemon.move(0) + " - " + pokemon.move(1)
            + "<br>" + pokemon.move(2) + " - " + pokemon.move(3) + "</html>"
        );

        WindowManager.instance().activeWindow().ifPresent(window -> {
            if (window.getContentPane() instanceof PokemonPanel pokemonPanel) {
                pokemonPanel.revalidate();
                pokemonPanel.repaint();
            }
        });
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setToolTipText(null);
    }

}