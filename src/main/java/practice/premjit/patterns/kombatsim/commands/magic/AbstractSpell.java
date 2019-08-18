package practice.premjit.patterns.kombatsim.commands.magic;

import practice.premjit.patterns.kombatsim.beats.BeatObserver;
import practice.premjit.patterns.kombatsim.beats.FlipFlopObserver;
import practice.premjit.patterns.kombatsim.common.FlipFlopable;
import practice.premjit.patterns.kombatsim.common.Identifiable;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.Mage;
import practice.premjit.patterns.kombatsim.strategies.magic.SpellBook;

/**
 * AbstractSpell encapsulates the cool down logic for Spells. The idea is that every Spell has a cool down associated 
 * with it. Once casted, it can only be casted after the cool down {@link AbstractSpell#cooldownDuration duration} is 
 * over. <p>
 * 
 * This is achieved by {@link CooldownSwitch}. <p>
 * 
 * AbstractSpell holds other common logic like: <p><ul>
 * <li>Managing {@link AbstractSpell#manaCost manaCost} required for casting the Spell.
 * <li>Api for determining if {@link AbstractSpell#mageHasMana() mageHasMana()}.
 * <li>Api for determining if Spell {@link AbstractSpell#isReady() isReady()}.
 * </ul>
 * 
 * @author Premjit Adhikary
 *
 */
public abstract class AbstractSpell implements Identifiable {
	protected Mage mage;
	protected String name;
	protected int id;
	protected double manaCost;
	protected int cooldownDuration;
	protected BeatObserver cooldownTimer;
	protected Switch cooldown;
	protected SpellBook book;

	protected AbstractSpell(Mage mage, int cooldownDuration, SpellBook book) {
		this.mage = mage;
		this.cooldownDuration = cooldownDuration;
		this.book = book;
		manaCost = 1;
		cooldown = new CooldownSwitch();
		id = Randomizer.generateId();
	}
	
	protected abstract void borrowFromSpellBook();
	
	protected abstract void returnToSpellBook();
	
	public boolean isReady() {
		return !cooldown.state();
	}
	
	public void startCooldown() {
		cooldownTimer = new FlipFlopObserver(cooldownDuration, mage, cooldown);
	}
	
	protected boolean mageHasMana() {
		return mage.currentMana() > manaCost;
	}

	public void setManaCost(double manaCost) {
		this.manaCost = manaCost;
	}
	
	@Override
	public String name() {
		return name;
	}
	
	@Override
	public int id() {
		return id;
	}
	
	/**
	 * CooldownSwitch holds the additional responsibility of borrowing and returning the spell from and to the 
	 * {@link SpellBook}
	 * 
	 * @author Premjit Adhikary
	 *
	 */
	protected class CooldownSwitch extends Switch {
		public CooldownSwitch() {
			super("Cooldown");
		}

		@Override
		public void set() {
			super.set();
			borrowFromSpellBook();
		}

		@Override
		public void reset() {
			super.reset();
			returnToSpellBook();
		}
		
	}
	
	/**
	 * Switch implements {@link FlipFlopable}. It holds a {@link AbstractSpell.Switch#state state} which is set and 
	 * reset based on duration. Also logs state change.
	 * 
	 * @author Premjit Adhikary
	 *
	 */
	protected class Switch implements FlipFlopable {
		private boolean state;
		private String switchName;
		
		public Switch(String switchName) {
			this.switchName = switchName;
		}
		
		public boolean state() {
			return state;
		}

		@Override
		public void set() {
			state = true;
			logSwitch();
		}

		@Override
		public void reset() {
			state = false;
			logSwitch();
		}
		
		private void logSwitch() {
			KombatLogger.getLogger().log(
					KombatLogger.LEVEL.MEDIUM, KombatLogger.EVENT_TYPE.UPDATE, mage.mapify(), 
					KombatLogger.mapBuilder()
						.withName(name)
						.with("Switch", switchName)
						.with("State", String.valueOf(state))
						.build());
		}
		
	}

}
