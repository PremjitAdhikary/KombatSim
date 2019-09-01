package practice.premjit.patterns.kombatsim.fighters.decorators;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.commands.AllActions;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.moves.buffs.AffectAttribute;
import practice.premjit.patterns.kombatsim.moves.buffs.AffectVariableAttribute;
import practice.premjit.patterns.kombatsim.moves.damages.ColdDamage;
import practice.premjit.patterns.kombatsim.moves.damages.FireDamage;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;
import practice.premjit.patterns.kombatsim.moves.damages.ShockDamage;

public final class AllDecorators {
	public static final String ENHANCED_ARMS = "ENHANCED_ARMS";
	public static final String NINJA = "NINJA";
	public static final String SAMURAI = "SAMURAI";
	public static final String BATMAN = "BATMAN";
	
	private static final Map<String, Function<AbstractFighter, AbstractFighterDecorator>> decoratorMap 
		= new HashMap<>();
	
	// Metal
	
	private static Function<AbstractFighter, AbstractFighterDecorator> enhancedArms = boxer -> 
		AttributeEnhancer.create( metalArms ->
			metalArms
				.toEnhance(boxer)
				.multiply(AttributeType.STRENGTH).by(1.5)
				.addTo(AttributeType.LIFE).value(40.0)
		);
	
	// Samurai
		
	private static Function<AbstractFighter, AbstractFighterDecorator> samuraiArmor = samurai -> 
		Armor.create( aSamuraiArmor -> 
			aSamuraiArmor
				.toProtect(samurai)
				.armorLife(30)
				.damageReductionMultiplier(0.3)
				.enablePhysicalDamageReduction()
		);
		
	// Currying
	private static IntFunction<Function<AbstractFighter, Move>> swordMove = 
		base -> f -> 
			PhysicalDamage.create(damage -> damage
					.min( base + (f.getPrimaryAttribute().get().base() * 0.5) )
					.max( base + 20 + (f.getPrimaryAttribute().get().base() * 0.6) )
			);
		
	private static Function<AbstractFighter, AbstractFighterDecorator> samuraiSword = samurai -> 
		Weapon.create( aSword -> 
			aSword
				.name("Sword")
				.wielder(samurai)
				.addCommand()
				.withName(AllActions.SWORD_SLASH.value())
				.andMove(swordMove.apply(25))
				.addCommand()
				.withName(AllActions.SWORD_CUT.value())
				.andMove(swordMove.apply(35))
				.replaceFighterAttacks()
		);
	
	private static Function<AbstractFighter, AbstractFighterDecorator> aSamurai = samuraiArmor.andThen(samuraiSword);
	
	// Ninja
	
	private static Function<AbstractFighter, AbstractFighterDecorator> enhancedNinja = ninja -> 
		AttributeEnhancer.create( enhancer -> 
			enhancer.toEnhance(ninja).addTo(AttributeType.DEXTERITY).value(30)
		);
		
	private static Function<AbstractFighter, AbstractFighterDecorator> nunChucks = ninja -> 
		Weapon.create( nunChacku -> 
			nunChacku
				.name("Nunchucks")
				.wielder(ninja)
				.addCommand()
				.withName(AllActions.NUNCHUCK_SWING.value())
				.andMove(
					f -> PhysicalDamage.create(damage -> damage
						.min(40 + f.getPrimaryAttribute().get().base() * 0.4)
						.max(50 + f.getPrimaryAttribute().get().base() * 0.5)
					)
				)
		);
	
	private static Function<AbstractFighter, AbstractFighterDecorator> shurikens = ninja -> 
		Projectiles.create( projectile -> 
			projectile
				.forFighter(ninja)
				.name(AllActions.SHURIKEN.value())
				.count(10)
				.move( () -> PhysicalDamage.create(damage -> damage.min(50).max(75)) )
		);
	
	private static Function<AbstractFighter, AbstractFighterDecorator> aNinja = 
			enhancedNinja.andThen(nunChucks).andThen(shurikens);
	
	// batman
	
	private static Function<AbstractFighter, AbstractFighterDecorator> enhancedBatman = batman -> 
		AttributeEnhancer.create( enhancer ->
			enhancer
				.toEnhance(batman)
				.multiply(AttributeType.STRENGTH).by(0.5)
				.addTo(AttributeType.DEXTERITY).value(10.0)
		);

		
	private static Function<AbstractFighter, AbstractFighterDecorator> batArmor = batman -> 
		Armor.create( armor -> 
			armor
				.toProtect(batman)
				.armorLife(200)
				.damageReductionMultiplier(0.6)
				.enablePhysicalDamageReduction()
				.enableFireDamageReduction()
				.enableShockDamageReduction()
				.enableColdDamageReduction()
		);
	
	private static Function<AbstractFighter, AbstractFighterDecorator> batsuit = enhancedBatman.andThen(batArmor);
	
	private static Function<AbstractFighter, AbstractFighterDecorator> batarang = batman -> 
		Projectiles.create( projectile -> 
			projectile
				.forFighter(batman)
				.name(AllActions.BATARANG.value())
				.count(5)
				.move( () -> PhysicalDamage.create(damage -> damage.min(45).max(65)) )
		);
		
	private static Function<AbstractFighter, AbstractFighterDecorator> thermitePellets = batman -> 
		Projectiles.create( projectile -> 
			projectile
				.forFighter(batman)
				.name(AllActions.THERMITE_PELLETS.value())
				.count(5)
				.move( () -> FireDamage.create(damage -> damage.min(20).max(35).damage(15).duration(5)) )
		);
		
	private static Function<AbstractFighter, AbstractFighterDecorator> freezeGrenades = batman -> 
		Projectiles.create( projectile -> 
			projectile
				.forFighter(batman)
				.name(AllActions.FREEZE_GRENADES.value())
				.count(5)
				.move( () -> ColdDamage.create(damage -> damage.min(10).deterioration(60).duration(25)) )
		);
		
	private static Function<AbstractFighter, AbstractFighterDecorator> stunPellets = batman -> 
		Projectiles.create( projectile -> 
			projectile
				.forFighter(batman)
				.name(AllActions.STUN_PELLETS.value())
				.count(5)
				.move( () -> ShockDamage.create(damage -> damage.max(80).duration(35)) )
		);
		
	private static Function<AbstractFighter, AbstractFighterDecorator> gasPellets = batman -> 
		Projectiles.create( projectile -> 
			projectile
				.forFighter(batman)
				.name(AllActions.GAS_PELLETS.value())
				.count(5)
				.move( () -> AffectVariableAttribute.create(buff -> 
						buff.affectAttribute(AttributeType.LIFE).duration(25).percentagePerBeat(-3)
					) 
				)
		);
		
	private static Function<AbstractFighter, AbstractFighterDecorator> manaBurners = batman -> 
		Projectiles.create( projectile -> 
			projectile
				.forFighter(batman)
				.name(AllActions.MANA_BURNERS.value())
				.count(5)
				.move( () -> AffectVariableAttribute.create(buff -> 
						buff.affectAttribute(AttributeType.MANA).duration(20).percentagePerBeat(-4)
					) 
				)
		);
		
	private static Function<AbstractFighter, AbstractFighterDecorator> kryptonite = batman -> 
		Projectiles.create( projectile -> 
			projectile
				.forFighter(batman)
				.name(AllActions.KRYPTONITE.value())
				.count(5)
				.move( () -> AffectAttribute.create(buff -> 
						buff.affectAttribute(AttributeType.STRENGTH).duration(55).multiplicand(-0.8)
					) 
				)
		);
		
	private static Function<AbstractFighter, AbstractFighterDecorator> healPill = batman -> 
		Projectiles.create( projectile -> 
			projectile
				.forFighter(batman)
				.name(AllActions.HEAL_PILL.value())
				.count(2)
				.move( () -> AffectVariableAttribute.create(buff -> 
						buff.affectAttribute(AttributeType.LIFE).duration(5).percentagePerBeat(6)
					) 
				)
				.recipient(Recipient.SELF)
		);
	
	private static Function<AbstractFighter, AbstractFighterDecorator> utilityBelt = 
			batarang
				.andThen(thermitePellets)
				.andThen(freezeGrenades)
				.andThen(stunPellets)
				.andThen(gasPellets)
				.andThen(manaBurners)
				.andThen(kryptonite)
				.andThen(healPill);
	
	private static Function<AbstractFighter, AbstractFighterDecorator> equippedBatman = 
			batsuit.andThen(utilityBelt);
	
	static {
		decoratorMap.put(ENHANCED_ARMS, enhancedArms);
		decoratorMap.put(NINJA, aNinja);
		decoratorMap.put(SAMURAI, aSamurai);
		decoratorMap.put(BATMAN, equippedBatman);
	}
	
	private AllDecorators() { }
	
	public static AbstractFighterDecorator getDecorator(String decorator, AbstractFighter fighter) {
		if (decoratorMap.containsKey(decorator)) {
			return decoratorMap.get(decorator).apply(fighter);
		}
		throw new IllegalArgumentException("Invalid decorator requested: " + decorator);
	}

}
