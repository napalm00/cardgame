package Cardgame.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe
 * <p>
 * Gui inizale del progetto. Equivalente del main per la GUI
 */
public class GUIMenu implements Runnable {

    /**
     * Costruttore
     *
     * Costruttore di default
     */
    public GUIMenu() {
    }

    /**
     * Metodo
     *
     * COdice eseguito al primo avvio della gui dopo la creazione della stessa
     */
    public void run() {

        JPanel pnlMain = new JPanel(new GridLayout(4, 1));

        //Interfaccia grafica
        final JFrame frmMenu = new JFrame("MAGIC");
        frmMenu.setPreferredSize(new Dimension(300,300));

        JButton btnPlay = new JButton("PLAY");
        btnPlay.setPreferredSize(new Dimension(300, 100));
        pnlMain.add(btnPlay);


        JButton btnCards = new JButton("CARDS");
        pnlMain.add(btnCards);
        JButton btnDecks = new JButton("DECKS");
        pnlMain.add(btnDecks);
        JButton btnExit = new JButton("EXIT");
        pnlMain.add(btnExit);

        frmMenu.add(pnlMain);


        frmMenu.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frmMenu.pack();
        frmMenu.setVisible(true);


        ActionListener lstnPlay = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmMenu.setVisible(false);
                GUISetup setup = new GUISetup();
                Thread t = new Thread(setup);
                {
                    t.start();
                }
            }
        };
        btnPlay.addActionListener(lstnPlay);


        ActionListener lstnDecks = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmMenu.dispose();
                GUIdeck deck = new GUIdeck();
                Thread t = new Thread(deck);
                {
                    t.start();
                }
            }
        };
        btnDecks.addActionListener(lstnDecks);

        ActionListener lstnCards = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIcards cards = new GUIcards();
            }
        };
        btnCards.addActionListener(lstnCards);

        ActionListener lstnExit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        btnExit.addActionListener(lstnExit);
    }
}
