package practice.premjit.patterns.kombatsim.commands.magic;

import java.util.Optional;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.beats.BeatObserver;
import practice.premjit.patterns.kombatsim.beats.FlipFlopObserver;
import practice.premjit.patterns.kombatsim.commands.ReactionCommand;
import practice.premjit.patterns.kombatsim.fighters.Mage;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.strategies.magic.SpellBook;

/**
 * Mages do not directly react to attacks on them. They do setup a defensive aura or shield around them which deals 
 * with those attacks. To setup these defenses, they cast a reaction spell. Once setup, the spell stays active for a 
 * set duration during which it reacts to all incoming attacks. <p>
 * 
 * So along with cool down timer, these spells have an additional switch timer to track whether the spell is active or 
 * not. This is achieved by the {@link AbstractReactionSpell#active active} {@link AbstractSpell.Switch Switch}. <p>
 * 
 * Other operations: <p><ul>
 * <li>Whether Spell {@link AbstractReactionSpell#isActive() isActive()}
 * <li>Whether Spell {@link AbstractReactionSpell#canBeActivated() canBeActivated()}
 * <li>{@link AbstractReactionSpell#activate() activate()} the Spell.
 * <li>Implement {@link AbstractReactionSpell#borrowFromSpellBook() borrowFromSpellBook()} to invoke {@link 
 * SpellBook#borrowSpell(AbstractReactionSpell) book.borrowSpell(ReactionSpell)}
 * <li>Implement {@link AbstractReactionSpell#returnToSpellBook() returnToSpellBook()} to invoke {@link 
 * SpellBook#returnSpell(AbstractReactionSpell) book.returnSpell(ReactionSpell)}
 * </ul>
 * 
 * @author Premjit Adhikary
 *
 */
public abstract class AbstractReactionSpell extends AbstractSpell implements ReactionCommand {
	protected Switch active;
	protected int activeDuration;
	protected BeatObserver activateTimer;

	protected AbstractReactionSpell(Mage mage, String name, SpellBook book, 
			int cooldownDuration, int activeDuration) {
		super(mage, cooldownDuration, book);
		this.name = name;
		cooldown.reset(); // by default spell is ready
		active = this.new Switch("Active");
		active.reset(); // by default spell not active
		this.activeDuration = activeDuration;
	}

	@Override
	public boolean canBeExecuted(Optional<Move> move) {
		return isActive();
	}
	
	public boolean isActive() {
		return active.state();
	}
	
	public boolean canBeActivated() {
		return isReady() && !isActive() && mageHasMana();
	}
	
	public boolean activate() {
		if (!canBeActivated())
			return false;
		
		mage.getAttribute(AttributeType.MANA).ifPresent(
				mana -> ((VariableAttribute) mana).incrementCurrent(-manaCost));
		activateTimer = new FlipFlopObserver(activeDuration, mage, active);
		startCooldown();
		onActivate();
		return true;
	}

	@Override
	protected void borrowFromSpellBook() {
		book.borrowSpell(this);
	}

	@Override
	protected void returnToSpellBook() {
		book.returnSpell(this);
	}
	
	protected abstract void onActivate();

}
