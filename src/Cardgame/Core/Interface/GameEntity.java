package Cardgame.Core.Interface;


public interface GameEntity extends Named {

    boolean isRemoved();

    void accept(GameEntityVisitor v);
}
