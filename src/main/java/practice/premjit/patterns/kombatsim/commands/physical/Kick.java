package practice.premjit.patterns.kombatsim.commands.physical;

import practice.premjit.patterns.kombatsim.attributes.Attribute;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.commands.AllActions;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.RangeValueMap;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;

/**
 * Kicks are determined by Fighters strength and dexterity. More dexterity, higher possibility of major damage.
 * 
 * @author Premjit Adhikary
 *
 */
public class Kick extends AbstractPhysicalActionCommand {
	protected static final double MIN_DAMAGE = 15.0;
	protected static final double MAX_DAMAGE = 20.0;
	
	protected static final RangeValueMap DEXTERITY_RANGE_AND_CHANCE = RangeValueMap.builder()
			.addRangeValue(   0.0,   0.0)
			.addRangeValue(   1.0,  10.0)
			.addRangeValue(  10.0,  50.0)
			.addRangeValue(  20.0,  60.0)
			.addRangeValue(  50.0,  80.0)
			.addRangeValue( 100.0,  90.0)
			.addRangeValue( 200.0,  95.0)
			.addRangeValue( 500.0,  98.0)
			.addRangeValue(1000.0, 100.0)
			.build();
	protected static final RangeValueMap DEXTERITY_RANGE_AND_DAMAGE = RangeValueMap.builder()
			.addRangeValue(   0.0,   0.0)
			.addRangeValue(   1.0,   1.0)
			.addRangeValue(  10.0,   2.0)
			.addRangeValue(  20.0,   5.0)
			.addRangeValue(  50.0,  10.0)
			.addRangeValue( 100.0,  20.0)
			.addRangeValue( 200.0,  35.0)
			.addRangeValue( 500.0,  60.0)
			.addRangeValue(1000.0, 100.0)
			.build();
	
	public Kick(AbstractFighter fighter) {
		super(fighter);
		this.name = AllActions.KICK.value();
	}

	@Override
	public boolean canBeExecuted() {
		return fighterHasStrengthAndDexterity();
	}

	@Override
	protected PhysicalDamage calculatePhysicalDamage() {
		double damageFromStrength = fighter.getAttribute(AttributeType.STRENGTH).map(Attribute::net).orElse(0.0) * 0.30;
		
		double dexterity = fighter.getAttribute(AttributeType.DEXTERITY).map(Attribute::net).orElse(0.0);
		double damageFromDexterity = DEXTERITY_RANGE_AND_DAMAGE.getValue(dexterity);
		double hitChance = DEXTERITY_RANGE_AND_CHANCE.getValue(dexterity);
		boolean hit = Randomizer.hit(hitChance);

		double min = MIN_DAMAGE + (hit ? damageFromStrength : 0);
		double max = MAX_DAMAGE + (hit ? (damageFromStrength + damageFromDexterity) : 0);
		
		return PhysicalDamage.create(b -> b.min(min).max(max));
	}
	
	private boolean fighterHasStrengthAndDexterity() {
		return fighter.getAttribute(AttributeType.STRENGTH).isPresent() 
				&& fighter.getAttribute(AttributeType.DEXTERITY).isPresent();
	}

}
