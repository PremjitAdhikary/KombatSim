package practice.premjit.patterns.kombatsim.commands.magic;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.commands.AllActions;
import practice.premjit.patterns.kombatsim.commands.AllReactions;
import practice.premjit.patterns.kombatsim.fighters.Mage;
import practice.premjit.patterns.kombatsim.moves.buffs.AffectVariableAttribute;
import practice.premjit.patterns.kombatsim.moves.buffs.AttributeSteal;
import practice.premjit.patterns.kombatsim.moves.damages.ColdDamage;
import practice.premjit.patterns.kombatsim.moves.damages.DarkDamage;
import practice.premjit.patterns.kombatsim.moves.damages.FireDamage;
import practice.premjit.patterns.kombatsim.moves.damages.ShockDamage;
import practice.premjit.patterns.kombatsim.strategies.magic.SpellBook;

public final class Spells {
	private static final Map<String, Function<Mage, AbstractSpell>> spellMap = new HashMap<>();
	
	// Elemental Mage Spells
	
	private static Function<Mage, AbstractSpell> flameBreath = mage -> {
		ActionSpell spell = new ActionSpell(mage, 
				AllActions.FLAME_BREATH.value(), 
				(SpellBook) mage.getActionStrategy(), 
				() -> FireDamage.create(fire -> fire
							.min(75)
							.max(85)
							.damage(15)
							.duration(5)),
				15);
		spell.setManaCost(35);
		return spell;
	};
	
	private static Function<Mage, AbstractSpell> iceBlast = mage -> {
		ActionSpell spell = new ActionSpell(mage, 
				AllActions.ICE_BLAST.value(), 
				(SpellBook) mage.getActionStrategy(), 
				() -> ColdDamage.create(cold -> cold
								.min(30)
								.deterioration(30)
								.duration(40)),
				20);
		spell.setManaCost(45);
		return spell;
	};
	
	private static Function<Mage, AbstractSpell> fireShield = mage -> {
		ReflectDamageSpell spell = new ReflectDamageSpell(mage, AllReactions.FIRE_SHIELD.value(), 
				(SpellBook) mage.getReactionStrategy(), 30, 55, AllActions.BURN.value());
		spell.setManaCost(10);
		spell.reducePhysicalDamage(40);
		spell.reduceFireDamage(100);
		spell.reduceColdDamage(15);
		spell.reduceShockDamage(20);
		spell.setLimitDamage(35);
		spell.setMoveFunction(
				d -> d > 20 ? 
						FireDamage.create(fire -> fire
								.min(20)
								.max(20)
								.damage(5)
								.duration(2)
								.indirect())
						: null
		);
//		spell.setMoveFunction( d -> {
//			if (d > 20) {
//				FireDamage r = FireDamage.create(fire -> fire
//									.min(20)
//									.max(20)
//									.damage(5)
//									.duration(2));
//				r.setIndirect();
//				return r;
//			}
//			return null;
//		} );
		return spell;
	};
	
	private static Function<Mage, AbstractSpell> frozenWall = mage -> {
		ReflectDamageSpell spell = new ReflectDamageSpell(mage, AllReactions.FROZEN_WALL.value(), 
				(SpellBook) mage.getReactionStrategy(), 35, 65, AllActions.CHILL.value());
		spell.setManaCost(15);
		spell.reducePhysicalDamage(40);
		spell.reduceFireDamage(30);
		spell.reduceColdDamage(100);
		spell.reduceShockDamage(20);
		spell.setLimitDamage(35);
		spell.setMoveFunction(
				d -> d > 20 ?
						ColdDamage.create(cold -> cold
								.min(5)
								.deterioration(10)
								.duration(55)
								.indirect())
						: null
		);
//		spell.setMoveFunction( d -> {
//			if (d > 20) {
//				ColdDamage r = ColdDamage.create(cold -> cold
//										.min(5)
//										.deterioration(10)
//										.duration(55));
//				r.setIndirect();
//				return r;
//			}
//			return null;
//		} );
		return spell;
	};
	
	// Dark Mage Spells
	
	private static Predicate<Mage> critical = mage -> mage.currentLife() < (mage.maxLife() * 0.2);
	
	private static Function<Mage, AbstractSpell> paralyze = mage -> {
		ActionSpell spell = new ActionSpell(mage, 
				AllActions.PARALYZE.value(), 
				(SpellBook) mage.getActionStrategy(), 
				() -> ShockDamage.create(shock -> shock.duration(30).max(2)), 
				50);
		spell.setManaCost(95);
		spell.setCustomCondition(critical.negate());
		return spell;
	};
	
	private static Function<Mage, AbstractSpell> lifeSteal = mage -> {
		ActionSpell spell = new ActionSpell(mage, 
				AllActions.LIFE_STEAL.value(), 
				(SpellBook) mage.getActionStrategy(), 
				() -> new AttributeSteal(mage, 10, 3, AttributeType.LIFE, AttributeType.LIFE), 
				40);
		spell.setManaCost(105);
		spell.setCustomCondition(critical);
		return spell;
	};
	
	private static Function<Mage, AbstractSpell> bleed = mage -> {
		ActionSpell spell = new ActionSpell(mage, 
				AllActions.BLEED.value(), 
				(SpellBook) mage.getActionStrategy(), 
				() -> AffectVariableAttribute.create(buff -> buff
							.affectAttribute(AttributeType.LIFE)
							.duration(30)
							.percentagePerBeat(-1.5)), 
				30);
		spell.setManaCost(75);
		spell.setCustomCondition(critical.negate());
		return spell;
	};
	
	private static Function<Mage, AbstractSpell> thornsCurse = mage -> {
		ReflectDamageSpell spell = new ReflectDamageSpell(mage, AllReactions.THORNS_CURSE.value(), 
				(SpellBook) mage.getReactionStrategy(), 30, 40, AllActions.CURSE.value());
		spell.setManaCost(15);
		spell.reducePhysicalDamage(70);
		spell.reduceFireDamage(70);
		spell.reduceColdDamage(60);
		spell.reduceShockDamage(60);
		spell.setLimitDamage(45);
		spell.setMoveFunction(
				da -> da > 10 ? 
						DarkDamage.create(dark -> dark
								.amount(da/2)
								.deterioration(4)
								.indirect())
						: null
		);
//		spell.setMoveFunction( da -> {
//			if (da > 10) {
//				DarkDamage r = new DarkDamage(da/2, 4);
//				r.setIndirect();
//				return r;
//			}
//			return null;
//		} );
		return spell;
	};
	
	static {
		spellMap.put(AllActions.FLAME_BREATH.value(), flameBreath);
		spellMap.put(AllActions.ICE_BLAST.value(), iceBlast);
		spellMap.put(AllReactions.FIRE_SHIELD.value(), fireShield);
		spellMap.put(AllReactions.FROZEN_WALL.value(), frozenWall);
		spellMap.put(AllActions.PARALYZE.value(), paralyze);
		spellMap.put(AllActions.LIFE_STEAL.value(), lifeSteal);
		spellMap.put(AllActions.BLEED.value(), bleed);
		spellMap.put(AllReactions.THORNS_CURSE.value(), thornsCurse);
	}
	
	private Spells() { }
	
	public static AbstractSpell getSpell(AllActions action, Mage mage) {
		return getSpell(action.value(), mage);
	}
	
	public static AbstractSpell getSpell(AllReactions reaction, Mage mage) {
		return getSpell(reaction.value(), mage);
	}
	
	private static AbstractSpell getSpell(String spell, Mage mage) {
		if (spellMap.containsKey(spell))
			return spellMap.get(spell).apply(mage);
		throw new IllegalArgumentException("Invalid Reaction Spell: "+spell);
	}

}
