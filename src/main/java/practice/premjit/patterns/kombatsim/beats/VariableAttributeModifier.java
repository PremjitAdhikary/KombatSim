package practice.premjit.patterns.kombatsim.beats;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;

/**
 * Basic idea here is to enable regeneration of {@link VariableAttribute} over
 * time.
 * <p>
 * <ul>
 * <li>Can be temporary or permanent.
 * <li>If negative {@link VariableAttributeModifier#amountPerBeat amountPerBeat}
 * is specified, instead of regenerate, it will degenerate.
 * <li>Works only on VariableAttribute.
 * <li>Latches on the fighters heartbeat to update.
 * </ul>
 * 
 * @author Premjit Adhikary
 *
 */
public class VariableAttributeModifier implements BeatObserver {
    protected VariableAttribute attribute;
    protected double amountPerBeat;
    protected int beats;
    protected AbstractFighter fighter;
    private int beatCount;
    
    public VariableAttributeModifier(VariableAttribute attribute, double amountPerBeat) {
        this.attribute = attribute;
        this.amountPerBeat = amountPerBeat;
    }
    
    public VariableAttributeModifier(AbstractFighter fighter, AttributeType type, double amountPerBeat) {
        this(fighter, type, amountPerBeat, Integer.MAX_VALUE);
    }
    
    public VariableAttributeModifier(AbstractFighter fighter, AttributeType type, double amountPerBeat, int beats) {
        this.fighter = fighter;
        fighter.getAttribute(type)
            .map(a -> attribute = (VariableAttribute) a)
            .orElseThrow(() -> new IllegalStateException("Fighter has no " + type));
        this.amountPerBeat = amountPerBeat;
        this.beats = beats;
    }

    @Override
    public void update() {
        if (beatCount >= beats)
            return;
        
        beatCount++;
        boolean success = attribute.incrementCurrent(amountPerBeat);
        if (success && fighter != null) {
            KombatLogger.getLogger().log(KombatLogger.LEVEL.MEDIUM, KombatLogger.EVENT_TYPE.UPDATE, 
                    fighter.mapify(), KombatLogger.mapBuilder().withName("Attribute Updated").build());
        }
        
        if (beatCount == beats && fighter != null) {
            fighter.unregisterObserver(this);
        }
    }

}
