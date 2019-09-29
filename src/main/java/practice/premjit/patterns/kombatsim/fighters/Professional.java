package practice.premjit.patterns.kombatsim.fighters;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.EnumSet;
import java.util.Map;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.Attribute;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.visitors.FighterVisitor;

public class Professional extends AbstractFighter {
    public static final String TYPE = "Professional";

    public Professional(String name, ArenaMediator arena, String subType) {
        super(name, arena);
        fighterType = TYPE;
        fighterSubType = subType;
        allowedAttributeTypes = EnumSet.of(AttributeType.LIFE, AttributeType.STRENGTH, 
                AttributeType.DEXTERITY, AttributeType.STAMINA);
    }
    
    @Override
    public Map<String, String> mapify() {
        return KombatLogger.mapBuilder()
                .withPartial(super.mapify())
                .with(STAMINA, Double.toString(getAttribute(AttributeType.STAMINA).map(Attribute::net).orElse(0.0)))
                .with(CURRENT_STAMINA, Double.toString(getAttribute(AttributeType.STAMINA)
                        .map(a -> (VariableAttribute) a).map(VariableAttribute::current).orElse(0.0)))
                .with(STRENGTH, Double.toString(getAttribute(AttributeType.STRENGTH).map(Attribute::net).orElse(0.0)))
                .with(DEXTERITY, Double.toString(getAttribute(AttributeType.DEXTERITY).map(Attribute::net).orElse(0.0)))
                .build();
    }

    @Override
    public void accept(FighterVisitor visitor) {
        visitor.visit(this);
    }

}
