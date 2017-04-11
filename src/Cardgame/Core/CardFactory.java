package Cardgame.Core;

import Cardgame.Core.Interface.Card;
import Cardgame.Core.Interface.CardConstructor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class CardFactory {
    private static final Map<String, CardConstructor> map = new HashMap<>();
    private static final CardFactory instance = new CardFactory();


    private CardFactory() {
        URL url = getClass().getClassLoader().getResource("Cardgame/cards");
        if (url.toString().startsWith("jar:")) loadCardsFromJar(url);
        else loadCardsFromDir(url);
    }

    public static Card construct(String s) {

        CardConstructor c = map.get(s);
        if (c == null) {
            throw new RuntimeException("card " + s + " did not register itsef");
        }

        return c.create();
    }

    public static int size() {
        return map.size();
    }

    public static void register(String s, CardConstructor c) {
        map.put(s, c);
        //System.out.println("registering "+s);
    }

    public static Set<String> known_cards() {
        return map.keySet();
    }

    private void loadCardsFromJar(URL url) {
        //decoding jar
        String[] jarparts = url.toString().substring("jar:".length()).split("!");

        URL jarurl;
        try {
            jarurl = new URL(jarparts[0]);
        } catch (MalformedURLException ex) {
            throw new RuntimeException("cannot find jar " + jarparts[0]);
        }

        ZipInputStream zip;
        try {
            zip = new ZipInputStream(jarurl.openStream());
        } catch (IOException ex) {
            throw new RuntimeException("cannot read cards directory from jar");
        }

        ZipEntry entry = null;
        try {
            entry = zip.getNextEntry();
        } catch (IOException ex) {
        }
        while (entry != null) {
            //new entry
            String entryName = entry.getName();
            if (entryName.startsWith("Cardgame/cards/")) {
                String fileName = entryName.substring("Cardgame/cards/".length());

                //look for all top level classe in package Cardgame.cards
                //eliminate subdirectories and inner-classes
                if (fileName.endsWith(".class") && !fileName.contains("/") && !fileName.contains("$")) {
                    loadClass(fileName);
                }
            }

            //get new entry
            try {
                entry = zip.getNextEntry();
            } catch (IOException ex) {
            }
        }

    }

    private void loadCardsFromDir(URL url) {
        Scanner classes;
        try {
            classes = new Scanner(url.openStream());
        } catch (IOException ex) {
            throw new RuntimeException("cannot read cards directory");
        }

        while (classes.hasNext()) {
            String fileName = classes.next();

            //look for all top level classe in package Cardgame.cards
            if (fileName.endsWith(".class") && !fileName.contains("$")) {
                loadClass(fileName);
            }
        }
    }

    private void loadClass(String fileName) {
        boolean loaded = true;
        String classname = fileName.substring(0, fileName.length() - ".class".length());
        try {
            Class.forName("Cardgame.cards." + classname);
        } catch (ClassNotFoundException ex) {
            loaded = false;
            //throw new RuntimeException("class " + classname + " not found");
        }
        if (loaded) System.out.println(fileName + " Loaded");
    }


}
