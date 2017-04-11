package Cardgame.Core;


import Cardgame.Core.Interface.GameEntity;

public abstract class AbstractGameEntity implements GameEntity {
    protected boolean isRemoved = false;

    public boolean isRemoved() {
        return isRemoved;
    }

    public void remove() {
        isRemoved = true;
    }
}
