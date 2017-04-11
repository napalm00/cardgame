package Cardgame.GUI.panel;

import Cardgame.Core.AbstractCard;
import Cardgame.Core.Interface.Effect;
import Cardgame.Core.Player;
import Cardgame.GUI.IntRefreshableGUI;

/**
 * Classe
 *
 * Classe che serve a disegnare il retro delle carte
 */
public class PanelBackCard extends AbstractCard {

    /**
     * Implementato per poter estendere {@link AbstractCard}
     *
     * @param owner Giocatore proprietario della carta
     * @return Null
     */
    @Override
    public Effect getEffect(Player owner) {
        return null;
    }

    /**
     * Implementato per poter estendere {@link AbstractCard}
     *
     * @return Null
     */
    @Override
    public String type() {
        return null;
    }

    /**
     * Implementato per poter estendere {@link AbstractCard}
     *
     * @return null
     */
    @Override
    public String ruleText() {
        return null;
    }

    /**
     * Implementato per poter estendere {@link AbstractCard}
     *
     * @return Ritorna sempre false
     */
    @Override
    public boolean isInstant() {
        return false;
    }

    /**
     * Implementato per poter estendere {@link AbstractCard}
     *
     * @return Ritorna la stringa "Back Card"
     */
    @Override
    public String name() {
        return "Back Card";
    }
}
