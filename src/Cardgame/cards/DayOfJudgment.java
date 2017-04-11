package Cardgame.cards;

import Cardgame.Core.*;
import Cardgame.Core.Interface.Card;
import Cardgame.Core.Interface.CardConstructor;
import Cardgame.Core.Interface.Effect;

import java.util.ArrayList;


public class DayOfJudgment extends AbstractCard {
    static private final String cardName = "Day of Judgment";

    static private StaticInitializer initializer =
            new StaticInitializer(cardName, new CardConstructor() {
                public Card create() {
                    return new DayOfJudgment();
                }
            });

    public Effect getEffect(Player owner) {
        return new DayOfJudgmentEffect(owner, this);
    }

    public String name() {
        return cardName;
    }

    public String type() {
        return "Sorcery";
    }

    public String ruleText() {
        return "Destroy all creatures";
    }

    public String toString() {
        return name() + "[" + ruleText() + "]";
    }

    public boolean isInstant() {
        return false;
    }

/*    public ImageIcon artImage() {
        return new ImageIcon("src/resources/IMG_Day_of_Judgment.jpg");
    }*/

    private class DayOfJudgmentEffect extends AbstractCardEffect {
        public DayOfJudgmentEffect(Player p, Card c) {
            super(p, c);
        }

        public void resolve() {

            ArrayList<DecoratedCreature> creatures = new ArrayList<>();
            creatures.addAll(CardGame.instance.getPlayer(0).getCreatures());
            creatures.addAll(CardGame.instance.getPlayer(1).getCreatures());
            for (DecoratedCreature c : creatures) {
                if (!c.isRemoved())
                    c.destroy();
            }
        }
    }

}