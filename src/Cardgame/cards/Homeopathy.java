package Cardgame.cards;

import Cardgame.Core.AbstractCard;
import Cardgame.Core.AbstractCardEffect;
import Cardgame.Core.Interface.Card;
import Cardgame.Core.Interface.CardConstructor;
import Cardgame.Core.Interface.Effect;
import Cardgame.Core.Player;
import Cardgame.Core.StaticInitializer;


public class Homeopathy extends AbstractCard {
    static private final String cardName = "Homeopathy";

    static private StaticInitializer initializer =
            new StaticInitializer(cardName, new CardConstructor() {
                public Card create() {
                    return new Homeopathy();
                }
            });

    public Effect getEffect(Player owner) {
        return new HomeopathyEffect(owner, this);
    }

    public String name() {
        return cardName;
    }

    public String type() {
        return "Instant";
    }

    public String ruleText() {
        return cardName + " does nothing";
    }

    public String toString() {
        return name() + "[" + ruleText() + "]";
    }

    public boolean isInstant() {
        return true;
    }

/*    public ImageIcon artImage(){
        return new ImageIcon("src/resources/IMG_Homeopathy.jpg");
    }*/

    private class HomeopathyEffect extends AbstractCardEffect {
        public HomeopathyEffect(Player p, Card c) {
            super(p, c);
        }

        public void resolve() {
        }
    }

}
