package Cardgame.cards;

import Cardgame.Core.*;
import Cardgame.Core.Interface.Card;
import Cardgame.Core.Interface.CardConstructor;
import Cardgame.Core.Interface.Effect;
import Cardgame.Core.Interface.TargetingEffect;
import Cardgame.GUI.GUIStack;

public class Cancel extends AbstractCard {
    static private final String cardName = "Cancel";

    static private StaticInitializer initializer =
            new StaticInitializer(cardName, new CardConstructor() {
                public Card create() {
                    return new Cancel();
                }
            });

    public String name() {
        return cardName;
    }

    public String type() {
        return "Instant";
    }

    public String ruleText() {
        return "Counter target spell";
    }

    public String toString() {
        return name() + " [" + ruleText() + "]";
    }

    public boolean isInstant() {
        return true;
    }

    public Effect getEffect(Player owner) {
        return new CancelEffect(owner, this);
    }

/*    public ImageIcon artImage(){
        return new ImageIcon("src/resources/IMG_Cancel.jpg");
    }*/

    private class CancelEffect extends AbstractCardEffect implements TargetingEffect {
        Effect target;

        public CancelEffect(Player p, Card c) {
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
                return name() + " [Counter " + target.name() + "]";
        }

        public void pickTarget() {
            System.out.println(owner.name() + ": choose target for " + name());
            CardStack stack = CardGame.instance.getStack();
            int i = 1;
            for (Effect e : stack) {
                System.out.println(i + ") " + e.name());
                ++i;
            }

            GUIStack s = new GUIStack(owner, name());
            Thread t = new Thread(s);
            {
                t.start();
            }

            while(s.getInput().isEmpty()){
                try {
                    t.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int idx = Integer.parseInt(s.getInput()) - 1;
            if (idx < 0 || idx >= stack.size())
                target = null;
            else
                target = stack.get(idx);
        }

        public void resolve() {
            if (target == null) {
                System.out.println(cardName + " has no target");
            } else if (CardGame.instance.getStack().indexOf(target) == -1) {
                System.out.println(cardName + " target is not on the stack anymore");
            } else {
                System.out.println(cardName + " removing " + target.name() + " from stack");
                CardGame.instance.getStack().remove(target);
            }
        }
    }

}

