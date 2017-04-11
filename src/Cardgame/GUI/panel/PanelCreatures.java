package Cardgame.GUI.panel;

import Cardgame.Core.DecoratedCreature;
import Cardgame.GUI.IntRefreshableGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

//
public class PanelCreatures extends JPanel implements IntRefreshableGUI {

    private JPanel container;
    private JPanel listBoxes;
    private JPanel buttons;
    private JPanel labels;
    private JScrollPane scrollAvailable;
    private JScrollPane scrollSelected;
    private JLabel lblAv;
    private JLabel lblSel;
    private JList available;
    private JList selected;
    private JButton addEntry;
    private JButton rmEntry;

    private class oggetto {
        DecoratedCreature d;
        Integer index;
    }

    private ArrayList<oggetto> disponibili;
    private oggetto[] arrDisponibili = new oggetto[0];

    private ArrayList<oggetto> selezionati;
    private oggetto[] arrSelezionati = new oggetto[0];

    private String input = new String();

    public PanelCreatures() {
        disponibili = new ArrayList<>();
        selezionati = new ArrayList<>();
        draw();
    }

    public PanelCreatures(ArrayList<DecoratedCreature> lst) {
        disponibili = new ArrayList<>();
        selezionati = new ArrayList<>();
        this.setList(lst);
    }

    public void setList(ArrayList<DecoratedCreature> lst){
        int i=0;
        for(DecoratedCreature c : lst){
            i++;
            oggetto o = new oggetto();
            o.d = c;
            o.index = i;
            this.disponibili.add(o);
        }
        this.arrDisponibili = new oggetto[this.disponibili.size()];
        System.out.println("La dimensione della lista è: "+disponibili.size());
        this.arrDisponibili = this.disponibili.toArray(this.arrDisponibili);
        this.draw();
    }

    public String getResult(){
        for(oggetto d:selezionati){
            input = input + d.index.toString() + " ";
        }
        return input;
    }

    private void drawAvailable(){
        this.available = new JList(this.arrDisponibili);
        this.available.setPreferredSize(new Dimension(200,400));
        this.scrollAvailable = new JScrollPane(this.available);
        this.scrollAvailable.setPreferredSize(new Dimension(200,400));
        this.listBoxes.add(this.scrollAvailable);
    }

    private void drawSelected(){
        this.selected = new JList(this.arrSelezionati);
        this.selected.setPreferredSize(new Dimension(200,400));
        this.scrollSelected = new JScrollPane(this.selected);
        this.scrollSelected.setPreferredSize(new Dimension(200,400));
        this.listBoxes.add(this.scrollSelected);
    }

    private void drawLists(){
        this.listBoxes = new JPanel(new GridLayout(1,2));
        this.listBoxes.setPreferredSize(new Dimension(900,700));

        this.drawAvailable();
        this.drawSelected();

        this.container.add(this.listBoxes);
    }

    private void drawLabels(){
        //this.labels.removeAll();
        this.labels = new JPanel(new GridLayout(1,2));
        this.lblAv = new JLabel("Disponibili");
        this.lblSel = new JLabel("Selezionati");

        this.labels.add(lblAv);
        this.labels.add(lblSel);

        this.container.add(labels);
    }

    private void drawButtons(){
        this.buttons = new JPanel(new GridLayout(1,2));
        this.buttons.setPreferredSize(new Dimension(800,100));

        this.addEntry = new JButton("Aggiungi");
        this.rmEntry = new JButton("Rimuovi");

        addEntry.addActionListener(lstAdd);
        rmEntry.addActionListener(lstRemove);

        this.buttons.add(addEntry);
        this.buttons.add(rmEntry);

        this.container.add(buttons);
    }

    ActionListener lstAdd = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            selezionati.add(disponibili.get(available.getSelectedIndex()));
            arrSelezionati = new oggetto[selezionati.size()];
            arrSelezionati = selezionati.toArray(arrSelezionati);
            drawLists();
        }
    };

    ActionListener lstRemove = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            selezionati.remove(disponibili.get(available.getSelectedIndex()));
            arrSelezionati = new oggetto[selezionati.size()];
            arrSelezionati = selezionati.toArray(arrSelezionati);
            drawLists();
        }
    };




    @Override
    public void draw() {
        container = new JPanel(new FlowLayout());
        this.container.setPreferredSize(new Dimension(1000,800));
        this.drawLabels();
        this.drawLists();
        this.drawButtons();
        this.add(container);
    }
}
