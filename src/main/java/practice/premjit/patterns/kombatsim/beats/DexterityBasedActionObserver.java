package practice.premjit.patterns.kombatsim.beats;

import java.util.Optional;

import practice.premjit.patterns.kombatsim.attributes.Attribute;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.common.RangeValueMap;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;

/**
 * Most of the fighters have a rhythm with which they perform attacks in the arena. To achieve this, observer pattern 
 * is used to beat to either a Tik or a Tok ({@link TikTok}). Dexterity is an attribute which determines the agility of 
 * the fighter. Based on dexterity, DexterityBasedActionObserver calculates when the fighter performs an action.
 * 
 * @author Premjit Adhikary
 *
 */
public class DexterityBasedActionObserver implements BeatObserver {
	protected static final int MAX_BEAT_COUNT = 100;
	
	protected static final RangeValueMap DEXTERITY_RANGE_AND_COUNT = RangeValueMap.builder()
			.addRangeValue(   0.0,1000.0)
			.addRangeValue(   1.0, 100.0)
			.addRangeValue(  20.0,  50.0)
			.addRangeValue(  30.0,  33.0)
			.addRangeValue(  40.0,  25.0)
			.addRangeValue(  50.0,  20.0)
			.addRangeValue(  60.0,  16.0)
			.addRangeValue(  70.0,  14.0)
			.addRangeValue(  80.0,  12.0)
			.addRangeValue(  90.0,  11.0)
			.addRangeValue( 100.0,  10.0)
			.addRangeValue( 125.0,   9.0)
			.addRangeValue( 150.0,   8.0)
			.addRangeValue( 200.0,   7.0)
			.addRangeValue( 250.0,   6.0)
			.addRangeValue( 350.0,   5.0)
			.addRangeValue( 450.0,   4.0)
			.addRangeValue( 600.0,   3.0)
			.addRangeValue( 750.0,   2.0)
			.addRangeValue(1000.0,   1.0)
			.build();
	
	protected int beatCount;
	protected AbstractFighter fighter;

	@Override
	public void update() {
		beatCount++;
		int maxBeatCount = calculateBeatCount();
		if (beatCount >= maxBeatCount) {
			beatCount = 0;
			fighter.act();
		}
	}
	
	public void setFighter(AbstractFighter fighter) {
		this.fighter = fighter;
	}

	protected int calculateBeatCount() {
		Optional<Attribute> optional = fighter.getAttribute(AttributeType.DEXTERITY);
		if (!optional.isPresent())
			return MAX_BEAT_COUNT;
		
		double dexterity = optional.get().net();
		return DEXTERITY_RANGE_AND_COUNT.getValue(dexterity).intValue();
	}

}
