package Cardgame.cards;

import Cardgame.Core.*;
import Cardgame.Core.Interface.Card;
import Cardgame.Core.Interface.CardConstructor;
import Cardgame.Core.Interface.Creature;
import Cardgame.Core.Interface.Effect;

import java.util.ArrayList;
import java.util.List;


public class Reflexologist extends AbstractCard {
    static private final String cardName = "Reflexologist";

    static private StaticInitializer initializer =
            new StaticInitializer(cardName, new CardConstructor() {
                public Card create() {
                    return new Reflexologist();
                }
            });


    public Effect getEffect(Player p) {
        return new ReflexologistEffect(p, this);
    }


    public String name() {
        return cardName;
    }

    public String type() {
        return "Creature";
    }

    public String ruleText() {
        return "Put in play a creature " + cardName + "(0/1) with tap: " + cardName + " does nothing";
    }

    public String toString() {
        return name() + "[" + ruleText() + "]";
    }

    public boolean isInstant() {
        return false;
    }

/*  public ImageIcon artImage(){
        return new ImageIcon("src/resources/IMG_Reflexologist.jpg");
    }*/

    private class ReflexologistEffect extends AbstractCreatureCardEffect {
        public ReflexologistEffect(Player p, Card c) {
            super(p, c);
        }

        protected Creature createCreature() {
            return new ReflexologistCreature(owner);
        }

    }

    private class ReflexologistCreature extends AbstractCreature {

        ReflexologistCreature(Player owner) {
            super(owner);
        }

        public String name() {
            return cardName;
        }

        public int power() {
            return 0;
        }

        public int toughness() {
            return 1;
        }

        public List<Effect> effects() {
            ArrayList<Effect> effects = new ArrayList<>();
            effects.add(new Reflexology());
            return effects;
        }

        public List<Effect> avaliableEffects() {
            ArrayList<Effect> effects = new ArrayList<>();
            if (!topDecorator.isTapped())
                effects.add(new Reflexology());
            return effects;
        }

        private class Reflexology extends AbstractEffect {
            public void resolve() {
            }

            public String name() {
                return "Reflexology";
            }

            public String toString() {
                return cardName + " tap: does nothing";
            }
        }
    }

}
