package practice.premjit.patterns.kombatsim.fighters.decorators;

import java.util.EnumMap;
import java.util.function.Consumer;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;

/**
 * As the name suggests, enhances existing attributes.
 * 
 * @author Premjit Adhikary
 *
 */
public class AttributeEnhancer extends AbstractFighterDecorator {
	private EnumMap<AttributeType, Double> attributeAddend;
	private EnumMap<AttributeType, Double> attributeMultiplicand;

	private AttributeEnhancer(AbstractFighter fighter) {
		super(fighter);
		attributeAddend = new EnumMap<>(AttributeType.class);
		attributeMultiplicand = new EnumMap<>(AttributeType.class);
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
	
	// For Type safety Builder
	
	public static AttributeEnhancer create(Consumer<AttributeEnhancerFighterBuilder> block) {
		AttributeEnhancerBuilder builder = new AttributeEnhancerBuilder();
		block.accept(builder);
		return builder.build();
	}
	
	public interface AttributeEnhancerFighterBuilder {
		AttributeEnhancerOptionalsBuilder toEnhance(AbstractFighter fighter);
	}
	
	public interface AttributeEnhancerOptionalsBuilder {
		AttributeEnhancerMultiplyByBuilder multiply(AttributeType type);
		AttributeEnhancerAddToBuilder addTo(AttributeType type);
	}
	
	public interface AttributeEnhancerMultiplyByBuilder {
		AttributeEnhancerOptionalsBuilder by(double multiplicand);
	}
	
	public interface AttributeEnhancerAddToBuilder {
		AttributeEnhancerOptionalsBuilder value(double addend);
	}
	
	static class AttributeEnhancerBuilder implements 
			AttributeEnhancerFighterBuilder, AttributeEnhancerOptionalsBuilder,
			AttributeEnhancerMultiplyByBuilder, AttributeEnhancerAddToBuilder {
		private AttributeEnhancer enhancer;
		
		private AttributeType enhanceAttribute;
		
		private AttributeEnhancerBuilder() { }

		@Override
		public AttributeEnhancerOptionalsBuilder toEnhance(AbstractFighter fighter) {
			enhancer = new AttributeEnhancer(fighter);
			return this;
		}

		@Override
		public AttributeEnhancerMultiplyByBuilder multiply(AttributeType type) {
			enhanceAttribute = type;
			return this;
		}

		@Override
		public AttributeEnhancerOptionalsBuilder by(double multiplicand) {
			enhancer.attributeMultiplicand.put(enhanceAttribute, multiplicand);
			return this;
		}
		
		@Override
		public AttributeEnhancerAddToBuilder addTo(AttributeType type) {
			enhanceAttribute = type;
			return this;
		}

		@Override
		public AttributeEnhancerOptionalsBuilder value(double addend) {
			enhancer.attributeAddend.put(enhanceAttribute, addend);
			return this;
		}
		
		private AttributeEnhancer build() {
			if (enhancer == null)
				throw new IllegalArgumentException("Required properties are not set");
			enhancer.equip();
			return enhancer;
		}
	}

}
