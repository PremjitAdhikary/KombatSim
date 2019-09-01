package practice.premjit.patterns.kombatsim.visitors;

import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.fighters.Amateur;
import practice.premjit.patterns.kombatsim.fighters.Hero;
import practice.premjit.patterns.kombatsim.fighters.Mage;
import practice.premjit.patterns.kombatsim.fighters.Professional;

public interface FighterVisitor {
	
	void visit(AbstractFighter a);
	
	void visit(Amateur a);
	
	void visit(Professional p);
	
	void visit(Hero h);
	
	void visit(Mage m);

}
