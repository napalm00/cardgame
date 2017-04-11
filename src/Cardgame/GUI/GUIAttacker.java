package Cardgame.GUI;

import Cardgame.Core.CardGame;
import Cardgame.Core.DecoratedCreature;
import Cardgame.GUI.panel.PanelCard;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * GuiAttacker
 *
 * Gui per la scelta delle creature attaccanti.
 * Se vengono scelte creature attaccanti viene successivamente lanciata una {@link GUIDefender}
 * aaltrimenti viene ridato il controllo al gestore turni
 */
// Gui per la scielta degli attaccanti
public class GUIAttacker extends Component implements Runnable {

    private JFrame frmattacker;
    private JPanel atkList;
    private ArrayList<DecoratedCreature> lst;
    private JButton btnOk;
    private JPanel container;
    String input;
    int gridLength;

    /**
     * Costruttore
     *
     * @param lst lista delle creature che possono essere scelte per attaccare
     */
    public GUIAttacker(ArrayList<DecoratedCreature> lst){

        this.lst = new ArrayList<>();
        this.lst.addAll(lst);
        this.container = new JPanel(new FlowLayout());
        atkList = new JPanel(new GridLayout(1, 0));
        this.frmattacker = new JFrame(CardGame.instance.getCurrentPlayer().name() + " select Attackers");
        btnOk = new JButton("OK");
        input = new String();



    }

    /**
     * Metodo
     *
     * Funzione chiamata quando viene lanciata la GUiAttacker e esegue il codice di
     * avvio della grafica
     */
    @Override
    public void run() {

        gridLength=0;

        for (DecoratedCreature c : lst) {

            String temp = new String(c.name() + " - " + c.power() + " / " + c.toughness());
            atkList.add(new JCheckBox(temp));
            gridLength++;
        }






        ActionListener lstnOk = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i=0;

                while(i<gridLength) {

                    if (atkList.getComponent(i) instanceof JCheckBox) {
                        JCheckBox temp = (JCheckBox) atkList.getComponent(i);
                        if (temp.isSelected())
                            input = input + (i + 1) + " ";
                        i++;

                    }
                    if(input.isEmpty())
                        input = "0";
                }

                frmattacker.dispose();

            }

        };

        btnOk.addActionListener(lstnOk);

        JScrollPane scrollatk = new JScrollPane(atkList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.container.add(scrollatk);
        this.container.add(btnOk);

        this.frmattacker.add(container);
        frmattacker.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frmattacker.setPreferredSize(new Dimension(150, 300));
        frmattacker.setVisible(true);
        frmattacker.pack();

    }



    public String getInput(){ return input;}
}
