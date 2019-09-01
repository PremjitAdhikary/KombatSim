package practice.premjit.patterns.kombatsim.fighters;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.EnumSet;
import java.util.Map;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.Attribute;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.visitors.FighterVisitor;

public class Amateur extends AbstractFighter {
	public static final String TYPE = "Amateur";

	public Amateur(String name, ArenaMediator arena, String subType) {
		super(name, arena);
		fighterType = TYPE;
		fighterSubType = subType;
		allowedAttributeTypes = EnumSet.of(AttributeType.LIFE, AttributeType.STRENGTH, AttributeType.DEXTERITY);
	}
	
	@Override
	public Map<String, String> mapify() {
		return KombatLogger.mapBuilder()
				.withPartial(super.mapify())
				.with(STRENGTH, Double.toString(getAttribute(AttributeType.STRENGTH).map(Attribute::net).orElse(0.0)))
				.with(DEXTERITY, Double.toString(getAttribute(AttributeType.DEXTERITY).map(Attribute::net).orElse(0.0)))
				.build();
	}

	@Override
	public void accept(FighterVisitor visitor) {
		visitor.visit(this);
	}
	
}
