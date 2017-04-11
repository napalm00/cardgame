package Cardgame.GUI;

import Cardgame.Core.CardFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe
 * <p>
 * Gui che visualizza le carte implementate
 */
//graphic window that displays implemented cards
public class GUIcards {

    private final JFrame frmCards;

    /**
     * Costruttore
     * <p>
     * Setta la struttura del layout di base del sistema di visualizzazione di carte
     */
    public GUIcards() {
        String[] card = new String[CardFactory.known_cards().size()];
        frmCards = new JFrame("Cards");
        JPanel panel = new JPanel();
        frmCards.setContentPane(panel);
        JButton btnOk = new JButton("OK");

        JList showCards = new JList(card);
        showCards.setVisibleRowCount(10);
        showCards.setFixedCellHeight(20);
        showCards.setFixedCellWidth(140);
        showCards.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frmCards.getContentPane().add(showCards);
        frmCards.getContentPane().add(btnOk, BorderLayout.SOUTH);
        frmCards.getContentPane().add(new JScrollPane());
        frmCards.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frmCards.setSize(showCards.getSize());
        frmCards.pack();
        frmCards.setVisible(true);
        int i = 0;

        for (String s : CardFactory.known_cards()) {
            card[i] = s;
            i++;
        }

        ActionListener lstnOk = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmCards.dispose();
            }
        };
        btnOk.addActionListener(lstnOk);
    }

}
