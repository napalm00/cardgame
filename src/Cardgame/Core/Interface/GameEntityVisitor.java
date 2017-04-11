package Cardgame.Core.Interface;


import Cardgame.Core.DecoratedCreature;
import Cardgame.Core.Player;

public interface GameEntityVisitor {

    void visit(Player p);

    void visit(Card c);

    void visit(Effect e);

    void visit(DecoratedCreature c);

}
