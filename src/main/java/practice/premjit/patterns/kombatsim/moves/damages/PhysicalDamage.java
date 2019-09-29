package practice.premjit.patterns.kombatsim.moves.damages;

import java.util.function.Consumer;

import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.visitors.MoveVisitor;

/**
 * Marking the damage as Physical in nature. Most common form of damage.
 * 
 * @author Premjit Adhikary
 *
 */
public class PhysicalDamage extends BaseDamage implements Cloneable {
    public static final String TYPE = "Physical";
    
    private PhysicalDamage(double damage) {
        super(damage);
        damageType = TYPE;
    }

    @Override
    public void accept(MoveVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public static PhysicalDamage create(Consumer<PhysicalDamageBuilder> block) {
        PhysicalDamageBuilder builder = new PhysicalDamageBuilder();
        block.accept(builder);
        return builder.build();
    }
    
    public static class PhysicalDamageBuilder {
        double min;
        double max;
        boolean indirect;
        private PhysicalDamageBuilder() {}
        
        public PhysicalDamageBuilder min(double m) {
            this.min = m;
            return this;
        }
        
        public PhysicalDamageBuilder max(double m) {
            this.max = m;
            return this;
        }
        
        public PhysicalDamageBuilder indirect() {
            this.indirect = true;
            return this;
        }
        
        private PhysicalDamage build() {
            PhysicalDamage d = new PhysicalDamage(Randomizer.randomDoubleInRange(min, max));
            if (this.indirect) d.setIndirect();
            return d;
        }
    }

}
