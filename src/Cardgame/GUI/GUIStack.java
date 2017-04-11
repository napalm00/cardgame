package Cardgame.GUI;

import Cardgame.Core.CardGame;
import Cardgame.Core.CardStack;
import Cardgame.Core.Interface.Effect;
import Cardgame.Core.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * GuiAttacker
 *
 * Gui per la scelta delle creature attaccanti.
 * Se vengono scelte creature attaccanti viene successivamente lanciata una {@link GUIDefender}
 * aaltrimenti viene ridato il controllo al gestore turni
 */
// Gui per la scielta degli attaccanti
public class GUIStack extends Component implements Runnable {

    private JFrame frmattacker;
    private ButtonGroup atkList;
    private ArrayList<Effect> lst;
    private JButton btnOk;
    private JPanel container;
    private JButton pass = new JButton("Pass");
    private Player owner = new Player();
    private String name = new String();
    String input;
    int gridLength;
    private JPanel bottoni = new JPanel();



    public GUIStack(Player p, String n){
        this.owner = p;
        this.name = n;
        this.lst = new ArrayList<>();
        CardStack stack = CardGame.instance.getStack();
        for(Effect e : stack){
            lst.add(e);
        }
        this.container = new JPanel(new FlowLayout());
        atkList = new ButtonGroup();
        this.frmattacker = new JFrame(owner.name() + " select target for " + name);
        btnOk = new JButton("Ok");
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

        for (Effect c : lst) {
            /*JPanel temp = new JPanel(new GridLayout(1,0));
            temp.setPreferredSize(new Dimension(100,250));
            temp.add(new PanelCard(c));
            temp.add(new JCheckBox(c.name()));*/
            JRadioButton b = new JRadioButton(c.name());
            atkList.add(b);
            bottoni.add(b);
            gridLength++;
        }






        ActionListener lstnOk = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i=0;


                for (Enumeration<AbstractButton> buttons = atkList.getElements(); buttons.hasMoreElements();) {
                    AbstractButton button = buttons.nextElement();
                    i++;
                    if (button.isSelected()) {
                        input = "" + i;
                    }else
                        input = "0";
                }

                frmattacker.dispose();
            }

        };

        ActionListener lstnPass = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input = "0";
                frmattacker.dispose();
            }
        };

        btnOk.addActionListener(lstnOk);
        pass.addActionListener(lstnPass);
        JScrollPane scrollatk = new JScrollPane(bottoni);
        this.container.add(scrollatk);
        this.container.add(pass);
        this.container.add(btnOk);

        this.frmattacker.add(container);
        frmattacker.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frmattacker.setPreferredSize(new Dimension(400, 100));
        frmattacker.setVisible(true);
        frmattacker.pack();

    }



    public String getInput(){ return input;}
}

