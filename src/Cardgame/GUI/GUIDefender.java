package Cardgame.GUI;

import Cardgame.Core.CardGame;
import Cardgame.Core.DecoratedCreature;
import Cardgame.Core.Interface.Card;
import Cardgame.GUI.panel.PanelDefenders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Gui per la scelta delle creature in difesa

/**
 * Classe
 * <p>
 * Classe per la creazione della gui per la selezione dei difensori nel caso che uno dei due giocatori abbia
 * attaccato e il giocatore "attaccato" abbia creature con cui difendersi
 */
public class GUIDefender extends Component implements Runnable {

    private JFrame frmDefender;
    ArrayList<DecoratedCreature> lst;
    DecoratedCreature atker;
    JPanel defList;
    JButton btnOk;
    JButton btnPass;
    int gridLength;
    String input;
    private JPanel container;



    /**
     * Costruttore
     * Crea la finestra in cui vengono rappresentate le creature con cui è possibile difendere.
     * In particolare il costruttore setta il layout di base
     */
    public GUIDefender(ArrayList<DecoratedCreature> lstDef,DecoratedCreature atker){

        lst = new ArrayList<>(lstDef);
        this.atker = atker;
        frmDefender = new JFrame(CardGame.instance.getCurrentAdversary().name() + " choose creatures defending from " + atker);
        defList = new JPanel(new GridLayout(1,0));
        btnOk = new JButton("OK");
        btnPass = new JButton("Don't Defend");
        input = new String();
        container = new JPanel();


        gridLength=0;

        for (DecoratedCreature c : lst) {

            String temp = new String(c.name() + " - " + c.power() + " / " + c.toughness());
            defList.add(new JRadioButton(temp));
            gridLength++;
        }

        container.add(defList);
        container.add(btnOk);
        container.add(btnPass);

        frmDefender.setPreferredSize(new Dimension(150, 300));
        frmDefender.setVisible(true);
        frmDefender.add(container);
        frmDefender.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    }

    @Override
    public void run() {

        ActionListener lstnOk = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i = 0;

                for (i = 0; i < gridLength; i++) {
                    if (defList.getComponent(i) instanceof JRadioButton) {
                        JRadioButton temp = (JRadioButton) defList.getComponent(i);
                        if (temp.isSelected())
                            input = "" + (i + 1);

                    }
                }

                frmDefender.dispose();
            }

        };

        btnOk.addActionListener(lstnOk);

        ActionListener lsntPass = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                input = "0";

                frmDefender.dispose();

            }
        };

    }

    public String getInput(){ return input;}

}
