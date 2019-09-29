package practice.premjit.patterns.kombatsim.fighters;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.Attribute;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.beats.BeatObservable;
import practice.premjit.patterns.kombatsim.beats.BeatObservableImpl;
import practice.premjit.patterns.kombatsim.beats.BeatObserver;
import practice.premjit.patterns.kombatsim.commands.ActionCommand;
import practice.premjit.patterns.kombatsim.commands.ReactionCommand;
import practice.premjit.patterns.kombatsim.common.Identifiable;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.common.logging.Loggable;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.visitors.FighterVisitor;
import practice.premjit.patterns.kombatsim.strategies.ActionStrategy;
import practice.premjit.patterns.kombatsim.strategies.ReactionStrategy;

public abstract class AbstractFighter implements BeatObserver, BeatObservable, Fighter, Loggable, Identifiable {
    protected String name;
    protected int id;
    protected String fighterType;
    protected String fighterSubType;
    protected ArenaMediator arena;
    
    protected EnumMap<AttributeType, Attribute> attributeMap;
    protected BeatObservable beats;
    protected EnumSet<AttributeType> allowedAttributeTypes;
    
    protected List<ActionCommand> allActions;
    protected List<ReactionCommand> allReactions;
    
    protected ActionStrategy actionStrategy;
    protected ReactionStrategy reactionStrategy;

    protected AbstractFighter(String name, ArenaMediator arena) {
        this(name, arena, "", "");
    }

    protected AbstractFighter(String name, ArenaMediator arena, String type, String subType) {
        this.name = name;
        this.arena = arena;
        attributeMap = new EnumMap<>(AttributeType.class);
        beats = new BeatObservableImpl();
        allActions = new ArrayList<>();
        allReactions = new ArrayList<>();
        id = Randomizer.generateId();
        fighterType = type;
        fighterSubType = subType;
    }
    
    @Override
    public String name() {
        return name;
    }

    public String fighterType() {
        return fighterType;
    }

    public String fighterSubType() {
        return fighterSubType;
    }

    public void setFighterType(String fighterType) {
        this.fighterType = fighterType;
    }

    public void setFighterSubType(String fighterSubType) {
        this.fighterSubType = fighterSubType;
    }

    public ArenaMediator arena() {
        return arena;
    }

    public void arena(ArenaMediator arena) {
        this.arena = arena;
    }
    
    public void addAttribute(Attribute attr) {
        if (allowedAttributeTypes.contains(attr.type()) && !attributeMap.containsKey(attr.type()))
            attributeMap.put(attr.type(), attr);
    }
    
    public Optional<Attribute> getAttribute(AttributeType type) {
        return AttributeUtility.getAttribute(attributeMap, type);
    }
    
    public Optional<Attribute> getPrimaryAttribute() {
        return AttributeUtility.getPrimaryAttribute(attributeMap);
    }
    
    public void addAction(ActionCommand action) {
        allActions.add(action);
    }
    
    public List<ActionCommand> allActions() {
        return Collections.unmodifiableList(allActions);
    }
    
    public void addReaction(ReactionCommand reaction) {
        allReactions.add(reaction);
    }
    
    public List<ReactionCommand> allReactions() {
        return Collections.unmodifiableList(allReactions);
    }

    public ActionStrategy getActionStrategy() {
        return this.actionStrategy;
    }

    public void setActionStrategy(ActionStrategy strategy) {
        this.actionStrategy = strategy;
    }

    public ReactionStrategy getReactionStrategy() {
        return this.reactionStrategy;
    }

    public void setReactionStrategy(ReactionStrategy strategy) {
        this.reactionStrategy = strategy;
    }
    
    @Override
    public int id() {
        return this.id;
    }
    
    public boolean isAlive() {
        return currentLife() > 0;
    }

    @Override
    public double maxLife() {
        return AttributeUtility.getVariableAttribute(attributeMap, AttributeType.LIFE)
                .map(VariableAttribute::net)
                .orElse(0.0);
    }

    @Override
    public double currentLife() {
        return AttributeUtility.getVariableAttribute(attributeMap, AttributeType.LIFE)
                .map(VariableAttribute::current)
                .orElse(0.0);
    }

    @Override
    public void act() {
        actionStrategy.perform();
    }

    @Override
    public void react(Optional<Move> move) {
        if (!reactionStrategy.perform(move)) {
            move.ifPresent(m -> m.affect(this));
            KombatLogger.getLogger().log(
                    KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.REACTION, this.mapify(), null);
        }
        if (currentLife() <= 0) {
            KombatLogger.getLogger().log(
                    KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.INFO, this.mapify(), 
                    KombatLogger.mapBuilder().withName("Knock Out").build());
        }
    }

    @Override
    public void update() {
        notifyObservers();
    }

    @Override
    public void registerObserver(BeatObserver observer) {
        beats.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(BeatObserver observer) {
        beats.unregisterObserver(observer);
    }

    @Override
    public void notifyObservers() {
        if (isAlive())
            beats.notifyObservers();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        AbstractFighter other = (AbstractFighter) obj;
        return id == other.id();
    }
    
    @Override
    public Map<String, String> mapify() {
        try {
            return KombatLogger
                    .mapBuilder()
                    .withName("Fighter")
                    .with(FIGHTER_NAME, name)
                    .with(FIGHTER_TYPE, fighterType)
                    .with(FIGHTER_SUBTYPE, fighterSubType)
                    .with(LIFE, Double.toString(maxLife()))
                    .with(CURRENT_LIFE, Double.toString(currentLife()))
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void accept(FighterVisitor visitor) {
        throw new UnsupportedOperationException("Concrete type should be accepting visitors");
    }

}
