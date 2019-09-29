package practice.premjit.patterns.kombatsim.strategies;

import java.util.Optional;

import practice.premjit.patterns.kombatsim.commands.ActionCommand;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;

/**
 * Strategy Pattern employed to select and execute ActionCommand
 * 
 * @author Premjit Adhikary
 *
 */
public abstract class AbstractFighterActionStrategy implements ActionStrategy {
    protected AbstractFighter fighter;

    public AbstractFighterActionStrategy(AbstractFighter fighter) {
        this.fighter = fighter;
    }
    
    @Override
    public void perform() {
        Optional<ActionCommand> action = selectAction();
        execute(action);
    }
    
    protected abstract Optional<ActionCommand> selectAction();
    
    protected abstract void execute(Optional<ActionCommand> action);

}
