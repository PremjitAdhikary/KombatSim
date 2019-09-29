package practice.premjit.patterns.kombatsim.fighters.decorators;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.Attribute;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.beats.BeatObserver;
import practice.premjit.patterns.kombatsim.commands.ActionCommand;
import practice.premjit.patterns.kombatsim.commands.ReactionCommand;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.strategies.ActionStrategy;
import practice.premjit.patterns.kombatsim.strategies.ReactionStrategy;
import practice.premjit.patterns.kombatsim.visitors.FighterVisitor;

/**
 * Decorator pattern to enhance the abilities of the fighters.
 * 
 * @author Premjit Adhikary
 *
 */
public abstract class AbstractFighterDecorator extends AbstractFighter {
    protected AbstractFighter theFighter;
    private boolean equip;

    protected AbstractFighterDecorator(AbstractFighter fighter) {
        super(fighter.name(), fighter.arena());
        this.theFighter = fighter;
    }
    
    @Override
    public String name() {
        return theFighter.name();
    }

    @Override
    public String fighterType() {
        return theFighter.fighterType();
    }

    @Override
    public String fighterSubType() {
        return theFighter.fighterSubType();
    }

    @Override
    public void setFighterType(String fighterType) {
        theFighter.setFighterType(fighterType);
    }

    @Override
    public void setFighterSubType(String fighterSubType) {
        theFighter.setFighterSubType(fighterSubType);
    }

    @Override
    public ArenaMediator arena() {
        return theFighter.arena();
    }
    
    @Override
    public void addAttribute(Attribute attr) {
        theFighter.addAttribute(attr);
    }
    
    @Override
    public Optional<Attribute> getAttribute(AttributeType type) {
        return theFighter.getAttribute(type);
    }
    
    @Override
    public Optional<Attribute> getPrimaryAttribute() {
        return theFighter.getPrimaryAttribute();
    }
    
    @Override
    public void addAction(ActionCommand action) {
        theFighter.addAction(action);
    }
    
    @Override
    public List<ActionCommand> allActions() {
        return theFighter.allActions();
    }
    
    @Override
    public void addReaction(ReactionCommand reaction) {
        theFighter.addReaction(reaction);
    }
    
    @Override
    public List<ReactionCommand> allReactions() {
        return theFighter.allReactions();
    }

    @Override
    public ActionStrategy getActionStrategy() {
        return theFighter.getActionStrategy();
    }

    @Override
    public void setActionStrategy(ActionStrategy actionPerformer) {
        theFighter.setActionStrategy(actionPerformer);
    }

    @Override
    public ReactionStrategy getReactionStrategy() {
        return theFighter.getReactionStrategy();
    }

    @Override
    public void setReactionStrategy(ReactionStrategy reactionPerformer) {
        theFighter.setReactionStrategy(reactionPerformer);
    }
    
    @Override
    public boolean isAlive() {
        return theFighter.isAlive();
    }

    @Override
    public double maxLife() {
        return theFighter.maxLife();
    }

    @Override
    public double currentLife() {
        return theFighter.currentLife();
    }

    @Override
    public void act() {
        if (isEquipped())
            theFighter.act();
    }

    @Override
    public void react(Optional<Move> move) {
        if (isEquipped())
            theFighter.react(move);
        else
            move.ifPresent( m -> m.affect(this) );
    }

    @Override
    public void update() {
        theFighter.update();
    }

    @Override
    public void registerObserver(BeatObserver observer) {
        theFighter.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(BeatObserver observer) {
        theFighter.unregisterObserver(observer);
    }

    @Override
    public void notifyObservers() {
        theFighter.notifyObservers();
    }
    
    @Override
    public int id() {
        return theFighter.id();
    }

    @Override
    public boolean equals(Object obj) {
        return theFighter.equals(obj);
    }
    
    @Override
    public Map<String, String> mapify() {
        return theFighter.mapify();
    }
    
    protected boolean isEquipped() {
        return equip;
    }
    
    protected void equipped() {
        equip = true;
    }
    
    /**
     * Equip to enable the Fighter. <br>
     * Builder pattern to make sure that at creation of subclass this always gets called to lock down the 
     * decorator.
     */
    protected abstract void equip();

    @Override
    public void accept(FighterVisitor visitor) {
        theFighter.accept(visitor);
    }

}
