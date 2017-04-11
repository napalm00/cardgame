package Cardgame.GUI.panel;

import Cardgame.Core.DecoratedCreature;
import Cardgame.Core.Interface.Named;
import Cardgame.GUI.IntRefreshableGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <h1>PanelCard</h1>
 * Panel per la visualizzazione della singola carta.
 * In base al tipo di carta può cambiare la visualizzazione
 */
public class PanelCard extends Panel implements IntRefreshableGUI {
    private Named carta;
    private JLabel attack;
    private JLabel defence;

    private JPanel cartaView;
    private JPanel statView;

    private String cardName;
    private ImageIcon image;
    private Image img;
    private JLabel ImageLabel;


    /**
     * Costruttore.
     *
     * Setta i valori a null che verranno poi cambiati se necessario dal draw
     *
     * @param carta (required) Carta o istanza di creatura di cui voglio avere l'immagine
     */
    public PanelCard(Named carta) {
        this.attack = null;
        this.defence = null;

        this.cartaView = null;
        this.statView = null;

        this.carta = carta;
        this.cardName = carta.name();
        this.draw();
    }

    /**
     *
     * Disegna la carta in base al tipo.
     * Se è una carta visualizza solo l'immagine.
     * Se è una creatura visualizza anche l'attacco e la difesa effettiva
     */
    @Override
    public void draw() {

        this.cartaView = new JPanel();
        this.cartaView.setPreferredSize(new Dimension(120, 194));
        this.image = new ImageIcon("img/" + this.getImageName());




        img = image.getImage();
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img, 58, 0, 95, 173, null);
        image = new ImageIcon(bi);

        //this.cartaView.setPreferredSize(new Dimension(95,178));
        this.ImageLabel = new JLabel("", this.image, JLabel.CENTER);
        this.cartaView.add(ImageLabel);
        this.add(cartaView);
    }

    public ImageIcon getIcon(){return image;}

    /**
     * getImageName
     * <p>
     * Funzione che costruisce il nome dell'immagine riferita alla carta
     *
     * @return String ritorna il nome dell'immagine legata alla carta
     */
    // Outputta il nome del file immagine
    private String getImageName() {
        /*System.out.println(this.cardName);*/
        String[] a = this.cardName.split(" ");
        String out = "";
        for (String s : a) {
            out += ("_" + s);
        }
        return "IMG" + out + ".jpg";
    }

}
