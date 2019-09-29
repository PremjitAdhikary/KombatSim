package practice.premjit.patterns.kombatsim.commands;

public interface ActionCommand {
    
    boolean canBeExecuted();
    
    void execute();

}
