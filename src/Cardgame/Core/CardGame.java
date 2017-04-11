package Cardgame.Core;

import Cardgame.Core.Interface.Card;
import Cardgame.Core.Interface.TurnManager;
import Cardgame.GUI.GUIBoard;
import Cardgame.GUI.GUIMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;


/*
Signleton class maintaining global game properties.
Handles: 
 - Players
 - Turns
 - Stack
 - Triggers
*/
public class CardGame {


    //Signleton and instance access
    public static final CardGame instance = new CardGame();
    // Player and turn management
    private final Player[] Players = new Player[2];
    private final Deque<TurnManager> turn_manager_stack = new ArrayDeque<TurnManager>();
    // Stack access
    private CardStack stack = new CardStack();
    //Trigger access
    private Triggers triggers = new Triggers();
    //IO resources  to be dropped in final version
    private Scanner reader = new Scanner(System.in);
    private GUIBoard board;

    //game setup
    private CardGame() {
        turn_manager_stack.push(new DefaultTurnManager(Players));

        Players[0] = new Player();
        Players[0].setName("Player 1");


        Players[1] = new Player();
        Players[1].setName("Player 2");
    }

    public static void main(String[] args) {

        try {
            System.out.println("Known cards: " + CardFactory.size());
            for (String s : CardFactory.known_cards()) {
                System.out.println(s);
            }



            /*
            //create decks
            ArrayList<Card> deck = new ArrayList<Card>();
            //for (int i=0; i!=5; ++i) deck.add(CardFactory.construct("Deflection"));
            //for (int i=0; i!=5; ++i) deck.add(CardFactory.construct("Afflict"));
            //for (int i=0; i!=5; ++i) deck.add(CardFactory.construct("Aggressive Urge"));
            for (int i=0; i!=5; ++i) deck.add(CardFactory.construct("Benevolent Ancestor"));
            //for (int i=0; i!=5; ++i) deck.add(CardFactory.construct("Day of Judgment"));
            //for (int i=0; i!=5; ++i) deck.add(CardFactory.construct("Cancel"));
            //for (int i=0; i!=5; ++i) deck.add(CardFactory.construct("Boiling Earth"));
            for (int i=0; i!=5; ++i) deck.add(CardFactory.construct("Darkness"));
            //for (int i=0; i!=5; ++i) deck.add(CardFactory.construct("World at War"));
            for (int i=0; i!=5; ++i) deck.add(CardFactory.construct("Volcanic Hammer"));

            instance.getPlayer(0).setDeck(deck.iterator());
            instance.getPlayer(1).setDeck(deck.iterator());
            */


            /*Commento questa parte perché queste due istruzioni vengono lanciate dal form*/
            /*
            instance.setup();

            instance.run();
            */
            /*Faccio partire il form*/

            GUIMenu menu = new GUIMenu();
            Thread t = new Thread(menu);
            {
                t.start();
            }


        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    /*Aggiunto un metodo overload setup che prese le seguenti stringhe setta il tutto*/

    public void setup(String player1, String player2, String deck1, String deck2) {
        Players[0].setName(player1);
        Players[1].setName(player2);

        /*****************************************/

        /*Questa porzione di codice serve meramente a leggere i deck ed inserirli nei rispettivi giocatori*/

        ArrayList<Card> p1deck = new ArrayList<Card>();
        Scanner p1deckFile = null;
        try {
            p1deckFile = new Scanner(new File(deck1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (p1deckFile.hasNextLine()) {
            p1deck.add(CardFactory.construct(p1deckFile.nextLine()));
        }

        ArrayList<Card> p2deck = new ArrayList<Card>();
        Scanner p2deckFile = null;
        try {
            p2deckFile = new Scanner(new File(deck2));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (p2deckFile.hasNextLine()) {
            p2deck.add(CardFactory.construct(p2deckFile.nextLine()));
        }
        /****************************************/


        Players[0].setDeck(p1deck.iterator());
        Players[1].setDeck(p2deck.iterator());


    }

    public void setup() {


        System.out.println("Please write player 1's name");
        Players[0].setName(reader.nextLine());

        ArrayList<Card> p1deck = new ArrayList<Card>();
        System.out.println(Players[0].name() + " give the filename of your deck");
        try {
            Scanner p1deckFile = new Scanner(new File(reader.nextLine()));
            while (p1deckFile.hasNextLine()) {
                p1deck.add(CardFactory.construct(p1deckFile.nextLine()));
            }
        } catch (IOException ex) {
            throw new RuntimeException("cannot read player 1's deck file");
        }
        Players[0].setDeck(p1deck.iterator());


        System.out.println("Please write player 2's name");
        Players[1].setName(reader.nextLine());

        ArrayList<Card> p2deck = new ArrayList<Card>();
        System.out.println(Players[1].name() + " give the filename of your deck");
        try {
            Scanner p2deckFile = new Scanner(new File(reader.nextLine()));
            while (p2deckFile.hasNextLine()) {
                p2deck.add(CardFactory.construct(p2deckFile.nextLine()));
            }
        } catch (IOException ex) {
            throw new RuntimeException("cannot read player 1's deck file");
        }
        Players[1].setDeck(p2deck.iterator());

    }

    //execute game
    public void run() {
        Players[0].getDeck().shuffle();
        Players[1].getDeck().shuffle();

        for (int i = 0; i != 5; ++i) Players[0].draw();
        for (int i = 0; i != 5; ++i) Players[1].draw();

        this.board = new GUIBoard();

        Players[0].setPhase(Phases.DRAW, new SkipPhase(Phases.DRAW));

        try {
            while (true) {
                instance.nextPlayer().executeTurn();
            }
        } catch (EndOfGame e) {
            System.out.println(e.getMessage());
        }
    }

    public void setBoard() {
        board.run();
    }

    public void setTurnManager(TurnManager m) {
        turn_manager_stack.push(m);
    }

    public void removeTurnManager(TurnManager m) {
        turn_manager_stack.remove(m);
    }

    public Player getPlayer(int i) {
        return Players[i];
    }

    public Player getCurrentPlayer() {
        return turn_manager_stack.peek().getCurrentPlayer();
    }

    public Player getCurrentAdversary() {
        return turn_manager_stack.peek().getCurrentAdversary();
    }

    public Player nextPlayer() {
        return turn_manager_stack.peek().nextPlayer();
    }

    public CardStack getStack() {
        return stack;
    }

    public Triggers getTriggers() {
        return triggers;
    }

    public Scanner getScanner() {
        return reader;
    }
}
