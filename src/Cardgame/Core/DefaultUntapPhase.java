package Cardgame.Core;


import Cardgame.Core.Interface.Creature;
import Cardgame.Core.Interface.Phase;

public class DefaultUntapPhase implements Phase {

    public void execute() {
        Player current_player = CardGame.instance.getCurrentPlayer();

        System.out.println(current_player.name() + ": untap phase");

        CardGame.instance.getTriggers().trigger(Triggers.UNTAP_FILTER);

        if (current_player.getCreatures().isEmpty())
            System.out.println("...no creatures to untap");

        for (Creature c : current_player.getCreatures()) {
            System.out.println("...untap " + c.name());
            c.untap();
        }
        CardGame.instance.setBoard();
    }
}