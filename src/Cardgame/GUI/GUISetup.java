package Cardgame.GUI;

import Cardgame.Core.CardGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe
 * <p>
 * Classe che gestisce le funzioni di {@link CardGame} attraverso un {@link GUIMenu} nel main
 */
public class GUISetup implements Runnable {

    final JFrame frame = new JFrame("Setup");
    private JButton startButton;
    private JButton cancelButton;
    private JPanel panel1;
    private JTextField Giocatore1;
    private JTextField Giocatore2;
    private JTextField Deck1;
    private JTextField Deck2;

    /**
     * Costruttore
     * <p>
     * Costruttore di default
     */
    public GUISetup() {
    }

    /**
     * Metodo
     * @return Ritorna il nome del giocatore 1
     */
    public String getGiocatore1() {
        return Giocatore1.getText();
    }

    /**
     * Metodo
     * @return Ritorna il nome del giocatore 2
     */
    public String getGiocatore2() {
        return Giocatore2.getText();
    }

    /**
     * Metodo
     * @return Ritorna il path relativo del mazzo del 1 giocatore
     */
    public String getDeck1() {
        return Deck1.getText();
    }

    /**
     * Metodo
     * @return Ritorna il path relativo del mazzo del 2 giocatore
     */
    public String getDeck2() {
        return Deck2.getText();
    }

    /**
     * Metodo
     *
     * Metodo che viene lanciato all'avvio dell'interfaccia grafica
     */
    public void run() {

        /*Inizializzazione dei bottoni*/

        frame.setPreferredSize(new Dimension(300, 300));
        startButton = new JButton("Start");
        cancelButton = new JButton("Cancel");
        panel1 = new JPanel(new GridLayout(3, 2));
        Giocatore1 = new JTextField("giocatore1");
        Giocatore2 = new JTextField("giocatore2");
        Deck1 = new JTextField("deck3.txt");
        Deck2 = new JTextField("deck2.txt");

        /*Al click nel JTextField, il testo scompare*/

        Deck1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Deck1.setText("");
            }
        });
        Deck2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Deck2.setText("");
            }
        });
        Giocatore1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Giocatore1.setText("");
            }
        });
        Giocatore2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Giocatore2.setText("");
            }
        });

        /*Bottoni setup e cancel*/

        ActionListener setupListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Aggiungo le informazioni ai campi di CardGame*/
                CardGame.instance.setup(getGiocatore1(), getGiocatore2(), getDeck1(), getDeck2());
                class beginning extends Thread {
                    public void run() {
                        frame.setVisible(false);
                        CardGame.instance.run();
                    }
                }
                new beginning().start();
            }
        };
        startButton.addActionListener(setupListener);
        ActionListener cancelListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                GUIMenu menu = new GUIMenu();
                Thread t = new Thread(menu);
                {
                    t.start();
                }
            }
        };

        cancelButton.addActionListener(cancelListener);

        /*Aggiunta componenti*/

        panel1.add(Giocatore1);
        panel1.add(Giocatore2);
        panel1.add(Deck1);
        panel1.add(Deck2);
        panel1.add(startButton);
        panel1.add(cancelButton);
        frame.add(panel1);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
