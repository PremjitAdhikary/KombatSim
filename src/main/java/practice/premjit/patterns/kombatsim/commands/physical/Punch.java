package practice.premjit.patterns.kombatsim.commands.physical;

import practice.premjit.patterns.kombatsim.attributes.Attribute;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.commands.AllActions;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;

/**
 * Punches are determined by Fighters strength. Higher strength, higher damage inflicted
 * 
 * @author Premjit Adhikary
 *
 */
public class Punch extends AbstractPhysicalActionCommand {
    protected static final double MIN_DAMAGE = 10.0;
    protected static final double MAX_DAMAGE = 15.0;
    
    public Punch(AbstractFighter fighter) {
        super(fighter);
        this.name = AllActions.PUNCH.value();
    }

    @Override
    public boolean canBeExecuted() {
        return fighterHasStrength();
    }
    
    @Override
    protected PhysicalDamage calculatePhysicalDamage() {
        double damageFromStrength = fighter.getAttribute(AttributeType.STRENGTH).map(Attribute::net).orElse(0.0) * 0.25;
        
        double min = MIN_DAMAGE + damageFromStrength;
        double max = MAX_DAMAGE + damageFromStrength;
        
        return PhysicalDamage.create(b -> b.min(min).max(max));
    }
    
    private boolean fighterHasStrength() {
        return fighter.getAttribute(AttributeType.STRENGTH).isPresent();
    }

}
