package practice.premjit.patterns.kombatsim.moves.buffs;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.Map;
import java.util.function.Consumer;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.beats.VariableAttributeModifier;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger.LogMapBuilder;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.buffs.AffectAttribute.AffectType;
import practice.premjit.patterns.kombatsim.visitors.MoveVisitor;

/**
 * This is a Buff which affects variable attribute of the fighter. 
 * <p><ul>
 * <li>The buff can be positive or negative in nature. 
 * <li>The buff can be temporary or permanent in nature. 
 * <li>The buff adds a {@link VariableAttributeModifier} to affect the fighter.
 * </ul>
 * 
 * @author Premjit Adhikary
 *
 */
public class AffectVariableAttribute extends Buff {
    static final String TYPE_PERMANENT = "Affect Variable Attribute";
    static final String TYPE_TEMPORARY = "Affect Variable Attribute Over Time";
    protected AttributeType attributeType;
    protected AffectType affectType;
    protected int duration;
    protected double percentagePerBeat;
    
    private AffectVariableAttribute(AttributeType attributeType, AffectType affectType) {
        this.attributeType = attributeType;
        this.affectType = affectType;
        buffType = (affectType == AffectType.TEMPORARY ? TYPE_TEMPORARY : TYPE_PERMANENT);
    }

    @Override
    public void affect(AbstractFighter fighter) {
        if (!fighter.getAttribute(attributeType).isPresent()) 
            return;
        
        double amountPerBeat = fighter.getAttribute(attributeType).get().base() * percentagePerBeat / 100;
        
        VariableAttributeModifier vam = affectType == AffectType.TEMPORARY ? 
                new VariableAttributeModifier(fighter, attributeType, amountPerBeat, duration) : 
                    new VariableAttributeModifier(fighter, attributeType, amountPerBeat) ;
        
        fighter.registerObserver(vam);
    }

    @Override
    public void accept(MoveVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Map<String, String> mapify() {
        LogMapBuilder builder = KombatLogger.mapBuilder()
                .withPartial(super.mapify())
                .with(ATTRIBUTE, attributeType.name())
                .with(AFFECT, affectType.name());
        if (AffectType.TEMPORARY == affectType)
            builder.with(DURATION, String.valueOf(duration));
        return builder
                .with(PERCENTAGE_BEAT, String.valueOf(percentagePerBeat))
                .buildPartial();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    // For the type safety builder
    
    public static AffectVariableAttribute create(Consumer<VariableAttributeTypeBuilder> block) {
        AffectVariableAttributeBuilder builder = new AffectVariableAttributeBuilder();
        block.accept(builder);
        return builder.build();
    }
    
    public static interface VariableAttributeTypeBuilder {
        DurationBuilder affectAttribute(AttributeType type);
    }
    
    public static interface DurationBuilder {
        FinalAffectVariableAttributeBuilder permanent();
        FinalAffectVariableAttributeBuilder duration(int d);
    }
    
    public static interface FinalAffectVariableAttributeBuilder {
        FinalAffectVariableAttributeBuilder percentagePerBeat(double p);
    }
    
    public static class AffectVariableAttributeBuilder 
            implements VariableAttributeTypeBuilder, DurationBuilder, FinalAffectVariableAttributeBuilder {
        AttributeType type;
        AffectVariableAttribute ava;
        
        private AffectVariableAttributeBuilder() { }

        @Override
        public DurationBuilder affectAttribute(AttributeType type) {
            this.type = type;
            return this;
        }

        @Override
        public FinalAffectVariableAttributeBuilder permanent() {
            ava = new AffectVariableAttribute(type, AffectType.PERMANENT);
            return this;
        }

        @Override
        public FinalAffectVariableAttributeBuilder duration(int d) {
            ava = new AffectVariableAttribute(type, AffectType.TEMPORARY);
            ava.duration = d;
            return this;
        }

        @Override
        public FinalAffectVariableAttributeBuilder percentagePerBeat(double p) {
            ava.percentagePerBeat = p;
            return this;
        }
        
        private AffectVariableAttribute build() {
            return ava;
        }
        
    }

}
