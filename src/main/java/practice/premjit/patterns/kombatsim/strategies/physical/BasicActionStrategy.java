package practice.premjit.patterns.kombatsim.strategies.physical;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import practice.premjit.patterns.kombatsim.commands.ActionCommand;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.strategies.AbstractFighterActionStrategy;

/**
 * Most Basic ActionStrategy... Select an ActionCommand at random
 * 
 * @author Premjit Adhikary
 *
 */
public class BasicActionStrategy extends AbstractFighterActionStrategy {

    public BasicActionStrategy(AbstractFighter fighter) {
        super(fighter);
    }

    @Override
    protected Optional<ActionCommand> selectAction() {
        List<ActionCommand> allEnabledActions = fighter.allActions()
                .stream()
                .filter( ActionCommand::canBeExecuted )
                .collect(Collectors.toList());
        
        if (allEnabledActions.isEmpty())
            return Optional.empty();
        
        int selectedAction = Randomizer.randomInteger(allEnabledActions.size()-1);
        return Optional.of(allEnabledActions.get(selectedAction));
    }

    @Override
    protected void execute(Optional<ActionCommand> action) {
        action.ifPresent( ActionCommand::execute );
    }

}
