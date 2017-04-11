package Cardgame.GUI.panel;

import Cardgame.Core.CardGame;
import Cardgame.Core.DecoratedCreature;
import Cardgame.Core.Player;
import Cardgame.GUI.IntRefreshableGUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Classe
 *
 * Pannello che gestisce la grafica per la scelta delle creature da mettere in difesa
 */
public class PanelDefenders extends JPanel implements IntRefreshableGUI {

    private Player p1;
    private Player p2;

    private JPanel panelDefenders;
    private ArrayList<JButton> defendersList;
    private ArrayList<JLabel> effectsList;

    /**
     * Costruttore
     * Setta il layout e i giocatori coinvolti
     *
     * @param p1 Giocatore 1
     * @param p2 Giocatore 2
     */
    public PanelDefenders(Player p1, Player p2) {

        this.p1 = p1;
        this.p2 = p2;

        panelDefenders = new JPanel(new GridLayout(1, 1));
        defendersList = new ArrayList<>();
        effectsList = new ArrayList<>();

        this.draw();

        for (JButton b : defendersList) {
            panelDefenders.add(b, BorderLayout.NORTH);
        }
        for(JLabel l : effectsList){
            panelDefenders.add(l,BorderLayout.SOUTH);
        }

        this.add(panelDefenders);

    }

    //TODO actionlistener dei bottoni

    /**
     * Metodo
     *
     * Disegna le creature e le pone in una lista per l'elaborazione
     */
    @Override
    public void draw() {

        for (DecoratedCreature c : CardGame.instance.getCurrentAdversary().getCreatures()) {
            if (c.canDefend()) {
                JButton creature = new JButton(c.name());
                //TODO per ora metto il nome, ma poi mettiamo l'immagine della carta
                // TODO Si puo' usare direttamente la @PanelCard che è in grado di disegnarsi da sola e ha tutti i dati
                JLabel effect = new JLabel(c.toString());
                defendersList.add(creature);
                effectsList.add(effect);
            }
        }
    }
}
