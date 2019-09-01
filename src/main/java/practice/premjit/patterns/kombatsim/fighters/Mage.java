package practice.premjit.patterns.kombatsim.fighters;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.EnumSet;
import java.util.Map;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.visitors.FighterVisitor;

public class Mage extends AbstractFighter {
	public static final String TYPE = "Mage";

	public Mage(String name, ArenaMediator arena, String subType) {
		super(name, arena);
		fighterType = TYPE;
		fighterSubType = subType;
		allowedAttributeTypes = EnumSet.of(AttributeType.LIFE, AttributeType.MANA);
		System.out.println("DETAILS:"+name+" "+id);
	}

	@Override
	public void accept(FighterVisitor visitor) {
		visitor.visit(this);
	}
	
	public double maxMana() {
		return AttributeUtility.getVariableAttribute(attributeMap, AttributeType.MANA)
				.map(VariableAttribute::net)
				.orElse(0.0);
	}
	
	public double currentMana() {
		return AttributeUtility.getVariableAttribute(attributeMap, AttributeType.MANA)
				.map(VariableAttribute::current)
				.orElse(0.0);
	}
	
	@Override
	public Map<String, String> mapify() {
		return KombatLogger.mapBuilder()
				.withPartial(super.mapify())
				.with(MANA, Double.toString(maxMana()))
				.with(CURRENT_MANA, Double.toString(currentMana()))
				.build();
	}

}
