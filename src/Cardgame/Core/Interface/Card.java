package Cardgame.Core.Interface;


import Cardgame.Core.Player;

public interface Card extends GameEntity {

    void remove();

    // returns the effect to be placed on the stack
    Effect getEffect(Player owner);

    String type(); //sorcery, instant, or creature

    String ruleText();

    boolean isInstant();
}
