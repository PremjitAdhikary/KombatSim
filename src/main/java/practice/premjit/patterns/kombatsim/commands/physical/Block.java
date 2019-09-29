package practice.premjit.patterns.kombatsim.commands.physical;

import java.util.Optional;

import practice.premjit.patterns.kombatsim.attributes.Attribute;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.commands.AllReactions;
import practice.premjit.patterns.kombatsim.common.RangeValueMap;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;

/**
 * Blocks are determined by Fighters strength. Higher strength, less damage to the fighter.
 * 
 * @author Premjit Adhikary
 *
 */
public class Block extends AbstractPhysicalReactionCommand {
    
    protected static final RangeValueMap STRENGTH_RANGE_AND_DAMAGE = RangeValueMap.builder()
            .addRangeValue(   0.0,   0.0)
            .addRangeValue(   1.0,   1.0)
            .addRangeValue(  10.0,   2.0)
            .addRangeValue(  20.0,   5.0)
            .addRangeValue(  50.0,  10.0)
            .addRangeValue( 100.0,  20.0)
            .addRangeValue( 200.0,  30.0)
            .addRangeValue( 500.0,  50.0)
            .addRangeValue(1000.0,  75.0)
            .build();
    
    public Block(AbstractFighter fighter) {
        super(fighter);
        this.name = AllReactions.BLOCK.value();
    }
    
    @Override
    public boolean canBeExecuted(Optional<Move> move) {
        return super.canBeExecuted(move) && fighterHasStrength();
    }

    @Override
    public void reduceDamage(PhysicalDamage physicalDamage) {
        double rawStrength = fighter.getAttribute(AttributeType.STRENGTH).map(Attribute::net).orElse(0.0);
        
        double damageReducedByRawStrength = rawStrength * 0.2;
        double damageLeft = physicalDamage.amount() - damageReducedByRawStrength;
                
        double damageReducedFromStrengthPercentage = STRENGTH_RANGE_AND_DAMAGE.getValue(rawStrength);
        double damageAmountReducedFromStrength = damageLeft * damageReducedFromStrengthPercentage / 100;
        double totalDamageReduced = damageReducedByRawStrength + damageAmountReducedFromStrength;
        
        physicalDamage.incrementBy(-totalDamageReduced);
    }
    
    private boolean fighterHasStrength() {
        return fighter.getAttribute(AttributeType.STRENGTH).isPresent();
    }

}
