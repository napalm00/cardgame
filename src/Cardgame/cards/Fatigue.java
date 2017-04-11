package Cardgame.cards;

import Cardgame.Core.*;
import Cardgame.Core.Interface.*;
import Cardgame.GUI.GUIDamageable;

import java.util.ArrayList;


public class Fatigue extends AbstractCard {
    static private final String cardName = "Fatigue";

    static private StaticInitializer initializer =
            new StaticInitializer(cardName, new CardConstructor() {
                public Card create() {
                    return new Fatigue();
                }
            });

    public String name() {
        return cardName;
    }

    public String type() {
        return "Sorcery";
    }

    public String ruleText() {
        return "Target player skips his next draw phase";
    }

    public String toString() {
        return name() + " [" + ruleText() + "]";
    }

    public boolean isInstant() {
        return false;
    }

    public Effect getEffect(Player owner) {
        return new FatigueEffect(owner, this);
    }

/*    public ImageIcon artImage(){
        return new ImageIcon("src/resources/IMG_Fatigue.jpg");
    }*/

    private class FatigueEffect extends AbstractCardEffect implements TargetingEffect {
        Player target;

        public FatigueEffect(Player p, Card c) {
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
                return name() + " [" + target.name() + " skips his next draw phase]";
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
                target.setPhase(Phases.DRAW, new SkipPhase(Phases.DRAW));
        }
    }

}
