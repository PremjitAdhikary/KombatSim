package practice.premjit.patterns.kombatsim.fighters.decorators;

import java.util.EnumMap;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;

/**
 * As the name suggests, enhances existing attributes.
 * 
 * @author Premjit Adhikary
 *
 */
public class AttributeEnhanacer extends AbstractFighterDecorator {
	private EnumMap<AttributeType, Double> attributeAddend;
	private EnumMap<AttributeType, Double> attributeMultiplicand;

	public AttributeEnhanacer(AbstractFighter fighter) {
		super(fighter);
		attributeAddend = new EnumMap<>(AttributeType.class);
		attributeMultiplicand = new EnumMap<>(AttributeType.class);
	}
	
	public void enhanceWithAdder(AttributeType type, double addend) {
		attributeAddend.put(type, addend);
	}
	
	public void enhanceWithMultiplier(AttributeType type, double multiplicand) {
		attributeMultiplicand.put(type, multiplicand);
	}

	@Override
	public void equip() {
		if (isEquipped())
			return;
		attributeAddend.entrySet()
			.forEach( e -> theFighter.getAttribute(e.getKey())
					.ifPresent(attr -> attr.add(e.getValue())));
		attributeMultiplicand.entrySet()
			.forEach( e -> theFighter.getAttribute(e.getKey())
					.ifPresent(attr -> attr.multiply(e.getValue())));
		equipped();
	}

}
