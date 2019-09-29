package practice.premjit.patterns.kombatsim.attributes;

/**
 * These are are attributes which keep varying as the fight goes on. These act
 * more of a resource rather than a property.
 * 
 * @author Premjit Adhikary
 */
public class VariableAttribute extends Attribute {
    private double current;

    VariableAttribute(AttributeType type, double base) {
        this(type, base, false);
    }

    VariableAttribute(AttributeType type, double base, boolean primary) {
        super(type, base, primary);
        this.current = base;
    }

    VariableAttribute(AttributeType type, double base, double current, boolean primary) {
        super(type, base, primary);
        this.current = current;
    }
    
    public double current() {
        return this.current;
    }
    
    public boolean incrementCurrent(double amount) {
        double prev = this.current;
        this.current += amount;
        double net = net();
        if (this.current > net)
            this.current = net;
        if (this.current <= 0.0) 
            this.current = 0.0;
        return prev != this.current;
    }
    
    @Override
    public void add(double add) {
        super.add(add);
        incrementCurrent(add);
    }
    
    @Override
    public void multiply(double multiply) {
        super.multiply(multiply);
        double add = base() * multiply;
        incrementCurrent(add);
    }

}
