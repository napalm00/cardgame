package Cardgame.Core;


import Cardgame.Core.Interface.Phase;

public class DefaultDrawPhase implements Phase {


    public void execute() {
        Player current_player = CardGame.instance.getCurrentPlayer();

        System.out.println(current_player.name() + ": draw phase");

        CardGame.instance.getTriggers().trigger(Triggers.DRAW_FILTER);
        current_player.draw();

        CardGame.instance.setBoard();

        while (current_player.getHand().size() > current_player.getMaxHandSize())
            current_player.selectDiscard();

        CardGame.instance.setBoard();
    }
}
