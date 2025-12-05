package dev.cudac.cobblemondatfilereader.gui.buttons;

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

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.setText("<html>" + StringUtils.capitalizeString(pokemon.species()) + "<br><i>(click to copy)</i></html>");
        PokemonManager.getInstance().getSpriteImage(pokemon).ifPresent(this::setIcon);
        this.setHorizontalAlignment(SwingConstants.CENTER);

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
        setToolTipText("<html>Ability: " + pokemon.ability()
            + "<br>Nature: " + pokemon.nature()
            + "<br>Gender: " + pokemon.gender().name()
            + "<br><br>IVs: (" + StringUtils.formatDouble(pokemon.ivPercentage()) + "%)"
            + "<br>" + pokemon.iv(StatType.HP) + " HP / " + pokemon.iv(StatType.ATTACK) + " Atk / " + pokemon.iv(StatType.DEFENCE)
            + "<br>" + pokemon.iv(StatType.SPECIAL_ATTACK) + " SpA / " + pokemon.iv(StatType.SPECIAL_DEFENCE) + " SpD / " + pokemon.iv(StatType.SPEED) + " Spe"
            + "<br><br>EVs: (" + StringUtils.formatDouble(pokemon.evPercentage()) + "%)"
            + "<br>" + pokemon.ev(StatType.HP) + " HP / " + pokemon.ev(StatType.ATTACK) + " Atk / " + pokemon.ev(StatType.DEFENCE)
            + "<br>" + pokemon.ev(StatType.SPECIAL_ATTACK) + " SpA / " + pokemon.ev(StatType.SPECIAL_DEFENCE) + " SpD / " + pokemon.ev(StatType.SPEED) + " Spe"
            + "<br><br>Moves:"
            + "<br>" + pokemon.move(0) + " - " + pokemon.move(1)
            + "<br>" + pokemon.move(2) + " - " + pokemon.move(3) + "</html>"
        );

        WindowManager.getInstance().getActiveWindow().ifPresent(window -> {
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