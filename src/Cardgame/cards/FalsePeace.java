package Cardgame.cards;

import Cardgame.Core.*;
import Cardgame.Core.Interface.*;
import Cardgame.GUI.GUIDamageable;

import java.util.ArrayList;


public class FalsePeace extends AbstractCard {
    static private final String cardName = "False Peace";

    static private StaticInitializer initializer =
            new StaticInitializer(cardName, new CardConstructor() {
                public Card create() {
                    return new FalsePeace();
                }
            });

    public String name() {
        return cardName;
    }

    public String type() {
        return "Sorcery";
    }

    public String ruleText() {
        return "Target player skips his next combat phase";
    }

    public String toString() {
        return name() + " [" + ruleText() + "]";
    }

    public boolean isInstant() {
        return false;
    }

    public Effect getEffect(Player owner) {
        return new FalsePeaceEffect(owner, this);
    }

/*    public ImageIcon artImage(){
        return new ImageIcon("src/resources/IMG_False_Peace.jpg");
    }*/

    private class FalsePeaceEffect extends AbstractCardEffect implements TargetingEffect {
        Player target;

        public FalsePeaceEffect(Player p, Card c) {
            super(p, c);
        }

        public boolean play() {
            pickTarget();
            return super.play();
        }

        public String toString() {
            if (target == null)
                return super.toString() + " (no target)";
            else
                return name() + " [" + target.name() + " skips his next combat phase]";
        }

        public void pickTarget() {
            System.out.println(owner.name() + ": choose target for " + name());
            System.out.println("1) " + CardGame.instance.getPlayer(0).name());
            System.out.println("2) " + CardGame.instance.getPlayer(1).name());

            ArrayList<Damageable> targets = new ArrayList<>();
            targets.add(CardGame.instance.getPlayer(0));
            targets.add(CardGame.instance.getPlayer(0));

            GUIDamageable creature = new GUIDamageable(targets);
            Thread t = new Thread(creature);
            {
                t.start();
            }

            while(creature.getInput().isEmpty()){
                try {
                    t.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //get user choice and play it
            int idx = Integer.parseInt(creature.getInput()) - 1;
            if (idx < 0 || idx > 1)
                target = null;
            else
                target = CardGame.instance.getPlayer(idx);
        }

        public void resolve() {
            if (target != null)
                target.setPhase(Phases.COMBAT, new SkipPhase(Phases.COMBAT));
        }
    }

}
