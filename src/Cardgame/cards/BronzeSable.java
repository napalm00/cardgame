package Cardgame.cards;

import Cardgame.Core.*;
import Cardgame.Core.Interface.Card;
import Cardgame.Core.Interface.CardConstructor;
import Cardgame.Core.Interface.Creature;
import Cardgame.Core.Interface.Effect;


public class BronzeSable extends AbstractCard {
    static private final String cardName = "Bronze Sable";

    static private StaticInitializer initializer =
            new StaticInitializer(cardName, new CardConstructor() {
                public Card create() {
                    return new BronzeSable();
                }
            });

    public String name() {
        return cardName;
    }

    public String type() {
        return "Creature";
    }

    public String ruleText() {
        return "Put in play a creature " + cardName + "(2/1)";
    }

    public String toString() {
        return name() + "[" + ruleText() + "]";
    }

    public boolean isInstant() {
        return false;
    }

    public Effect getEffect(Player p) {
        return new BronzeSableEffect(p, this);
    }

/*    public ImageIcon artImage() {
        return new ImageIcon("src/resources/IMG_Bronze_Sable.jpg");
    }*/

    private class BronzeSableEffect extends AbstractCreatureCardEffect {
        public BronzeSableEffect(Player p, Card c) {
            super(p, c);
        }

        protected Creature createCreature() {
            return new BronzeSableCreature(owner);
        }

    }

    private class BronzeSableCreature extends AbstractCreature {

        BronzeSableCreature(Player owner) {
            super(owner);
        }

        public String name() {
            return cardName;
        }

        public int power() {
            return 2;
        }

        public int toughness() {
            return 1;
        }

    }

}

