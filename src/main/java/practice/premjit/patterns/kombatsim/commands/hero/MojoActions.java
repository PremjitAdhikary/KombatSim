package practice.premjit.patterns.kombatsim.commands.hero;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.commands.AllActions;
import practice.premjit.patterns.kombatsim.fighters.Hero;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.moves.buffs.AffectAttribute;
import practice.premjit.patterns.kombatsim.moves.damages.ColdDamage;
import practice.premjit.patterns.kombatsim.moves.damages.FireDamage;
import practice.premjit.patterns.kombatsim.moves.damages.ShockDamage;

public final class MojoActions {
	private static final Map<String, Function<Hero, MojoBasedAction>> actionMap = new HashMap<>();
	
	// flash powers
	
	private static Function<Hero, MojoBasedAction> haste = hero -> {
		MojoBasedAction action = new MojoBasedAction(hero, AllActions.HASTE.value(), 
				() -> AffectAttribute.create(buff -> buff
						.affectAttribute(AttributeType.DEXTERITY)
						.duration(15)
						.multiplicand(3)));
		action.setRecipient(Recipient.SELF);
		action.setMojoCost(95);
		return action;
	};
	
	private static Function<Hero, MojoBasedAction> arcLightning = hero -> {
		MojoBasedAction action = new MojoBasedAction(hero, AllActions.ARC_LIGHTNING.value(), 
				() -> ShockDamage.create(shock -> shock
									.max(50)
									.duration(25)));
		action.setMojoCost(90);
		return action;
	};
	
	// superman powers
	
	private static Function<Hero, MojoBasedAction> heatVision = hero -> {
		MojoBasedAction action = new MojoBasedAction(hero, AllActions.HEAT_VISION.value(),
				() -> FireDamage.create(fire -> fire
								.min(100)
								.max(100)
								.damage(20)
								.duration(4)));
		action.setMojoCost(160);
		return action;
	};
	
	private static Function<Hero, MojoBasedAction> freezeBreath = hero -> {
		MojoBasedAction action = new MojoBasedAction(hero, AllActions.FREEZE_BREATH.value(),
				() -> ColdDamage.create(cold -> cold
								.min(15)
								.deterioration(20)
								.duration(45)));
		action.setMojoCost(150);
		return action;
	};
	
	static {
		actionMap.put(AllActions.HASTE.value(), haste);
		actionMap.put(AllActions.ARC_LIGHTNING.value(), arcLightning);
		actionMap.put(AllActions.HEAT_VISION.value(), heatVision);
		actionMap.put(AllActions.FREEZE_BREATH.value(), freezeBreath);
	}
	
	private MojoActions() { }
	
	public static MojoBasedAction getAction(AllActions action, Hero hero) {
		if (actionMap.containsKey(action.value())) {
			return actionMap.get(action.value()).apply(hero);
		}
		throw new IllegalArgumentException("Invalid Mojo Action: "+action.value());
	}

}
