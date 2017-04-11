package Cardgame.GUI;

import Cardgame.Core.CardGame;
import Cardgame.GUI.panel.PanelGame;
import Cardgame.GUI.panel.PanelPlayers;
import Cardgame.GUI.panel.PanelStack;

import javax.swing.*;
import java.awt.*;

/**
 * GUIBoard
 *
 * Gui che visualizza il campo di gioco e delle informazioni sulla partita
 */
public class GUIBoard extends Component implements Runnable {
    private PanelGame game_field;
    private PanelPlayers players_field;
    private PanelStack stack_effect;
    private JFrame frmBoard;
    private JPanel pnlBoard;


    /**
     * Costruttore
     * <p>
     * Crea la gui e setta le dimensioni di tutti i componenti
     */

    public GUIBoard() {
        this.game_field = new PanelGame(CardGame.instance.getCurrentPlayer(), CardGame.instance.getCurrentAdversary());
        this.players_field = new PanelPlayers(CardGame.instance.getCurrentPlayer(), CardGame.instance.getCurrentAdversary());
        this.stack_effect = new PanelStack();

        this.game_field.setPreferredSize(new Dimension(1200, 1000));
        this.players_field.setMaximumSize(new Dimension(100, 800));
        this.stack_effect.setMaximumSize(new Dimension(100, 800));

        this.frmBoard = new JFrame("Board");
        this.pnlBoard = new JPanel(new FlowLayout());
        this.pnlBoard.setPreferredSize(new Dimension(1400, 1000));
        this.frmBoard.setPreferredSize(new Dimension(1600, 1000));
        this.frmBoard.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pnlBoard.add(this.players_field);
        this.pnlBoard.add(this.game_field);
        this.pnlBoard.add(this.stack_effect);
        JScrollPane scrollPnlBoard = new JScrollPane(pnlBoard, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.frmBoard.add(scrollPnlBoard);
        this.frmBoard.pack();
        this.frmBoard.setVisible(true);

    }

    /**
     * Metodo
     *
     * Disegna i componenti della GUI
     */
    @Override
    public void run() {
        game_field.draw();
        players_field.draw();
        stack_effect.draw();
    }
}
