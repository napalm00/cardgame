package Cardgame.GUI;


import Cardgame.Core.CardGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe
 *
 * Genera e gestisce la finestra di fine partita
 */
public class GUIEnd extends Component implements Runnable{
    private JFrame frmEnd;
    private JPanel panel;
    private JLabel label;
    private JButton yes;
    private JButton no;

    public GUIEnd(){
        frmEnd=new JFrame("END GAME");
        panel=new JPanel(new GridLayout(2,2));
        label=new JLabel("Il gioco è finito: Il vincitore è "+ CardGame.instance.getCurrentPlayer().name()+" VUOI RIGIOCARE?");
        yes=new JButton("SI");
        no=new JButton("NO");
    }

    /**
     * Metodo
     *
     * Gestisce i bottoni della gui di fine partita e il tread corrispondente
     */
    @Override
    public void run(){
        ActionListener chooseYes = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frmEnd.dispose();
                GUIMenu menu=new GUIMenu();
                Thread newgame= new Thread(menu);
                {
                    newgame.start();
                }
            }
        };

        ActionListener chooseNo = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        };

        yes.addActionListener(chooseYes);
        no.addActionListener(chooseNo);
        panel.add(yes, BorderLayout.EAST);
        frmEnd.add(no, BorderLayout.WEST);
        frmEnd.add(label, BorderLayout.NORTH);
        frmEnd.add(panel);

        frmEnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmEnd.pack();
        frmEnd.setVisible(true);
    }

}
