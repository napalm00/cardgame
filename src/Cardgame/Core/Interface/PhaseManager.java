package Cardgame.Core.Interface;


import Cardgame.Core.Phases;

public interface PhaseManager {
    Phases currentPhase();

    Phases nextPhase();

}
