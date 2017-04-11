package Cardgame.Core;



import Cardgame.GUI.GUIEnd;

import Cardgame.GUI.GUIend;


public class EndOfGame extends RuntimeException {


    public EndOfGame() {
    }


    public EndOfGame(String msg) {
        super(msg);
        GUIend guiEndPhase = new GUIend();
        Thread t = new Thread(guiEndPhase);
        {
            t.run();
        }
    }
}
