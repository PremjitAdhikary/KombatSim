package practice.premjit.patterns.kombatsim.commands.professional;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.commands.physical.AbstractPhysicalActionCommand;
import practice.premjit.patterns.kombatsim.common.RangeValueMap;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;

/**
 * While Physical in nature, this can only be executed by
 * {@link practice.premjit.patterns.kombatsim.fighters.Professional
 * professionals} (who have the attribute stamina). The extent of damage is
 * determined by the amount of stamina present.
 * <p>
 * 
 * Note that every action has a cost (on stamina) associated with it.
 * <p>
 * 
 * @author Premjit Adhikary
 *
 */
public class StaminaBasedAction extends AbstractPhysicalActionCommand {
    private static int diff = 10;
    private RangeValueMap staminaRange;

    public StaminaBasedAction(AbstractFighter fighter, String name) {
        super(fighter);
        this.name = name;
        setupStaminaRange();
    }

    @Override
    public boolean canBeExecuted() {
        return fighterHasEnoughStamina();
    }

    @Override
    protected PhysicalDamage calculatePhysicalDamage() {
        double amount = getDamageAmount();
        chargeStamina(amount);
        
        return PhysicalDamage.create(d -> d.min(amount).max(amount + diff));
    }
    
    private void setupStaminaRange() {
        fighter.getAttribute(AttributeType.STAMINA)
            .ifPresent(s -> 
                staminaRange = RangeValueMap.builder()
                    .addRangeValue(s.base() * 0.4, s.base() * 0.4)
                    .addRangeValue(s.base() * 0.6, s.base() * 0.6)
                    .addRangeValue(s.base() * 0.8, s.base() * 0.8)
                    .build()
            );
    }
    
    private boolean fighterHasEnoughStamina() {
        return staminaRange != null 
                && staminaRange.hasValue(getFighterStamina().current());
    }
    
    private double getDamageAmount() {
        return staminaRange.getValue(getFighterStamina().current());
    }
    
    private void chargeStamina(double amount) {
        getFighterStamina().incrementCurrent(-amount);
    }
    
    private VariableAttribute getFighterStamina() {
        return (VariableAttribute) fighter.getAttribute(AttributeType.STAMINA)
                .orElse(AttributeUtility.buildStamina(0.0, 0.0));
    }

}
