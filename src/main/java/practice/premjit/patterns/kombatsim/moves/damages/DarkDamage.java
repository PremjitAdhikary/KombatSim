package practice.premjit.patterns.kombatsim.moves.damages;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.Map;
import java.util.function.Consumer;

import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.visitors.MoveVisitor;

/**
 * Along with base damage, this permanently deteriorates the primary attribute
 * of the opponent. Point being, longer the fight, the weaker the opponent gets.
 * <br>
 * <br>
 * 
 * @author Premjit Adhikary
 *
 */
public class DarkDamage extends BaseDamage {
    public static final String TYPE = "Dark";
    protected double deteriorationPercentage;

    public DarkDamage(double damageAmount, double deteriorationPercentage) {
        super(damageAmount);
        this.deteriorationPercentage = deteriorationPercentage;
        this.damageType = TYPE;
    }

    @Override
    public void affect(AbstractFighter fighter) {
        super.affect(fighter);
        fighter.getPrimaryAttribute().ifPresent( p -> p.multiply(-(deteriorationPercentage/100)) );
    }

    @Override
    public void accept(MoveVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Map<String, String> mapify() {
        return KombatLogger.mapBuilder()
                .withPartial(super.mapify())
                .with(DETERIORATION_PERCENTAGE, Double.toString(deteriorationPercentage))
                .buildPartial();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public static DarkDamage create(Consumer<DarkDamageBuilder> block) {
        DarkDamageBuilder builder = new DarkDamageBuilder();
        block.accept(builder);
        return builder.build();
    }
    
    public static class DarkDamageBuilder {
        double amount;
        double percentage;
        boolean indirect;
        private DarkDamageBuilder() {}
        
        public DarkDamageBuilder amount(double a) {
            this.amount = a;
            return this;
        }
        
        public DarkDamageBuilder deterioration(double p) {
            this.percentage = p;
            return this;
        }
        
        public DarkDamageBuilder indirect() {
            this.indirect = true;
            return this;
        }
        
        private DarkDamage build() {
            DarkDamage d = new DarkDamage(amount, percentage);
            if (this.indirect) d.setIndirect();
            return d;
        }
    }

}
