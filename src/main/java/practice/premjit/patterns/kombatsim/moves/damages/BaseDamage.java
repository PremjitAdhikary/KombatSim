package practice.premjit.patterns.kombatsim.moves.damages;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.Map;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.common.logging.Loggable;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;

/**
 * Base damage which affects the life of the opponent
 * <p>
 * 
 * {@link #indirect} is to indicate if Damage is not direct from the attacker.
 * For example, if Damage is reflected from Mages shield, this would be true.
 * 
 * @author Premjit Adhikary
 *
 */
public abstract class BaseDamage implements Move, Loggable, Cloneable {
    protected double damageAmount;
    protected String damageType;
    protected boolean indirect;

    protected BaseDamage(double damageAmount) {
        this.damageAmount = damageAmount;
    }
    
    public double amount() {
        return this.damageAmount;
    }
    
    public void incrementBy(double incr) {
        this.damageAmount += incr;
        if (damageAmount < 0)
            damageAmount = 0;
    }

    public boolean isIndirect() {
        return indirect;
    }

    protected void setIndirect() {
        indirect = true;
    }

    public void clearIndirect() {
        indirect = false;
    }

    @Override
    public Map<String, String> mapify() {
        return KombatLogger.mapBuilder()
                .with(DAMAGE_TYPE, damageType)
                .with(DAMAGE_AMOUNT, Double.toString(this.damageAmount))
                .buildPartial();
    }

    @Override
    public void affect(AbstractFighter fighter) {
        fighter.getAttribute(AttributeType.LIFE).ifPresent(
                life -> ((VariableAttribute) life).incrementCurrent(-damageAmount));
    }

}
