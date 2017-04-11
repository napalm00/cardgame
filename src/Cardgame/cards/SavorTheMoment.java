package Cardgame.cards;

import Cardgame.Core.*;
import Cardgame.Core.Interface.Card;
import Cardgame.Core.Interface.CardConstructor;
import Cardgame.Core.Interface.Effect;
import Cardgame.Core.Interface.TurnManager;


public class SavorTheMoment extends AbstractCard {
    static private final String cardName = "Savor the Moment";

    static private StaticInitializer initializer =
            new StaticInitializer(cardName, new CardConstructor() {
                public Card create() {
                    return new SavorTheMoment();
                }
            });

    public Effect getEffect(Player owner) {
        return new SavorTheMomentEffect(owner, this);
    }

    public String name() {
        return cardName;
    }

    public String type() {
        return "Sorcery";
    }

    public String ruleText() {
        return "Take an extra turn after this one. Skip the untap step of that turn.";
    }

    public String toString() {
        return name() + "[" + ruleText() + "]";
    }

    public boolean isInstant() {
        return false;
    }

/*    public ImageIcon artImage() {
        return new ImageIcon("src/resources/IMG_Savor_the_Moment.jpg");
    }*/

    private class SavorTheMomentEffect extends AbstractCardEffect {
        public SavorTheMomentEffect(Player p, Card c) {
            super(p, c);
        }

        public void resolve() {
            CardGame.instance.getCurrentPlayer().setPhase(Phases.UNTAP, new SkipPhase(Phases.UNTAP));
            CardGame.instance.setTurnManager(new ExtraTurn());
        }
    }

    private class ExtraTurn implements TurnManager {
        private Player current;
        private Player adversary;

        public ExtraTurn() {
            current = CardGame.instance.getCurrentPlayer();
            adversary = CardGame.instance.getCurrentAdversary();
        }


        public Player getCurrentPlayer() {
            return current;
        }

        public Player getCurrentAdversary() {
            return adversary;
        }

        public Player nextPlayer() {
            CardGame.instance.removeTurnManager(this);
            return current;
        }

    }

}
