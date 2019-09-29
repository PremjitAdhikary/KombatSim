package practice.premjit.patterns.kombatsim.moves.buffs;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import practice.premjit.patterns.kombatsim.attributes.Attribute;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.beats.BeatObserver;
import practice.premjit.patterns.kombatsim.beats.FlipFlopObserver;
import practice.premjit.patterns.kombatsim.common.FlipFlopable;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger.LogMapBuilder;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.visitors.MoveVisitor;

/**
 * This is a Buff which affects attribute of the fighter. 
 * <p><ul>
 * <li>The buff can be positive or negative in nature. 
 * <li>The buff can be temporary or permanent in nature. 
 * <li>If attribute to affect is not specified, this will affect the primary attribute.
 * </ul>
 * 
 * @author Premjit Adhikary
 *
 */
public class AffectAttribute extends Buff implements FlipFlopable {
    static final String TYPE_PERMANENT = "Affect Attribute";
    static final String TYPE_TEMPORARY = "Affect Attribute Over Time";
    protected AbstractFighter fighter;
    protected AttributeType attributeType;
    protected AffectType affectType;
    protected Double addend;
    protected Double multiplicand;
    protected Integer duration;
    protected BeatObserver timer;
    protected boolean affectPrimary;
    
    private AffectAttribute(AffectType affectType) {
        this.affectPrimary = true;
        init(affectType);
    }
    
    private AffectAttribute(AttributeType attributeType, AffectType affectType) {
        this.attributeType = attributeType;
        init(affectType);
    }
    
    private void init(AffectType affectType) {
        this.affectType = affectType;
        buffType = (affectType == AffectType.TEMPORARY ? TYPE_TEMPORARY : TYPE_PERMANENT);
    }

    @Override
    public void affect(AbstractFighter fighter) {
        this.fighter = fighter;
        if (affectType == AffectType.TEMPORARY)
            timer = new FlipFlopObserver(duration, fighter, this);
        else
            set();
    }

    @Override
    public void set() {
        Optional<Attribute> optional = affectPrimary ? 
                fighter.getPrimaryAttribute() : fighter.getAttribute(attributeType);
        optional.ifPresent(attr -> {
            if (addend != null) attr.add(addend);
            if (multiplicand != null) attr.multiply(multiplicand);
        });
    }

    @Override
    public void reset() {
        if (affectType == AffectType.PERMANENT)
            return;
        Optional<Attribute> optional = affectPrimary ? 
                fighter.getPrimaryAttribute() : fighter.getAttribute(attributeType);
        optional.ifPresent(attr -> {
            if (addend != null) attr.add(-addend);
            if (multiplicand != null) attr.multiply(-multiplicand);
        });
    }

    @Override
    public Map<String, String> mapify() {
        LogMapBuilder builder = KombatLogger.mapBuilder()
                .withPartial(super.mapify())
                .with(ATTRIBUTE, (affectPrimary ? "Primary" : attributeType.name() ))
                .with(AFFECT, affectType.name());
        if (addend != null)
            builder.with(ADD, addend.toString());
        if (multiplicand != null)
            builder.with(MULTIPLY, multiplicand.toString());
        if (AffectType.TEMPORARY == affectType)
            builder.with(DURATION, duration.toString());
        return builder.buildPartial();
    }

    enum AffectType {
        PERMANENT,
        TEMPORARY
    }

    @Override
    public void accept(MoveVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    // For the type safety builder
    
    public static AffectAttribute create(Consumer<AttributeTypeBuilder> block) {
        AffectAttributeBuilder builder = new AffectAttributeBuilder();
        block.accept(builder);
        return builder.build();
    }
    
    public static interface AttributeTypeBuilder {
        DurationBuilder affectPrimary();
        DurationBuilder affectAttribute(AttributeType type);
    }
    
    public static interface DurationBuilder {
        FinalAffectAttributeBuilder permanent();
        FinalAffectAttributeBuilder duration(int d);
    }
    
    public static interface FinalAffectAttributeBuilder {
        FinalAffectAttributeBuilder addend(double add);
        FinalAffectAttributeBuilder multiplicand(double mult);
    }
    
    public static class AffectAttributeBuilder 
        implements AttributeTypeBuilder, DurationBuilder, FinalAffectAttributeBuilder {
        boolean isPrimary;
        AttributeType type;
        AffectAttribute aa;
        
        private AffectAttributeBuilder() { }

        @Override
        public DurationBuilder affectPrimary() {
            isPrimary = true;
            return this;
        }

        @Override
        public DurationBuilder affectAttribute(AttributeType type) {
            this.type = type;
            return this;
        }

        @Override
        public FinalAffectAttributeBuilder permanent() {
            aa = isPrimary ? 
                    new AffectAttribute(AffectType.PERMANENT) : new AffectAttribute(type, AffectType.PERMANENT);
            return this;
        }

        @Override
        public FinalAffectAttributeBuilder duration(int d) {
            aa = isPrimary ? 
                    new AffectAttribute(AffectType.TEMPORARY) : new AffectAttribute(type, AffectType.TEMPORARY);
            aa.duration = d;
            return this;
        }

        @Override
        public FinalAffectAttributeBuilder addend(double add) {
            aa.addend = add;
            return this;
        }

        @Override
        public FinalAffectAttributeBuilder multiplicand(double mult) {
            aa.multiplicand = mult;
            return this;
        }
        
        private AffectAttribute build() {
            return aa;
        }
        
    }

}
