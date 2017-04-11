package Cardgame.Core;

import Cardgame.Core.Interface.Card;
import Cardgame.Core.Interface.Creature;
import Cardgame.Core.Interface.Effect;
import Cardgame.GUI.GUIHand;

import java.util.ArrayList;
import java.util.Scanner;


// utility static methods for common behavior
public abstract class Utilities {

    // looks for all playable effects from cards in hand and creatures in play
    // and asks player for which one to play
    // includes creatures and sorceries only if is_main is true
    public static boolean play_available_effect(Player active_player, boolean is_main) {
        //collect and display available effects...
        ArrayList<Effect> available_effects = new ArrayList<>();
        Scanner reader = CardGame.instance.getScanner();

        //...cards first
        System.out.println(active_player.name() + " select card/effect to play, 0 to pass");
        int i = 0;
        for (Card c : active_player.getHand()) {
            if (is_main || c.isInstant()) {
                available_effects.add(c.getEffect(active_player));
                System.out.println(Integer.toString(i + 1) + ") " + c);
                ++i;
            }
        }

        //...creature effects last
        for (Creature c : active_player.getCreatures()) {
            for (Effect e : c.avaliableEffects()) {
                available_effects.add(e);
                System.out.println(Integer.toString(i + 1) + ") " + c.name() +
                        ": [" + e + "]");
                ++i;
            }
        }

        GUIHand mano = new GUIHand(available_effects);
        Thread t = new Thread(mano);
        {
            t.start();
        }

        while(mano.getInput().isEmpty()){
            try {
                t.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //get user choice and play it
        int idx = Integer.parseInt(mano.getInput()) - 1;
        if (idx < 0 || idx >= available_effects.size()) return false;

        available_effects.get(idx).play();
        return true;
    }


}
