package Cardgame.cards;

import Cardgame.Core.*;
import Cardgame.Core.Interface.*;
import Cardgame.GUI.GUIDamageable;

import java.util.ArrayList;

public class VolcanicHammer extends AbstractCard {
    static private final String cardName = "Volcanic Hammer";

    static private StaticInitializer initializer =
            new StaticInitializer(cardName, new CardConstructor() {
                public Card create() {
                    return new VolcanicHammer();
                }
            });

    public String name() {
        return cardName;
    }

    public String type() {
        return "Sorcery";
    }

    public String ruleText() {
        return "Deal 3 damage to target creature or player";
    }

    public String toString() {
        return name() + " [" + ruleText() + "]";
    }

    public boolean isInstant() {
        return false;
    }

    public Effect getEffect(Player owner) {
        return new VolcanicHammerEffect(owner, this);
    }

/*    public ImageIcon artImage(){
        return new ImageIcon("src/resources/IMG_Volcanic_Hammer.jpg");
    }*/

    private class VolcanicHammerEffect extends AbstractCardEffect implements TargetingEffect {
        Damageable target;

        public VolcanicHammerEffect(Player p, Card c) {
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
                return name() + " [Deal 3 damage to " + target.name() + "]";
        }

        public void pickTarget() {
            System.out.println(owner.name() + ": choose target for " + name());

            ArrayList<Damageable> targets = new ArrayList<>();
            int i = 1;

            Player curPlayer = CardGame.instance.getPlayer(0);
            System.out.println(i + ") " + curPlayer.name());
            targets.add(curPlayer);
            ++i;
            for (DecoratedCreature c : curPlayer.getCreatures()) {
                System.out.println(i + ") " + curPlayer.name() + ": " + c.name());
                targets.add(c);
                ++i;
            }

            curPlayer = CardGame.instance.getPlayer(1);
            System.out.println(i + ") " + curPlayer.name());
            targets.add(curPlayer);
            ++i;
            for (DecoratedCreature c : curPlayer.getCreatures()) {
                System.out.println(i + ") " + curPlayer.name() + ": " + c.name());
                targets.add(c);
                ++i;
            }

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
            if (idx < 0 || idx >= targets.size())
                target = null;
            else
                target = targets.get(idx);
        }

        public void resolve() {
            if (target == null) {
                System.out.println(cardName + " has no target");
            } else if (target.isRemoved()) {
                System.out.println(cardName + " target not in play anymore");
            } else {
                target.inflictDamage(3);
            }
        }
    }
}
