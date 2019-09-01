package practice.premjit.patterns.kombatsim.attributes;

import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;

public final class AttributeUtility {
	private static final EnumSet<AttributeType> VARIABLE_ATTRIBUTES = EnumSet.of(
			AttributeType.LIFE, AttributeType.STAMINA, AttributeType.MOJO, AttributeType.MANA); 
	
	private AttributeUtility() {
		throw new UnsupportedOperationException();
	}
	
	public static Optional<Attribute> getAttribute(Map<AttributeType, Attribute> attributeMap, AttributeType type) {
		validateMap(attributeMap);
		return Optional.ofNullable(attributeMap.get(type));
	}
	
	public static Optional<VariableAttribute> getVariableAttribute(Map<AttributeType, Attribute> attributeMap, 
			AttributeType type) {
		validateMap(attributeMap);
		Attribute attr = attributeMap.get(type);
		return (attr instanceof VariableAttribute ? Optional.of((VariableAttribute) attr) : Optional.empty());
	}
	
	public static Optional<Attribute> getPrimaryAttribute(Map<AttributeType, Attribute> attributeMap) {
		validateMap(attributeMap);
		return attributeMap.values()
				.stream()
				.filter(Attribute::isPrimary)
				.findFirst();
	}
	
	private static boolean validateMap(Map<AttributeType, Attribute> attributeMap) {
		if (attributeMap == null)
			throw new IllegalArgumentException("Map not initialized.");
		return true;
	}
	
	public static Attribute buildStrength(double base) {
		return buildAttribute(AttributeType.STRENGTH, base, false);
	}
	
	public static Attribute buildStrengthAsPrimary(double base) {
		return buildAttribute(AttributeType.STRENGTH, base, true);
	}
	
	public static Attribute buildDexterity(double base) {
		return buildAttribute(AttributeType.DEXTERITY, base, false);
	}
	
	public static Attribute buildDexterityAsPrimary(double base) {
		return buildAttribute(AttributeType.DEXTERITY, base, true);
	}
	
	private static Attribute buildAttribute(AttributeType type, double base, boolean primary) {
		return new Attribute(type, base, primary);
	}
	
	public static VariableAttribute buildLife(double base) {
		return buildVariableAttribute(AttributeType.LIFE, base, base, false);
	}
	
	public static VariableAttribute buildStamina(double base, double current) {
		return buildVariableAttribute(AttributeType.STAMINA, base, current, false);
	}
	
	public static VariableAttribute buildStaminaAsPrimary(double base, double current) {
		return buildVariableAttribute(AttributeType.STAMINA, base, current, true);
	}
	
	public static VariableAttribute buildMojo(double base, double current) {
		return buildVariableAttribute(AttributeType.MOJO, base, current, false);
	}
	
	public static VariableAttribute buildMojoAsPrimary(double base, double current) {
		return buildVariableAttribute(AttributeType.MOJO, base, current, true);
	}
	
	public static VariableAttribute buildMana(double base, double current) {
		return buildVariableAttribute(AttributeType.MANA, base, current, false);
	}
	
	public static VariableAttribute buildManaAsPrimary(double base, double current) {
		return buildVariableAttribute(AttributeType.MANA, base, current, true);
	}
	
	private static VariableAttribute buildVariableAttribute(AttributeType type, double base, 
			double current, boolean primary) {
		return new VariableAttribute(type, base, current, primary);
	}
	
	public static boolean isVariableAttribute(AttributeType type) {
		return VARIABLE_ATTRIBUTES.contains(type);
	}

}
