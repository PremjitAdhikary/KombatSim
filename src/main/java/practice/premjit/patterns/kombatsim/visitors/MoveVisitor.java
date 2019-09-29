package practice.premjit.patterns.kombatsim.visitors;

import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.buffs.AffectAttribute;
import practice.premjit.patterns.kombatsim.moves.buffs.AffectVariableAttribute;
import practice.premjit.patterns.kombatsim.moves.buffs.AttributeSteal;
import practice.premjit.patterns.kombatsim.moves.buffs.Buff;
import practice.premjit.patterns.kombatsim.moves.damages.ColdDamage;
import practice.premjit.patterns.kombatsim.moves.damages.FireDamage;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;
import practice.premjit.patterns.kombatsim.moves.damages.ShockDamage;

public interface MoveVisitor {
    
    void visit(Move m);
    
    void visit(PhysicalDamage p);
    
    void visit(FireDamage f);
    
    void visit(ColdDamage c);
    
    void visit(ShockDamage s);
    
    void visit(Buff b);
    
    void visit(AffectAttribute aa);
    
    void visit(AffectVariableAttribute ave);
    
    void visit(AttributeSteal as);

}
