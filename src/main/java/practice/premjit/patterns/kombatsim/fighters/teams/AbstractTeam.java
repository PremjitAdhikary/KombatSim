package practice.premjit.patterns.kombatsim.fighters.teams;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.Attribute;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.commands.ActionCommand;
import practice.premjit.patterns.kombatsim.commands.ReactionCommand;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.strategies.ActionStrategy;
import practice.premjit.patterns.kombatsim.strategies.ReactionStrategy;

public abstract class AbstractTeam extends AbstractFighter {
	protected ProxyArena proxyArena;

	protected AbstractTeam(String name, ArenaMediator arena) {
		super(name, arena);
		id = Randomizer.generateId();
	}
	
	protected abstract AbstractFighter currentFighter();
	
	public ArenaMediator proxyArena() {
		return proxyArena;
	}
	
	@Override
	public String name() {
		return currentFighter().name();
	}

	@Override
	public String fighterType() {
		return currentFighter().fighterType();
	}

	@Override
	public String fighterSubType() {
		return currentFighter().fighterSubType();
	}

	@Override
	public void setFighterType(String fighterType) {
		// do nothing
	}

	@Override
	public void setFighterSubType(String fighterSubType) {
		// do nothing
	}

	@Override
	public ArenaMediator arena() {
		return this.arena;
	}
	
	@Override
	public void addAttribute(Attribute attr) {
		// do nothing
	}
	
	@Override
	public Optional<Attribute> getAttribute(AttributeType type) {
		return currentFighter().getAttribute(type);
	}
	
	@Override
	public Optional<Attribute> getPrimaryAttribute() {
		return currentFighter().getPrimaryAttribute();
	}
	
	@Override
	public void addAction(ActionCommand action) {
		// do nothing
	}
	
	@Override
	public List<ActionCommand> allActions() {
		return currentFighter().allActions();
	}
	
	@Override
	public void addReaction(ReactionCommand reaction) {
		// do nothing
	}
	
	@Override
	public List<ReactionCommand> allReactions() {
		return currentFighter().allReactions();
	}

	@Override
	public ActionStrategy getActionStrategy() {
		return currentFighter().getActionStrategy();
	}

	@Override
	public void setActionStrategy(ActionStrategy actionPerformer) {
		// do nothing
	}

	@Override
	public ReactionStrategy getReactionStrategy() {
		return currentFighter().getReactionStrategy();
	}

	@Override
	public void setReactionStrategy(ReactionStrategy reactionPerformer) {
		// do nothing
	}

	@Override
	public double maxLife() {
		return currentFighter().maxLife();
	}

	@Override
	public double currentLife() {
		return currentFighter().currentLife();
	}

	@Override
	public void act() {
		// do nothing
	}
	
	@Override
	public int id() {
		return id;
	}
	
	@Override
	public Map<String, String> mapify() {
		return currentFighter().mapify();
	}

}
