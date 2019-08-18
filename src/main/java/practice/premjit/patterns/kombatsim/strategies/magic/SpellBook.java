package practice.premjit.patterns.kombatsim.strategies.magic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import practice.premjit.patterns.kombatsim.commands.magic.AbstractReactionSpell;
import practice.premjit.patterns.kombatsim.commands.magic.ActionSpell;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.fighters.Mage;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.strategies.ActionStrategy;
import practice.premjit.patterns.kombatsim.strategies.ReactionStrategy;

/**
 * SpellBook is loosely based on ObjectPool pattern. <br><br>
 * 
 * The idea is the spells can be borrowed from and returned to the SpellBook. So at any point, the Mage would only 
 * have limited number of spells to work with. <br><br> 
 * 
 * Note that at every beat that the Mage has to perform an action, there is a {@link SpellBook#actionChance chance} 
 * factor that determines whether the spell is casted (executed) or not. <br><br>
 * 
 * For reaction, the mages cast a {@link AbstractReactionSpell ReactionSpell} which creates an aura or a shield around 
 * the mage. This stays effective for a duration and during that duration, based on the nature of the reaction spell 
 * cast and the Move it has to counter, reacts to it. <br><br> 
 * 
 * The moment a spell is casted, it is borrowed({@link SpellBook#borrowSpell(ActionSpell) borrowSpell(Action)}, {@link 
 * SpellBook#borrowSpell(AbstractReactionSpell) borrowSpell(Reaction}) for a duration as it takes its time to cools 
 * down ({@link SpellBook#actionSpellsCooldown actionSpellsCooldown}, {@link SpellBook#reactionSpellsCooldown 
 * reactionSpellsCooldown}). After cool down, the spells are returned({@link SpellBook#returnSpell(ActionSpell) 
 * returnSpell(Action)}, {@link SpellBook#returnSpell(AbstractReactionSpell) returnSpell(reaction)}) so that they are 
 * ready ({@link SpellBook#actionSpellsReady actionSpellsReady}, {@link SpellBook#reactionSpellsReady 
 * reactionSpellsReady}). <br><br>
 * 
 * @author Premjit Adhikary
 *
 */
public class SpellBook implements ActionStrategy, ReactionStrategy {
	protected Mage mage;
	protected double actionChance;
	
	protected List<ActionSpell> actionSpellsReady;
	protected List<ActionSpell> actionSpellsCooldown;
	protected List<AbstractReactionSpell> reactionSpellsReady;
	protected List<AbstractReactionSpell> reactionSpellsCooldown;

	public SpellBook(Mage mage) {
		this.mage = mage;
		actionSpellsReady = new ArrayList<>();
		actionSpellsCooldown = new ArrayList<>();
		reactionSpellsReady = new ArrayList<>();
		reactionSpellsCooldown = new ArrayList<>();
		actionChance = 50;
	}

	@Override
	public void perform() {
		if (Randomizer.hit(actionChance)) {
			getActionSpell().ifPresent( ActionSpell::execute );
		}
	}

	@Override
	public boolean perform(Optional<Move> move) {
		if (!isReactionActive()) {
			getReactionSpell().ifPresent( AbstractReactionSpell::activate );
		}
		
		Optional<AbstractReactionSpell> reactionSpell = getActiveReaction();
		if (reactionSpell.isPresent()) {
			reactionSpell.get().execute(move);
			return true;
		}
		return false;
	}
	
	public Optional<ActionSpell> getActionSpell() {
		List<ActionSpell> enabledActions = actionSpellsReady.stream()
				.filter(ActionSpell::canBeExecuted)
				.collect(Collectors.toList());
		if (enabledActions.isEmpty())
			return Optional.empty();
		ActionSpell spell = enabledActions.get(Randomizer.randomInteger(enabledActions.size()-1));
		return Optional.of(spell);
	}
	
	public void borrowSpell(ActionSpell spell) {
		actionSpellsReady.remove(spell);
		actionSpellsCooldown.add(spell);
	}
	
	public void returnSpell(ActionSpell spell) {
		actionSpellsCooldown.remove(spell);
		actionSpellsReady.add(spell);
	}
	
	public Optional<AbstractReactionSpell> getReactionSpell() {
		if (reactionSpellsReady.isEmpty() || isReactionActive())
			return Optional.empty();
		AbstractReactionSpell spell = reactionSpellsReady.get(Randomizer.randomInteger(reactionSpellsReady.size()-1));
		return Optional.of(spell);
	}
	
	public void borrowSpell(AbstractReactionSpell spell) {
		reactionSpellsReady.remove(spell);
		reactionSpellsCooldown.add(spell);
	}
	
	public void returnSpell(AbstractReactionSpell spell) {
		reactionSpellsCooldown.remove(spell);
		reactionSpellsReady.add(spell);
	}
	
	protected Optional<AbstractReactionSpell> getActiveReaction() {
		return mage.allReactions()
			.stream()
			.filter(r -> 
						r instanceof AbstractReactionSpell && ((AbstractReactionSpell) r).isActive())
			.map( r -> (AbstractReactionSpell) r)
			.findFirst();
	}
	
	public boolean isReactionActive() {
		return mage.allReactions()
				.stream()
				.anyMatch( r -> 
					r instanceof AbstractReactionSpell && ((AbstractReactionSpell) r).isActive());
	}

	public void setActionChance(double actionChance) {
		this.actionChance = actionChance;
	}

}
