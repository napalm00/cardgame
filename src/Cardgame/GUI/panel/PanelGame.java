package Cardgame.GUI.panel;

import Cardgame.Core.CardGame;
import Cardgame.Core.DecoratedCreature;
import Cardgame.Core.Interface.Card;
import Cardgame.Core.Player;
import Cardgame.GUI.IntRefreshableGUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel rappresentante il campo da gioco con le mani dei due giocatori
 */
public class PanelGame extends JPanel implements IntRefreshableGUI {

    private JPanel board;
    private JPanel handp1;  //Current player
    private JPanel handp2;  //Opponent
    private JPanel fieldp1;
    private JPanel fieldp2;
    private Player p1, p2;

    /**
     * Costruttore
     *
     * Genera il layout completo del campo di gioco.
     * Lo genera vuoto
     * @param p1 Giocatore 1
     * @param p2 Giocatore 2
     */
    public PanelGame(Player p1, Player p2) {
        board = new JPanel(new GridLayout(0, 1));
        handp1 = new JPanel(new GridLayout(1, 0));
        handp2 = new JPanel(new GridLayout(1, 0));
        fieldp1 = new JPanel(new GridLayout(1, 0));
        fieldp2 = new JPanel(new GridLayout(1, 0));

        this.p1 = p1;
        this.p2 = p2;
        this.draw();

        this.board.setPreferredSize(new Dimension(1200, 1000));
        this.fieldp1.setPreferredSize(new Dimension(1200, 180));
        this.fieldp2.setPreferredSize(new Dimension(1200, 180));
        this.handp1.setPreferredSize(new Dimension(1200, 180));
        this.handp2.setPreferredSize(new Dimension(1200, 180));


        JScrollPane scrollfield1 = new JScrollPane(fieldp1, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane scrollfield2 = new JScrollPane(fieldp2, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane scrollhand1 = new JScrollPane(handp1, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane scrollhand2 = new JScrollPane(handp2, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scrollfield1.setPreferredSize(new Dimension(800, 200));
        scrollfield2.setPreferredSize(new Dimension(800, 200));
        scrollhand1.setPreferredSize(new Dimension(800, 200));
        scrollhand2.setPreferredSize(new Dimension(800, 200));

        /*
        fieldp1.setBackground(Color.BLUE);
        fieldp2.setBackground(Color.RED);
        handp2.setBackground(Color.CYAN);
        handp1.setBackground(Color.YELLOW);
        */
        //JLabel test = new JLabel("test");

        board.add(scrollhand1);
        board.add(scrollfield1);
        board.add(scrollfield2);
        board.add(scrollhand2);
        //System.out.println(CardGame.instance.getCurrentPlayer().getHand().toString());

        /*
        board.add(handp1);
        board.add(fieldp1);
        board.add(fieldp2);
        board.add(handp2);
*/
        this.add(board);
        SwingUtilities.updateComponentTreeUI(this);

    }


    /**
     * Metodo
     * <p>
     * Prepara la rappresentazione grafica della mano
     *
     * @param player Giocatore proprietario della mano da rappresentare
     * @param panel  JPanel corrispondente allo spazio per la mano
     */
    private void drawHand(Player player, JPanel panel) {
        panel.removeAll();
        List<Card> lst = player.getHand();
        if(player.equals(CardGame.instance.getCurrentPlayer())) {
            for (Card card : lst) {
                /*System.out.println("Ho disegnato " + card.name());*/
                panel.add(new PanelCard(card));
            }
        }else{
            for (Card card : lst) {
                System.out.println("Ho disegnato " + card.name());
                panel.add(new PanelCard(new PanelBackCard()));
            }
        }
    }


    /**
     * Metodo
     * <p>
     * Prepara il campo da gioco per il giocatore
     *
     * @param player Giocatore di cui voglio graficare le creature
     * @param panel  JPanel in cui voglio inserire la grafica delle creature
     */
    private void drawField(Player player, JPanel panel) {
        panel.removeAll();
        List<DecoratedCreature> lst = player.getCreatures();
        for (DecoratedCreature creature : lst) {
            System.out.println("Ho disegnato la creatura: " + creature.name());
            panel.add(new PanelCard(creature));
        }
    }


    /**
     * Metodo
     * <p>
     * Genera le mani dei due giocatori e i due fronti di creature dei rispettivi giocatori.
     * Al termine di ciò disegna tutto nella board
     */
    public void draw() {
        this.drawHand(p1, handp1);
        this.drawHand(p2, handp2);
        this.drawField(p1, fieldp1);
        this.drawField(p2, fieldp2);

        SwingUtilities.updateComponentTreeUI(this.board);
    }

}












