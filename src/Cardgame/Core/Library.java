package Cardgame.Core;

import Cardgame.Core.Interface.Card;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Library {
    final private Random rnd = new Random();
    final private Player owner;
    private ArrayList<Card> cards = new ArrayList<Card>();

    public Library(Player p) {
        owner = p;
    }

    public void shuffle() {
        final int end = cards.size();
        for (int i = 0; i != end - 1; ++i) {
            int idx = i + rnd.nextInt(end - i);
            Card tmp = cards.get(idx);
            cards.set(idx, cards.get(i));
            cards.set(i, tmp);
        }
    }

    public void add(Card c) {
        cards.add(c);
    }

    public void add(Iterator<Card> deck) {
        while (deck.hasNext())
            cards.add(deck.next());
    }

    Card draw() {
        if (cards.isEmpty())
            owner.lose("out of cards");

        return cards.remove(cards.size() - 1);
    }

    public int cardsNumber() {
        return cards.size();
    }
}
