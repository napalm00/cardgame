package Cardgame.Core;

import Cardgame.Core.Interface.*;

import java.util.*;

/*
Player
responsabilities:
- manage life
- execute turn
- manages phases
- holds library and hand
- manages creatures in play

collaborators:
- library
- game
- phase 
- phase manager (strategy)
- card
- creature
*/
public class Player implements Damageable {
    private final Library library = new Library(this);
    // creature management
    private final ArrayList<DecoratedCreature> creatures = new ArrayList<>();
    DamageStrategy damage_strategy_stack = new DefaultDamageStrategy(this);
    // basic properties: name, library, deck, and life
    private String name;
    private int life = 10;
    // phases maps a phaseID to a stack of phase implemenations
    // top one is active
    private EnumMap<Phases, Deque<Phase>> phases = new EnumMap<>(Phases.class);
    //
    private Deque<PhaseManager> phase_manager_stack = new ArrayDeque<PhaseManager>();
    // hand management
    private ArrayList<Card> hand = new ArrayList<>();
    private int max_hand_size = 7;

    public Player() {
        phase_manager_stack.push(new DefaultPhaseManager());

        phases.put(Phases.DRAW, new ArrayDeque<Phase>());
        setPhase(Phases.DRAW, new DefaultDrawPhase());

        phases.put(Phases.UNTAP, new ArrayDeque<Phase>());
        setPhase(Phases.UNTAP, new DefaultUntapPhase());

        phases.put(Phases.COMBAT, new ArrayDeque<Phase>());
        setPhase(Phases.COMBAT, new DefaultCombatPhase());

        phases.put(Phases.MAIN, new ArrayDeque<Phase>());
        setPhase(Phases.MAIN, new DefaultMainPhase());

        phases.put(Phases.END, new ArrayDeque<Phase>());
        setPhase(Phases.END, new DefaultEndPhase());

        phases.put(Phases.NULL, new ArrayDeque<Phase>());
    }

    public String name() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public boolean isRemoved() {
        return false;
    }

    public void accept(GameEntityVisitor v) {
        v.visit(this);
    }

    public Library getDeck() {
        return library;
    }

    public void setDeck(Iterator<Card> deck) {
        library.add(deck);
    }

    public int getLife() {
        return life;
    }

    public void setLife(int pts) {
        life = pts;
    }

    public void changeLife(int pts) {
        life += pts;
    }

    public void addDamageStrategy(DamageStrategyDecorator d) {
        damage_strategy_stack = d.decorate(damage_strategy_stack);
    }

    public void removeDamageStrategy(DamageStrategyDecorator d) {
        if (damage_strategy_stack instanceof DamageStrategyDecorator) {
            damage_strategy_stack =
                    ((DamageStrategyDecorator) damage_strategy_stack).removeDecorator(d);
        }
    }


    // phase management

    public void inflictDamage(int pts) {
        damage_strategy_stack.inflictDamage(pts);
    }

    public void heal(int pts) {
        damage_strategy_stack.heal(pts);
    }

    public void lose(String s) {
        damage_strategy_stack.lose(s);
    }

    public void inflictDamage(Creature c, int pts) {
        damage_strategy_stack.inflictDamage(c, pts);
    }

    void executeTurn() {
        System.out.println(name + "'s turn");
        System.out.println(name + " life " + getLife());
        if (getCreatures().isEmpty()) {
            System.out.println("No creatures in play");
        } else {
            System.out.println("Creatures in play:");
            for (DecoratedCreature c : getCreatures()) {
                System.out.println(c);
            }
        }
        System.out.println("");

        Player adversary = CardGame.instance.getCurrentAdversary();
        System.out.println(adversary.name() + " life " + adversary.getLife());
        if (adversary.getCreatures().isEmpty()) {
            System.out.println("No creatures in play");
        } else {
            System.out.println("Creatures in play:");
            for (DecoratedCreature c : adversary.getCreatures()) {
                System.out.println(c);
            }
        }
        System.out.println("");


        Phase cur_phase;
        while ((cur_phase = getPhase(nextPhase())) != null) {
            try {
                cur_phase.execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Phase getPhase(Phases p) {
        return phases.get(p).peek();
    }

    public void setPhase(Phases id, Phase p) {
        phases.get(id).push(p);
    }

    public void removePhase(Phases id, Phase p) {
        phases.get(id).remove(p);
    }

    public void setPhaseManager(PhaseManager m) {
        phase_manager_stack.push(m);
    }

    public void removePhaseManager(PhaseManager m) {
        phase_manager_stack.remove(m);
    }

    public Phases currentPhase() {
        return phase_manager_stack.peek().currentPhase();
    }

    public Phases nextPhase() {
        return phase_manager_stack.peek().nextPhase();
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getMaxHandSize() {
        return max_hand_size;
    }

    public void setMaxHandSize(int size) {
        max_hand_size = size;
    }

    public void draw() {
        Card drawn = library.draw();
        System.out.println(name() + " draw card: " + drawn.name());
        hand.add(drawn);
    }

    public void selectDiscard() {
        Scanner reader = CardGame.instance.getScanner();

        System.out.println(name() + " Choose a card to discard");
        for (int i = 0; i != hand.size(); ++i) {
            System.out.println(Integer.toString(i + 1) + ") " + hand.get(i));
        }
        int idx = reader.nextInt() - 1;
        if (idx >= 0 && idx < hand.size())
            hand.remove(idx);
    }

    ;

    public List<DecoratedCreature> getCreatures() {
        return creatures;
    }

    //private final ArrayList<Creature> creatures = new ArrayList<>();
    //public List<Creature> get_creatures() {return creatures;}


}
