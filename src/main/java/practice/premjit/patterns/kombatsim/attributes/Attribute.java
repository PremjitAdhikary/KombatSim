package practice.premjit.patterns.kombatsim.attributes;

/**
 * Attributes give personality to the fighters. Depending on type of attribute and amount of it, the way the 
 * fighter fights changes.
 * 
 * @author Premjit Adhikary
 */
public class Attribute {
	private AttributeType type;
	private double base;
	private double addend;
	private double multiplicand;
	private boolean primary;

	Attribute(AttributeType type, double base) {
		this(type, base, false);
	}

	Attribute(AttributeType type, double base, boolean primary) {
		this.type = type;
		this.base = base;
		this.primary = primary;
		this.addend = 0;
		this.multiplicand = 1.0;
	}
	
	public double base() {
		return this.base;
	}
	
	public AttributeType type() {
		return this.type;
	}
	
	public double net() {
		double net = this.base * this.multiplicand + this.addend;
		return (net > 0 ? net : 0);
	}
	
	public void add(double add) {
		this.addend += add;
	}
	
	public void multiply(double multiply) {
		this.multiplicand += multiply;
	}
	
	public boolean isPrimary() {
		return this.primary;
	}
}
