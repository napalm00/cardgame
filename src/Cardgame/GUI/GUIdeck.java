package Cardgame.GUI;

import Cardgame.Core.CardFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * TODO Mi rifiuto di commentarla fintanto che non viene rimessa a posto.
 * Una classe con un unico metodo che fa tutto (e non è il costruttore) non è
 * programmazione ad oggetti... è imperativa e basta
 */
public class GUIdeck extends Component implements Runnable {


    JScrollPane scrollProva;
    JScrollPane scrollPane;
    FileReader fileReader;
    BufferedReader bufferedReader;
    FileOutputStream fout;

    // TODO seriamente, perchè due oggetti con lo stesso contenuto? O almeno appare così nei commenti
    ArrayList<String> as; //selected file
    ArrayList<String> all; //all cards
    String[] selected_file; //selected file
    String[] fa; //all cards

    Boolean firstSfoglia = false;
    private JList list1;
    private JList list2;

    public GUIdeck() {
    }

    @Override
    public void run() {
        //TODO spostare della roba nel costruttore
        final JPanel pnlButton = new JPanel(new GridLayout(0, 2));
        final JPanel pnlLists = new JPanel();
        pnlLists.setLayout(new GridLayout(0, 2));
        final JFrame frmDeck = new JFrame("Deck"); //frame
        final JFileChooser fcSfoglia = new JFileChooser("Sfoglia..."); //file opener
        final JButton btnRemove = new JButton("Remove");
        final JButton btnAdd = new JButton("Add");
        JButton btnOk = new JButton("Ok"); //bottone "Ok"
        JButton btnSfoglia = new JButton("Sfoglia"); //bottone "Sfoglia"
        btnAdd.setEnabled(false);
        btnRemove.setEnabled(false);

        btnOk.setMaximumSize(new Dimension(100, 100));
        btnSfoglia.setMaximumSize(new Dimension(100, 100));
        pnlButton.add(btnOk, BorderLayout.EAST); //Posizionamento del bottone
        pnlButton.add(btnSfoglia, BorderLayout.WEST); //Posizionamento del file opener
        pnlButton.add(btnAdd);
        pnlButton.add(btnRemove);
        frmDeck.setSize(600, 500); //dimensioni del frame
        frmDeck.getContentPane().add(pnlButton, BorderLayout.SOUTH);


        //TODO Fine roba da spostare nel costruttore. Se ne volete spostare di più fate vobis
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files", "txt"); //nuovo filtro per i files
        fcSfoglia.setFileFilter(filter); //setta il file opener al filtro

        ActionListener sfoglia = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fcSfoglia.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this file: " +
                            fcSfoglia.getSelectedFile().getName());
                }
                fileReader = null; //filereader per leggere le carte dal file deck
                try {
                    fileReader = new FileReader(fcSfoglia.getSelectedFile().getPath());
                } catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                }

                bufferedReader = new BufferedReader(fileReader);
                String s = null;
                as = new ArrayList<String>();
                while (true) {
                    try {
                        s = bufferedReader.readLine();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    if (s == null)
                        break;
                    as.add(s);
                }
                all = new ArrayList();
                for (String a : CardFactory.known_cards()) {
                    all.add(a);
                }
                selected_file = new String[as.size()]; //deck
                fa = new String[all.size()];
                Collections.sort(as);
                Collections.sort(all);
                selected_file = as.toArray(selected_file);
                fa = all.toArray(fa);
                list1 = new JList(selected_file);
                list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                list1.setLayoutOrientation(JList.VERTICAL);
                list2 = new JList(fa);
                list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                list2.setLayoutOrientation(JList.VERTICAL);
                scrollPane = new JScrollPane(list1);
                scrollProva = new JScrollPane(list2); //Sinistra
                if (!firstSfoglia) {
                    pnlLists.add(scrollProva);
                    pnlLists.add(scrollPane);
                    frmDeck.getContentPane().add(pnlLists);
                    firstSfoglia = true;
                }
                btnAdd.setEnabled(true);
                btnRemove.setEnabled(true);
                SwingUtilities.updateComponentTreeUI(frmDeck);
                try {
                    fileReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    bufferedReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };


        ActionListener lstnOk = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmDeck.dispose();
                Thread t = new Thread(new GUImenu());
                {
                    t.start();
                }
            }
        };
        //
        ActionListener lstnRemove = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(as.toString());
                Object selected = list1.getSelectedValue();
                System.out.println(selected);
                as.remove(selected);
                as.removeAll(Arrays.asList("", null));
                Collections.sort(as);
                System.out.println(as.toString());
                selected_file = as.toArray(selected_file);
                String testo = "\n";
                try {
                    fout = new FileOutputStream(fcSfoglia.getSelectedFile().getPath());
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                for (int i = 0; i < as.size(); i++)
                    testo += selected_file[i] + "\n";
                try {
                    fout.write(testo.getBytes());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    fout.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(frmDeck);

            }
        };

        ActionListener lstnAdd = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(as.toString());
                Object selected = list2.getSelectedValue();
                System.out.println(selected);
                as.add((String) selected);
                as.removeAll(Arrays.asList("", null));
                Collections.sort(as);
                System.out.println(as.toString());
                selected_file = as.toArray(selected_file);
                String testo = "\n";
                try {
                    fout = new FileOutputStream(fcSfoglia.getSelectedFile().getPath());
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                for (int i = 0; i < as.size(); i++)
                    testo += selected_file[i] + "\n";
                try {
                    fout.write(testo.getBytes());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    fout.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(frmDeck);
            }
        };
        btnAdd.addActionListener(lstnAdd);
        btnRemove.addActionListener(lstnRemove);
        btnOk.addActionListener(lstnOk);
        btnSfoglia.addActionListener(sfoglia);
        frmDeck.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frmDeck.setVisible(true); //setting della visibilità

    }
}
