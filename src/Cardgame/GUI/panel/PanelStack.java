package Cardgame.GUI.panel;

import Cardgame.Core.CardGame;
import Cardgame.Core.CardStack;
import Cardgame.Core.Interface.Effect;
import Cardgame.GUI.IntRefreshableGUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * PanelStack
 * <p>
 * Pannello che conterrà una rappresentazione grafica dello stack e delle carte presenti all'interno di esso
 */
public class PanelStack extends JPanel implements IntRefreshableGUI {
    private JLabel labelStack;
    private JList listStack;
    private JScrollPane scrollPane;
    private ArrayList<String> s;
    private String[] as;

    /**
     * Costruttore
     * <p>
     * Costruttore del panel, in cui sono settate dimensioni e la struttura del progetto
     */
    public PanelStack() {
        if(CardGame.instance.getStack()!=null)
            this.draw();

        else {
            System.out.println("Stack vuotoh");
            this.draw();
        }

    }

    /**
     * getLAbelStack
     * <p>
     * Funzione per avere il label corrispondente allo stack
     *
     * @return JLabel rappresentazione dello stack
     */
    public JLabel getLabelStack() {
        return labelStack;
    }

    /**
     * Setter per labelStack
     *
     * @param labelStack Label che voglio settare
     */
    public void setLabelStack(JLabel labelStack) {
        this.labelStack = labelStack;
    }


    public JList getListStack() {
        return listStack;
    }

    public void setListStack(JList listStack) {
        this.listStack = listStack;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    /**
     * Metodo
     * Prende tutti gli effetti in stack e li inserisce in un campo interno (s) della classe
     */
    public void addToArray(){
        s = new ArrayList<String>();
        for(Effect eff : CardGame.instance.getStack()){
            this.s.add(eff.name());
        }
        this.as = new String[this.s.size()];
        if(s.size()>0)
            this.as = s.toArray(as);
    }

    /**
     * Metodo
     *
     * Traccia le misure dello stack e lo struttura all'interno della gui
     */
    public void drawStack(){
        this.addToArray();
        this.listStack = new JList(this.as);
        this.scrollPane = new JScrollPane(listStack);
        listStack.setPreferredSize(new Dimension(100, 780));
        this.scrollPane.setPreferredSize(new Dimension(100,780));
        this.add(scrollPane);
    }

    /**
     * Metodo
     *
     * Gestisce i label dello Stack posizionandoli correttamente nella gui
     */
    public void drawLabel(){
        this.labelStack = new JLabel("Stack");
        labelStack.setPreferredSize(new Dimension(100, 20));
        this.add(labelStack);
    }

    /**
     * Metodo
     *
     * Se presente, cancella la precetende gui e la ridisegna con i nuovi dati
     */
    @Override
    public void draw() {
        this.removeAll();
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(100, 800));
        this.drawLabel();
        this.drawStack();
    }
}
