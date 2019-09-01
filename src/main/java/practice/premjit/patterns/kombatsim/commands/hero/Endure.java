package practice.premjit.patterns.kombatsim.commands.hero;

import java.util.Optional;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.commands.AllReactions;
import practice.premjit.patterns.kombatsim.commands.physical.AbstractPhysicalReactionCommand;
import practice.premjit.patterns.kombatsim.commands.physical.Block;
import practice.premjit.patterns.kombatsim.commands.physical.Evade;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;

/**
 * Endure, a unique ReactionCommand for Batman, follows the Composite pattern to incorporate elements of both {@link 
 * practice.premjit.patterns.kombatsim.commands.physical.Block Block} and {@link 
 * practice.premjit.patterns.kombatsim.commands.physical.Evade Evade}. <p>
 * 
 * The idea is a vanilla batman, being vulnerable is more like to Evade the attacks while equipped batman, being more 
 * strong, will Block the attacks. <p>
 * 
 * After the first level of defense (Block or Evade) is done, in case the damage is fatal, Batman uses his Mojo to hold 
 * on to his dear life. <p>
 * 
 * @author Premjit Adhikary
 *
 */
public class Endure extends AbstractPhysicalReactionCommand {
	private boolean equiped;
	private AbstractPhysicalReactionCommand block;
	private AbstractPhysicalReactionCommand evade;
	private double mojoCost;
	private double lifeLimit;

	public Endure(AbstractFighter fighter, boolean equiped) {
		super(fighter);
		this.equiped = equiped;
		block = new Block(fighter);
		evade = new Evade(fighter);
		name = equiped ? AllReactions.BLOCK_AND_ENDURE.value() : AllReactions.EVADE_OR_ENDURE.value();
		mojoCost = 20;
		lifeLimit = 25;
	}
	
	@Override
	public boolean canBeExecuted(Optional<Move> move) {
		return fighterHasMojo() && command().canBeExecuted(move);
	}

	@Override
	public void reduceDamage(PhysicalDamage physicalDamage) {
		command().reduceDamage(physicalDamage);
		if (!isDamageFatal(physicalDamage.amount()))
			return;
		
		removeFatality(physicalDamage);
		chargeMojo();
	}
	
	AbstractPhysicalReactionCommand command() {
		return equiped ? block : evade;
	}
	
	private boolean fighterHasMojo() {
		return fighter.getAttribute(AttributeType.MOJO)
				.map(a -> (VariableAttribute) a)
				.filter(va -> va.current() > mojoCost)
				.isPresent();
	}
	
	private boolean isDamageFatal(double damage) {
		return damage >= fighter.currentLife();
	}
	
	private void removeFatality(PhysicalDamage physicalDamage) {
		double incr = physicalDamage.amount() - fighter.currentLife() + lifeLimit;
		incr = Math.max(physicalDamage.amount(), incr);
		physicalDamage.incrementBy(-incr);
	}
	
	private void chargeMojo() {
		fighter.getAttribute(AttributeType.MOJO).ifPresent(
				mojo -> ((VariableAttribute) mojo).incrementCurrent(-mojoCost));
	}

}
