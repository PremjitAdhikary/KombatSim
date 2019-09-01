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

public class MoveCloner implements MoveVisitor {
	Move clone;
	
	public Move clone(Move move) {
		clone = null;
		move.accept(this);
		return clone;
	}

	@Override
	public void visit(Move m) {
		// do nothing
	}

	@Override
	public void visit(PhysicalDamage p) {
		try {
			clone = (PhysicalDamage) p.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Clone Failed");
		}
	}

	@Override
	public void visit(FireDamage f) {
		try {
			clone = (FireDamage) f.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Clone Failed");
		}
	}

	@Override
	public void visit(ColdDamage c) {
		try {
			clone = (ColdDamage) c.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Clone Failed");
		}
	}

	@Override
	public void visit(ShockDamage s) {
		try {
			clone = (ShockDamage) s.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Clone Failed");
		}
	}

	@Override
	public void visit(Buff b) {
		// do nothing
	}

	@Override
	public void visit(AffectAttribute aa) {
		try {
			clone = (AffectAttribute) aa.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Clone Failed");
		}
	}

	@Override
	public void visit(AffectVariableAttribute ave) {
		try {
			clone = (AffectVariableAttribute) ave.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Clone Failed");
		}
	}

	@Override
	public void visit(AttributeSteal as) {
		try {
			clone = (AttributeSteal) as.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Clone Failed");
		}
	}

}
