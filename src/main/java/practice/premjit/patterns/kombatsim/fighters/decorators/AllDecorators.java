package practice.premjit.patterns.kombatsim.fighters.decorators;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.commands.AllActions;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
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
	
	private static Function<AbstractFighter, AbstractFighterDecorator> enhancedArms = fighter -> {
		AttributeEnhanacer enhancer = new AttributeEnhanacer(fighter);
		enhancer.enhanceWithMultiplier(AttributeType.STRENGTH, 1.5);
		enhancer.enhanceWithAdder(AttributeType.LIFE, 40.0);
		enhancer.equip();
		return enhancer;
	};
	
	// Samurai
	
	private static Function<AbstractFighter, AbstractFighterDecorator> samuraiArmor = fighter -> {
		Armor armor = new Armor(fighter, 30, 0.3);
		armor.enablePhysicalDamageReduction();
		armor.equip();
		return armor;
	};
	
	private static Function<AbstractFighter, AbstractFighterDecorator> samuraiSword = fighter -> {
		Weapon sword = new Weapon(fighter, "Sword");
		sword.replaceActions();
		
		sword.buildAndAddWeaponCommand(AllActions.SWORD_SLASH.value(), swordMove(25));
		sword.buildAndAddWeaponCommand(AllActions.SWORD_CUT.value(), swordMove(35));
		
		sword.equip();
		return sword;
	};
	
	private static Function<AbstractFighter, Move> swordMove(double base) {
		return f -> {
			VariableAttribute stamina = (VariableAttribute) f.getPrimaryAttribute().get();
			double add = stamina.base() * 0.5;
			return PhysicalDamage.create(damage -> damage
					.min(base + add)
					.max(base + add + 20));
			};
	}
	
	private static Function<AbstractFighter, AbstractFighterDecorator> aSamurai = samuraiArmor.andThen(samuraiSword);
	
	// Ninja
	
	private static Function<AbstractFighter, AbstractFighterDecorator> enhancedNinja = fighter -> {
		AttributeEnhanacer enhancer = new AttributeEnhanacer(fighter);
		enhancer.enhanceWithAdder(AttributeType.DEXTERITY, 30);
		enhancer.equip();
		return enhancer;
	};
	
	private static Function<AbstractFighter, AbstractFighterDecorator> nunChucks = fighter -> {
		Weapon weapon = new Weapon(fighter, "Nunchucks");
		
		weapon.buildAndAddWeaponCommand(
				AllActions.NUNCHUCK_SWING.value(), 
				f -> {
					double base = 40;
					double add = f.getPrimaryAttribute().get().base() * 0.4;
					return PhysicalDamage.create(damage -> damage
							.min(base + add)
							.max(base + add + 10));
					});
		
		weapon.equip();
		return weapon;
	};
	
	private static Function<AbstractFighter, AbstractFighterDecorator> shurikens = fighter -> {
		Projectiles projectiles = new Projectiles(fighter);
		projectiles.setProjectileName(AllActions.SHURIKEN.value());
		projectiles.setProjectileCount(10);
		projectiles.setMoveSupplier(
				() -> PhysicalDamage.create(damage -> damage
							.min(50)
							.max(75))
				);
		projectiles.equip();
		return projectiles;
	};
	
	private static Function<AbstractFighter, AbstractFighterDecorator> aNinja = 
			enhancedNinja.andThen(nunChucks).andThen(shurikens);
	
	// batman
	
	private static Function<AbstractFighter, AbstractFighterDecorator> enhancedBatman = fighter -> {
		AttributeEnhanacer enhancedAbilities = new AttributeEnhanacer(fighter);
		enhancedAbilities.enhanceWithMultiplier(AttributeType.STRENGTH, .5);
		enhancedAbilities.enhanceWithAdder(AttributeType.DEXTERITY, 10);
		enhancedAbilities.equip();
		return enhancedAbilities;
	};
	
	private static Function<AbstractFighter, AbstractFighterDecorator> batArmor = fighter -> {
		Armor armor = new Armor(fighter, 200, .6);
		armor.enablePhysicalDamageReduction();
		armor.enableFireDamageReduction();
		armor.enableShockDamageReduction();
		armor.enableColdDamageReduction();
		armor.equip();
		return armor;
	};
	
	private static Function<AbstractFighter, AbstractFighterDecorator> batsuit = enhancedBatman.andThen(batArmor);
	
	private static Function<AbstractFighter, AbstractFighterDecorator> batarang = fighter -> {
		Projectiles projectiles = new Projectiles(fighter);
		projectiles.setProjectileName(AllActions.BATARANG.value());
		projectiles.setProjectileCount(5);
		projectiles.setMoveSupplier(() -> PhysicalDamage.create(d -> d.min(45).max(65)));
		projectiles.equip();
		return projectiles;
	};
	
	private static Function<AbstractFighter, AbstractFighterDecorator> thermitePellets = fighter -> {
		Projectiles projectiles = new Projectiles(fighter);
		projectiles.setProjectileName(AllActions.THERMITE_PELLETS.value());
		projectiles.setProjectileCount(5);
		projectiles.setMoveSupplier(
				() -> FireDamage.create(damage -> damage
								.min(20)
								.max(35)
								.damage(15)
								.duration(5)));
		projectiles.equip();
		return projectiles;
	};
	
	private static Function<AbstractFighter, AbstractFighterDecorator> freezeGrenades = fighter -> {
		Projectiles projectiles = new Projectiles(fighter);
		projectiles.setProjectileName(AllActions.FREEZE_GRENADES.value());
		projectiles.setProjectileCount(5);
		projectiles.setMoveSupplier(
				() -> ColdDamage.create(damage -> damage
								.min(10)
								.deterioration(60)
								.duration(25)));
		projectiles.equip();
		return projectiles;
	};
	
	private static Function<AbstractFighter, AbstractFighterDecorator> stunPellets = fighter -> {
		Projectiles projectiles = new Projectiles(fighter);
		projectiles.setProjectileName(AllActions.STUN_PELLETS.value());
		projectiles.setProjectileCount(5);
		projectiles.setMoveSupplier(
				() -> ShockDamage.create(damage -> damage
								.max(80)
								.duration(35)));
		projectiles.equip();
		return projectiles;
	};
	
	private static Function<AbstractFighter, AbstractFighterDecorator> gasPellets = fighter -> {
		Projectiles projectiles = new Projectiles(fighter);
		projectiles.setProjectileName(AllActions.GAS_PELLETS.value());
		projectiles.setProjectileCount(5);
		projectiles.setMoveSupplier(
				() -> AffectVariableAttribute.create(buff -> buff
							.affectAttribute(AttributeType.LIFE)
							.duration(25)
							.percentagePerBeat(-3))
				);
		projectiles.equip();
		return projectiles;
	};
	
	private static Function<AbstractFighter, AbstractFighterDecorator> manaBurners = fighter -> {
		Projectiles projectiles = new Projectiles(fighter);
		projectiles.setProjectileName(AllActions.MANA_BURNERS.value());
		projectiles.setProjectileCount(5);
		projectiles.setMoveSupplier(
				() -> AffectVariableAttribute.create(buff -> buff
							.affectAttribute(AttributeType.MANA)
							.duration(20)
							.percentagePerBeat(-4))
				);
		projectiles.equip();
		return projectiles;
	};
	
	private static Function<AbstractFighter, AbstractFighterDecorator> kryptonite = fighter -> {
		Projectiles projectiles = new Projectiles(fighter);
		projectiles.setProjectileName(AllActions.KRYPTONITE.value());
		projectiles.setProjectileCount(7);
		projectiles.setMoveSupplier(
				() -> AffectAttribute.create(b -> b
						.affectAttribute(AttributeType.STRENGTH)
						.duration(55)
						.multiplicand(-0.8)));
		projectiles.equip();
		return projectiles;
	};
	
	private static Function<AbstractFighter, AbstractFighterDecorator> utilityBelt = 
			batarang
				.andThen(thermitePellets)
				.andThen(freezeGrenades)
				.andThen(stunPellets)
				.andThen(gasPellets)
				.andThen(manaBurners)
				.andThen(kryptonite);
	
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
