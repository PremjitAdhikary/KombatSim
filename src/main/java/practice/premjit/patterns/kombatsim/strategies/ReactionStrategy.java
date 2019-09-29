package practice.premjit.patterns.kombatsim.strategies;

import java.util.Optional;

import practice.premjit.patterns.kombatsim.moves.Move;

public interface ReactionStrategy {
    
    boolean perform(Optional<Move> move);

}
