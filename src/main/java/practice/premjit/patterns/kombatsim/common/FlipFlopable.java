package practice.premjit.patterns.kombatsim.common;

/**
 * Implement this to set and reset on the cue of
 * {@link practice.premjit.patterns.kombatsim.beats.FlipFlopObserver
 * FlipFlopObserver}
 * 
 * @author Premjit Adhikary
 *
 */
public interface FlipFlopable {

    void set();
    
    void reset();
    
}
