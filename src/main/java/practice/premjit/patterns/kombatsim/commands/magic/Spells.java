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
	
	private static Function<Mage, AbstractSpell> flameBreath = mage -> 
		ActionSpell.create( action -> 
			action
				.mage(mage)
				.name(AllActions.FLAME_BREATH.value())
				.book((SpellBook) mage.getActionStrategy())
				.move( () -> FireDamage.create( fire -> fire.min(75).max(85).damage(15).duration(5) ) )
				.cooldown(15)
				.manaCost(35)
		);
		
	private static Function<Mage, AbstractSpell> iceBlast = mage -> 
		ActionSpell.create( action -> 
			action
				.mage(mage)
				.name(AllActions.ICE_BLAST.value())
				.book((SpellBook) mage.getActionStrategy())
				.move( () -> ColdDamage.create( cold -> cold.min(30).deterioration(30).duration(40) ) )
				.cooldown(20)
				.manaCost(45)
		);
		
	private static Function<Mage, AbstractSpell> fireShield = mage -> 
		ReflectDamageSpell.create( reaction -> 
			reaction
				.mage(mage)
				.name(AllReactions.FIRE_SHIELD.value())
				.book((SpellBook) mage.getReactionStrategy())
				.reflectMoveName(AllActions.BURN.value())
				.reflectMove( d -> 
					d <= 20 ? null : 
						FireDamage.create( fire -> fire.min(20).max(20).damage(5).duration(2).indirect() )
				)
				.cooldown(30)
				.activeDuration(55)
				.manaCost(10)
				.reducePhysicalDamage(40)
				.reduceFireDamage(100)
				.reduceColdDamage(15)
				.reduceShockDamage(20)
				.limitDamage(35)
		);
		
	private static Function<Mage, AbstractSpell> frozenWall = mage -> 
		ReflectDamageSpell.create( reaction -> 
			reaction
				.mage(mage)
				.name(AllReactions.FROZEN_WALL.value())
				.book((SpellBook) mage.getReactionStrategy())
				.reflectMoveName(AllActions.CHILL.value())
				.reflectMove( d -> 
					d <= 20 ? null : 
						ColdDamage.create( cold -> cold.min(5).deterioration(10).duration(55).indirect() )
				)
				.cooldown(35)
				.activeDuration(65)
				.manaCost(15)
				.reducePhysicalDamage(40)
				.reduceFireDamage(30)
				.reduceColdDamage(100)
				.reduceShockDamage(20)
				.limitDamage(35)
		);
	
	// Dark Mage Spells
	
	private static Predicate<Mage> critical = mage -> mage.currentLife() < (mage.maxLife() * 0.2);
	
	private static Function<Mage, AbstractSpell> paralyze = mage -> 
		ActionSpell.create( action -> 
			action
				.mage(mage)
				.name(AllActions.PARALYZE.value())
				.book((SpellBook) mage.getActionStrategy())
				.move( () -> ShockDamage.create(shock -> shock.duration(30).max(2)) )
				.cooldown(50)
				.executeCondition( critical.negate() )
				.manaCost(95)
		);
		
	private static Function<Mage, AbstractSpell> lifeSteal = mage -> 
		ActionSpell.create( action -> 
		action
			.mage(mage)
			.name(AllActions.LIFE_STEAL.value())
			.book((SpellBook) mage.getActionStrategy())
			.move( () -> new AttributeSteal(mage, 10, 3, AttributeType.LIFE, AttributeType.LIFE) )
			.cooldown(40)
			.executeCondition(critical)
			.manaCost(105)
	);
		
	private static Function<Mage, AbstractSpell> bleed = mage -> 
		ActionSpell.create( action -> 
		action
			.mage(mage)
			.name(AllActions.BLEED.value())
			.book((SpellBook) mage.getActionStrategy())
			.move( () -> AffectVariableAttribute.create(buff -> 
					buff.affectAttribute(AttributeType.LIFE).duration(30).percentagePerBeat(-1.5)
				) 
			)
			.cooldown(30)
			.executeCondition( critical.negate() )
			.manaCost(75)
	);
		
	private static Function<Mage, AbstractSpell> thornsCurse = mage -> 
		ReflectDamageSpell.create( reaction -> 
			reaction
				.mage(mage)
				.name(AllReactions.THORNS_CURSE.value())
				.book((SpellBook) mage.getReactionStrategy())
				.reflectMoveName(AllActions.CURSE.value())
				.reflectMove( da -> 
					da <= 10 ? null : 
						DarkDamage.create( dark -> dark.amount(da/2).deterioration(4).indirect() )
				)
				.cooldown(30)
				.activeDuration(40)
				.manaCost(15)
				.reducePhysicalDamage(70)
				.reduceFireDamage(70)
				.reduceColdDamage(60)
				.reduceShockDamage(60)
				.limitDamage(45)
		);
	
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
