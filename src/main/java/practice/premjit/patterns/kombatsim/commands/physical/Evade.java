package practice.premjit.patterns.kombatsim.commands.physical;

import java.util.Optional;

import practice.premjit.patterns.kombatsim.attributes.Attribute;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.commands.AllReactions;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.RangeValueMap;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;

/**
 * Evade is a function of Fighters dexterity. Higher the dexterity, higher the chance to evade the hit completely. <p>
 * 
 * The drawback is that if hit, takes the full brunt of the damage.
 * 
 * @author Premjit Adhikary
 *
 */
public class Evade extends AbstractPhysicalReactionCommand {
	
	protected static final RangeValueMap DEXTERITY_RANGE_AND_CHANCE = RangeValueMap.builder()
			.addRangeValue(   0.0,   0.0)
			.addRangeValue(   1.0,  10.0)
			.addRangeValue(  10.0,  20.0)
			.addRangeValue(  20.0,  30.0)
			.addRangeValue(  50.0,  50.0)
			.addRangeValue( 100.0,  65.0)
			.addRangeValue( 200.0,  75.0)
			.addRangeValue( 500.0,  85.0)
			.addRangeValue(1000.0,  90.0)
			.addRangeValue(2000.0, 100.0)
			.build();

	public Evade(AbstractFighter fighter) {
		super(fighter);
		this.name = AllReactions.EVADE.value();
	}
	
	@Override
	public boolean canBeExecuted(Optional<Move> move) {
		return super.canBeExecuted(move) && fighterHasDexterity();
	}

	@Override
	public void reduceDamage(PhysicalDamage physicalDamage) {
		double dexterity = fighter.getAttribute(AttributeType.DEXTERITY).map(Attribute::net).orElse(0.0);
		double evadeChance = DEXTERITY_RANGE_AND_CHANCE.getValue(dexterity);
		
		boolean evaded = Randomizer.hit(evadeChance);
		if (evaded) {
			physicalDamage.incrementBy(-physicalDamage.amount());
		}
	}
	
	private boolean fighterHasDexterity() {
		return fighter.getAttribute(AttributeType.DEXTERITY).isPresent();
	}

}
