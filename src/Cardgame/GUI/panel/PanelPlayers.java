package Cardgame.GUI.panel;

import Cardgame.Core.CardGame;
import Cardgame.Core.Player;
import Cardgame.GUI.IntRefreshableGUI;

import javax.swing.*;
import java.awt.*;

/**
 * Classe
 * <p>
 * Classe che rappresenta il pannello che visualizza il turno in corso, i punti ferita dei giocatori
 * e i dati dei mazzi
 */

public class PanelPlayers extends JPanel implements IntRefreshableGUI {

    private JPanel panelPlayers;

    private JPanel phases = new JPanel(new GridLayout(1, 1));

    private JLabel currentPhase = new JLabel();

    private JPanel infop1 = new JPanel(new GridLayout(3, 1));

    private JLabel turnp1 = new JLabel();
    private JLabel deckp1 = new JLabel();
    private JLabel lifep1 = new JLabel();

    private JPanel infop2 = new JPanel(new GridLayout(3, 1));
    private JLabel lifep2 = new JLabel();
    private JLabel deckp2 = new JLabel();
    private JLabel turnp2 = new JLabel();

    private Player p1;
    private Player p2;


    /**
     * Costruttore
     *
     * Genera la struttura di base del panel e setta i giocatori
     *
     * @param p1 Primo giocatore
     * @param p2 Secondo giocatore
     */
    public PanelPlayers(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;

        panelPlayers = new JPanel(new GridLayout(3, 1));

        phases = new JPanel(new GridLayout(1, 1));

        currentPhase = new JLabel();

        infop1 = new JPanel(new GridLayout(3, 1));

        turnp1 = new JLabel();
        deckp1 = new JLabel();
        lifep1 = new JLabel();

        infop2 = new JPanel(new GridLayout(3, 1));
        lifep2 = new JLabel();
        deckp2 = new JLabel();
        turnp2 = new JLabel();

        this.draw();

        panelPlayers.add(infop1);
        panelPlayers.add(phases);
        panelPlayers.add(infop2);

        this.add(panelPlayers);
    }

    /**
     * Metodo
     *
     * Ridisegna le parti del panel e visualizza i dati della partita in corso
     */
    @Override
    public void draw() {
        lifep1.setText("Life " + p1.name() + ": " + p1.getLife());
        deckp1.setText("Cards: " + p1.getDeck().cardsNumber());
        turnp1.setText(CardGame.instance.getCurrentPlayer().name()); //TODO mettere la bandierina

        infop1.add(turnp1, BorderLayout.NORTH);
        infop1.add(deckp1, BorderLayout.CENTER);
        infop1.add(lifep1, BorderLayout.SOUTH);

        lifep2.setText("Life " + p2.name() + ": " + p1.getLife());
        deckp2.setText("Cards: " + p2.getDeck().cardsNumber());
        turnp2.setText(CardGame.instance.getCurrentPlayer().name()); //TODO mettere la bandierina

        infop2.add(lifep2, BorderLayout.NORTH);
        infop2.add(deckp2, BorderLayout.CENTER);
        infop2.add(turnp2, BorderLayout.SOUTH);

        currentPhase.setText(CardGame.instance.getCurrentPlayer().currentPhase().toString());
        phases.add(currentPhase);
    }
}
