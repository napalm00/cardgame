package Cardgame.cards;

import Cardgame.Core.*;
import Cardgame.Core.Interface.*;
import Cardgame.GUI.GUICreatures;

import java.util.ArrayList;


public class Afflict extends AbstractCard {

    static private final String cardName = "Afflict";

    static private StaticInitializer initializer =
            new StaticInitializer(cardName, new CardConstructor() {
                public Card create() {
                    return new Afflict();
                }
            });

    public Effect getEffect(Player owner) {
        return new AfflictEffect(owner, this);
    }

    public String name() {
        return cardName;
    }

    public String type() {
        return "Instant";
    }

    public String ruleText() {
        return "Target creature gets -1/-1 until end of turn";
    }

    public String toString() {
        return name() + "[" + ruleText() + "]";
    }

    public boolean isInstant() {
        return true;
    }

/*    public ImageIcon artImage(){
        return new ImageIcon("src/resources/IMG_Afflict.jpg");
    }*/

    private class AfflictEffect extends AbstractCardEffect implements TargetingEffect {
        private DecoratedCreature target;

        public AfflictEffect(Player p, Card c) {
            super(p, c);
        }

        public boolean play() {
            pickTarget();
            return super.play();
        }

        public void resolve() {
            if (target == null)
                return;

            if (target.isRemoved()) {
                System.out.println("Attaching " + cardName + " to removed creature");
                return;
            }

            final AfflictDecorator decorator = new AfflictDecorator();
            TriggerAction action = new TriggerAction() {
                public void execute() {
                    if (!target.isRemoved()) {
                        System.out.println("Triggered removal of " + cardName + " from " + target);
                        target.removeDecorator(decorator);
                    } else {
                        System.out.println("Triggered dangling removal of " + cardName + " from removed target. Odd should have been invalidated!");
                    }
                }
            };
            System.out.println("Ataching " + cardName + " to " + target.name() + " and registering end of turn trigger");
            CardGame.instance.getTriggers().register(Triggers.END_FILTER, action);

            decorator.setRemoveAction(action);
            target.addDecorator(decorator);
        }

        public void pickTarget() {
            System.out.println(owner.name() + ": choose target for " + name());

            ArrayList<DecoratedCreature> creatures = new ArrayList<>();
            int i = 1;

            Player player1 = CardGame.instance.getPlayer(0);
            Player player2 = CardGame.instance.getPlayer(1);

            for (DecoratedCreature c : player1.getCreatures()) {
                System.out.println(i + ") " + player1.name() + ": " + c);
                ++i;
                creatures.add(c);
            }
            for (DecoratedCreature c : player2.getCreatures()) {
                System.out.println(i + ") " + player2.name() + ": " + c);
                ++i;
                creatures.add(c);
            }


            GUICreatures creature = new GUICreatures(creatures);
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
            if (idx < 0 || idx >= creatures.size())
                target = null;
            else
                target = creatures.get(idx);
        }

        public String toString() {
            if (target == null)
                return super.toString() + " (no target)";
            else if (target.isRemoved())
                return super.toString() + " (removed creature)";
            else
                return name() + " [" + target.name() + " gets -1/-1 until end of turn]";
        }

    }

    class AfflictDecorator extends AbstractCreatureDecorator {
        TriggerAction action;

        public void setRemoveAction(TriggerAction a) {
            action = a;
        }

        public void onRemove() {
            System.out.println("Removing " + cardName + " and deregistering end of turn trigger");
            if (action != null)
                CardGame.instance.getTriggers().remove(action);
            super.onRemove();
        }

        public int power() {
            return decorated.power() - 1;
        }

        public int toughness() {
            return decorated.toughness() - 1;
        }
    }
}
