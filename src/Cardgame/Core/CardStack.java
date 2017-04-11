package Cardgame.Core;

import Cardgame.Core.Interface.Effect;
import Cardgame.GUI.panel.PanelStack;

import java.util.ArrayList;
import java.util.Iterator;


public class CardStack implements Iterable<Effect> {

    private final ArrayList<Effect> stack = new ArrayList<>();

    /****************************************************************/
    /*Questo è il codice inerente allo stack, modellato per il funzionamento in console*/
    public Iterator<Effect> iterator() {
        return stack.iterator();
    }

    //aggiungo un elemento allo stack
    public void push(Effect e) {
        stack.add(e);
        CardGame.instance.setBoard();
    }

    //rimuovo un elemento dallo stack
    public Effect pop() {
        Effect e = stack.remove(stack.size() - 1);
        CardGame.instance.setBoard();
        return e;
    }

    public void remove(Effect e) {
        stack.remove(e);
        e.remove();
    }

    public int size() {
        return stack.size();
    }

    public Effect get(int i) {
        return stack.get(i);
    }

    public int indexOf(Effect e) {
        return stack.indexOf(e);
    }

    public void resolve() {
        while (!stack.isEmpty()) {
            Effect e = pop();

            System.out.println("Stack: resolving " + e);

            CardGame.instance.setBoard();

            e.remove();
            e.resolve();
        }
    }

    /****************************************************************/


}
