package practice.premjit.patterns.kombatsim.fighters.decorators;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import practice.premjit.patterns.kombatsim.commands.ActionCommand;
import practice.premjit.patterns.kombatsim.common.Identifiable;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.common.logging.Loggable;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;

/**
 * Projectiles add additional ActionCommands of type
 * {@link Projectiles.ProjectileCommand ProjectileCommand} that the Fighter can
 * call upon. The catch is that there is a limit to the number of projectiles
 * available {@link #projectileCount}. This count is accounted for in
 * {@link Projectiles.ProjectileCommand#canBeExecuted()}
 * <p>
 * 
 * {@link #moveSupplier} supplies the move to inflict the opponent.
 * <p>
 * 
 * @author Premjit Adhikary
 *
 */
public class Projectiles extends AbstractFighterDecorator {
    private String projectileName;
    private int projectileCount;
    private Supplier<Move> moveSupplier;
    private Recipient recipient;

    private Projectiles(AbstractFighter fighter) {
        super(fighter);
        recipient = Recipient.OPPONENT;
    }

    @Override
    public void equip() {
        if (isEquipped())
            return;
        theFighter.addAction(new ProjectileCommand());
        equipped();
    }
    
    class ProjectileCommand implements ActionCommand, Identifiable {
        private int id;

        ProjectileCommand() {
            this.id = Randomizer.generateId();
        }

        @Override
        public boolean canBeExecuted() {
            return projectileCount > 0;
        }

        @Override
        public void execute() {
            Move move = calculateMove();
            projectileCount--;
            sendMove(move);
        }

        @Override
        public String name() {
            return projectileName;
        }

        @Override
        public int id() {
            return id;
        }
        
        private Move calculateMove() {
            return moveSupplier.get();
        }
        
        private void sendMove(Move move) {
            KombatLogger.getLogger().log(
                    KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.ACTION, theFighter.mapify(), mapify(move));
            theFighter.arena().sendMove(move, recipient, theFighter);
        }
        
        public Map<String, String> mapify(Move move) {
            try {
                return KombatLogger.mapBuilder()
                        .withName(projectileName)
                        .with("Count", String.valueOf(projectileCount))
                        .withPartial(((Loggable) move).mapify())
                        .build();
            } catch (Exception e) {
                return null;
            }
        }
        
    }
    
    // For Type safety Builder
    
    public static Projectiles create(Consumer<ProjectilesFighterBuilder> block) {
        ProjectilesBuilder builder = new ProjectilesBuilder();
        block.accept(builder);
        return builder.build();
    }
    
    public interface ProjectilesFighterBuilder {
        ProjectilesNameBuilder forFighter(AbstractFighter fighter);
    }
    
    public interface ProjectilesNameBuilder {
        ProjectilesCountBuilder name(String name);
    }
    
    public interface ProjectilesCountBuilder {
        ProjectilesMoveBuilder count(int count);
    }
    
    public interface ProjectilesMoveBuilder {
        ProjectilesOptionalsBuilder move(Supplier<Move> supplier);
    }
    
    public interface ProjectilesOptionalsBuilder {
        ProjectilesOptionalsBuilder recipient(Recipient r);
    }
    
    public static class ProjectilesBuilder implements ProjectilesFighterBuilder, ProjectilesNameBuilder, 
            ProjectilesCountBuilder, ProjectilesMoveBuilder, ProjectilesOptionalsBuilder {
        private Projectiles projectiles;

        @Override
        public ProjectilesNameBuilder forFighter(AbstractFighter fighter) {
            projectiles = new Projectiles(fighter);
            return this;
        }

        @Override
        public ProjectilesCountBuilder name(String name) {
            projectiles.projectileName = name;
            return this;
        }

        @Override
        public ProjectilesMoveBuilder count(int count) {
            projectiles.projectileCount = count;
            return this;
        }

        @Override
        public ProjectilesOptionalsBuilder move(Supplier<Move> supplier) {
            projectiles.moveSupplier = supplier;
            return this;
        }

        @Override
        public ProjectilesOptionalsBuilder recipient(Recipient r) {
            projectiles.recipient = r;
            return this;
        }
        
        private Projectiles build() {
            if (projectiles == null)
                throw new IllegalArgumentException("Required properties are not set");
            projectiles.equip();
            return projectiles;
        }
    }

}
