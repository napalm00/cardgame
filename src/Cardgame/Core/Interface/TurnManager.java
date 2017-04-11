package Cardgame.Core.Interface;


import Cardgame.Core.Player;

public interface TurnManager {
    Player getCurrentPlayer();

    Player getCurrentAdversary();

    Player nextPlayer();
}