package practice.premjit.patterns.kombatsim.strategies.hero;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import practice.premjit.patterns.kombatsim.commands.ActionCommand;
import practice.premjit.patterns.kombatsim.commands.AllActions;
import practice.premjit.patterns.kombatsim.common.Identifiable;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.fighters.Amateur;
import practice.premjit.patterns.kombatsim.fighters.Hero;
import practice.premjit.patterns.kombatsim.fighters.Mage;
import practice.premjit.patterns.kombatsim.fighters.Professional;
import practice.premjit.patterns.kombatsim.fighters.decorators.Projectiles;
import practice.premjit.patterns.kombatsim.fighters.factory.Heroes;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.strategies.AbstractFighterActionStrategy;
import practice.premjit.patterns.kombatsim.visitors.FighterVisitor;
import practice.premjit.patterns.kombatsim.visitors.MoveVisitor;

/**
 * Batman needs his custom strategy. He is the underdog against the giants like Superman, Flash and other Mages.<br><br>
 * 
 * The vanilla Batman is most vulnerable, but agile. So while he can punch and kick, the only attack he performs are 
 * kicks so as to impart the maximum damage. <br><br>
 * 
 * The equipped Batman has augmented strength. So instead of kicks, he punches around. <br>
 * The equipped Batman also has at his disposal a lot of {@link Projectiles projectiles} that he can take advantage of. 
 * This strategy is to enable Batman to use them effectively. <br><br>
 * 
 * The crux of the strategy for equipped Batman is that at regular interval to weaken the opponent. And then inflict 
 * maximum damage by punching. <br>
 * Now every opponent has his own weakness. So choose the right projectile, Batman first identifies his opponent. This 
 * is done by executing an initial Move {@link Tracer}, which uses visitor pattern ({@link TracerVisitor}) to determine 
 * the most effective projectiles against the opponent. Once we have short listed the projectiles, one of them is 
 * selected at random and its {@link ActionCommand command} executed.
 * 
 * @author Premjit Adhikary
 *
 */
public class BatmanActionStrategy extends AbstractFighterActionStrategy {
	private static final EnumSet<AllActions> PHYSICAL_N_FIRE = 
			EnumSet.of(AllActions.BATARANG, AllActions.THERMITE_PELLETS);
	private static final EnumSet<AllActions> SHOCK_N_COLD = 
			EnumSet.of(AllActions.STUN_PELLETS, AllActions.FREEZE_GRENADES);
	private static final EnumSet<AllActions> MAGE_NEUTRALIZER = 
			EnumSet.of(AllActions.GAS_PELLETS, AllActions.MANA_BURNERS);
	private static final EnumSet<AllActions> KRYPTONITE_N_SHOCK = 
			EnumSet.of(AllActions.KRYPTONITE, AllActions.STUN_PELLETS);
	
	int actionCount;
	private boolean equipped;

	public BatmanActionStrategy(AbstractFighter fighter, boolean equipped) {
		super(fighter);
		this.equipped = equipped;
	}

	@Override
	protected Optional<ActionCommand> selectAction() {
		if (!equipped)
			return getAction(AllActions.KICK);
		
		if (timeToWeakenOpponent()) {
			EnumSet<AllActions> weakeners = getWeakener();
			List<ActionCommand> allEnabledWeakeners = getActions(weakeners);
			int selectedAction = Randomizer.randomInteger(allEnabledWeakeners.size()-1);
			return Optional.of(allEnabledWeakeners.get(selectedAction));
		}
		
		return getAction(AllActions.PUNCH);
	}

	@Override
	protected void execute(Optional<ActionCommand> action) {
		actionCount++;
		action.ifPresent( ActionCommand::execute );
	}
	
	private boolean timeToWeakenOpponent() {
		return (actionCount % 3) == 0;
	}
	
	private EnumSet<AllActions> getWeakener() {
		Tracer tracer = new Tracer();
		this.fighter.arena().sendMove(tracer, Recipient.OPPONENT, this.fighter);
		return tracer.selectedProjectiles;
	}
	
	private Optional<ActionCommand> getAction(AllActions action) {
		return fighter.allActions()
				.stream()
				.filter( a -> action.value().equals(((Identifiable) a).name()) )
				.findFirst();
	}
	
	private List<ActionCommand> getActions(EnumSet<AllActions> actions) {
		return fighter.allActions()
				.stream()
				.filter( a -> actions.contains(AllActions.getByValue(((Identifiable) a).name())) )
				.filter( ActionCommand::canBeExecuted )
				.collect(Collectors.toList());
	}
	
	class Tracer implements Move {
		EnumSet<AllActions> selectedProjectiles;

		@Override
		public void affect(AbstractFighter fighter) {
			fighter.accept(new TracerVisitor(this));
		}

		@Override
		public void accept(MoveVisitor visitor) {
			visitor.visit(this);
		}
		
	}
	
	class TracerVisitor implements FighterVisitor {
		Tracer tracer;
		
		public TracerVisitor(Tracer tracer) {
			this.tracer = tracer;
		}

		@Override
		public void visit(AbstractFighter a) {
			tracer.selectedProjectiles = PHYSICAL_N_FIRE;
		}

		@Override
		public void visit(Amateur a) {
			tracer.selectedProjectiles = PHYSICAL_N_FIRE;
		}

		@Override
		public void visit(Professional p) {
			tracer.selectedProjectiles = PHYSICAL_N_FIRE;
		}

		@Override
		public void visit(Hero h) {
			switch (h.fighterSubType()) {
			case Heroes.FLASH:
				tracer.selectedProjectiles = SHOCK_N_COLD;
				break;
			case Heroes.SUPERMAN:
				tracer.selectedProjectiles = KRYPTONITE_N_SHOCK;
				break;
			case Heroes.BATMAN:
			case Heroes.EQUIPPED_BATMAN:
				tracer.selectedProjectiles = PHYSICAL_N_FIRE;
				break;
			}
		}

		@Override
		public void visit(Mage m) {
			tracer.selectedProjectiles = MAGE_NEUTRALIZER;
			
		}
		
	}

}
