package practice.premjit.patterns.kombatsim.commands;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public enum AllActions {
	PUNCH ("Punch"),
	KICK ("Kick"),
	CROSS ("Cross"),
	CHOP ("Chop"),
	FLYING_KICK ("Flying Kick"),
	SHURIKEN ("Shuriken"),
	SWORD_SLASH ("Sword Slash"),
	SWORD_CUT ("Sword Cut"),
	NUNCHUCK_SWING ("Nunchuck Swing"),
	HASTE ("Haste"),
	ARC_LIGHTNING ("Arc Lightning"),
	HEAT_VISION ("Heat Vision"),
	FREEZE_BREATH ("Freeze Breath"),
	FLAME_BREATH ("Flame Breath"),
	BURN ("Burn"),
	ICE_BLAST ("Ice Blast"), 
	CHILL ("Chill"),
	PARALYZE ("Paralyze"),
	CURSE ("Curse"), 
	LIFE_STEAL ("Life Steal"), 
	BLEED ("Bleed"),
	BATARANG ("Batarang"),
	FREEZE_GRENADES ("Freeze Grenades"),
	STUN_PELLETS ("Stun Pellets"), 
	THERMITE_PELLETS ("Thermite Pellets"),
	GAS_PELLETS ("Gas Pellets"),
	MANA_BURNERS ("Mana Burners"),
	KRYPTONITE ("Kryptonite");
	
	private static final EnumSet<AllActions> PHYSICALS = EnumSet.of(PUNCH, KICK, CROSS, CHOP, FLYING_KICK);
	private static final EnumSet<AllActions> PROJECTILES = EnumSet.of(SHURIKEN, BATARANG, FREEZE_GRENADES, 
			STUN_PELLETS, THERMITE_PELLETS, GAS_PELLETS, MANA_BURNERS, KRYPTONITE);
	private static final EnumSet<AllActions> WEAPONS = EnumSet.of(SWORD_SLASH, SWORD_CUT, NUNCHUCK_SWING);
	private static final EnumSet<AllActions> NON_PHYSICALS = EnumSet.of(ARC_LIGHTNING, HEAT_VISION, FREEZE_BREATH, 
			FLAME_BREATH, ICE_BLAST, ARC_LIGHTNING, BURN, CHILL, PARALYZE, CURSE);
	private static final EnumSet<AllActions> FIRES = EnumSet.of(HEAT_VISION, FLAME_BREATH, BURN);
	private static final EnumSet<AllActions> COLDS = EnumSet.of(FREEZE_BREATH, ICE_BLAST, CHILL);
	private static final EnumSet<AllActions> SHOCKS = EnumSet.of(ARC_LIGHTNING, PARALYZE);
	private static final EnumSet<AllActions> BUFFS = EnumSet.of(HASTE, CURSE, LIFE_STEAL, BLEED);
	
	private String value;
	
	private AllActions(String v) {
		value = v;
	}
	
	public String value() {
		return this.value;
	}
	
	public static AllActions getByValue(String v) {
		return Arrays.stream(AllActions.values())
				.filter(a -> a.value().equals(v))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No Such Action: "+v));
	}
	
	public static Set<AllActions> allPhysicalAttacks() {
		return PHYSICALS;
	}
	
	public static Set<AllActions> allProjectiles() {
		return PROJECTILES;
	}
	
	public static Set<AllActions> allWeaponAttacks() {
		return WEAPONS;
	}
	
	public static Set<AllActions> allNonPhysicalAttacks() {
		return NON_PHYSICALS;
	}
	
	public static Set<AllActions> allFireAttacks() {
		return FIRES;
	}
	
	public static Set<AllActions> allColdAttacks() {
		return COLDS;
	}
	
	public static Set<AllActions> allShockAttacks() {
		return SHOCKS;
	}
	
	public static Set<AllActions> allBuffs() {
		return BUFFS;
	}

}
