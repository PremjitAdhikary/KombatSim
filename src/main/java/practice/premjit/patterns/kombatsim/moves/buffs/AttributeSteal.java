package practice.premjit.patterns.kombatsim.moves.buffs;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.Map;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.beats.VariableAttributeModifier;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.fighters.Mage;
import practice.premjit.patterns.kombatsim.visitors.MoveVisitor;

/**
 * As the name suggests, this is a buff which will steal from a particular
 * attribute of the affected fighter over time and transfer the same (as it is
 * or after conversion) to the caster of the buff.
 * <p>
 * <ul>
 * <li>The buff is temporary.
 * <li>The buff only works on variable attributes.
 * <li>The buff adds two {@link VariableAttributeModifier}s, first to affect the
 * fighter and second to affect the caster.
 * </ul>
 * 
 * @author Premjit Adhikary
 *
 */
public class AttributeSteal extends Buff {
    static final String TYPE = "Steal";
    Mage caster;
    int duration;
    double percentagePerBeat;
    AttributeType steal;
    AttributeType restore;
    
    public AttributeSteal(Mage caster, int duration, double percentagePerBeat, 
            AttributeType steal, AttributeType restore) {
        super();
        buffType = TYPE;
        this.caster = caster;
        this.duration = duration;
        this.percentagePerBeat = percentagePerBeat;
        this.steal = steal;
        this.restore = restore;
    }

    @Override
    public void affect(AbstractFighter fighter) {
        if (!fighter.getAttribute(steal).isPresent())
            return;
        
        double amountPerBeat = fighter.getAttribute(steal).get().base() * percentagePerBeat / 100;
        
        fighter.registerObserver(
                new VariableAttributeModifier(fighter, steal, -amountPerBeat, duration));
        
        caster.registerObserver(
                new VariableAttributeModifier(caster, restore, amountPerBeat, duration));
    }

    @Override
    public void accept(MoveVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Map<String, String> mapify() {
        return KombatLogger.mapBuilder()
                .withPartial(super.mapify())
                .with(ATTRIBUTE_STEAL, steal.name())
                .with(ATTRIBUTE_RESTORE, restore.name())
                .with(DURATION, String.valueOf(duration))
                .with(PERCENTAGE_BEAT, String.valueOf(percentagePerBeat))
                .buildPartial();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
