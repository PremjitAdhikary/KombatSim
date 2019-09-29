package practice.premjit.patterns.kombatsim.fighters.factory;

import java.util.HashMap;
import java.util.Map;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.fighters.Fighter;

/**
 * AbstractFactory pattern.
 * 
 * @author Premjit Adhikary
 *
 */
public abstract class FighterFactory {
    private static final Map<String, FighterFactory> FACTORIES;
    
    static {
        FACTORIES = new HashMap<>();
    }
    
    public static final FighterFactory getFactory(String fighterType) {
        if (FACTORIES.containsKey(fighterType)) {
            return FACTORIES.get(fighterType);
        }
        throw new IllegalArgumentException("Invalid input factory: "+fighterType);
    }
    
    public static final void registerFactory(String fighterType, FighterFactory factory) {
        FACTORIES.put(fighterType, factory);
    }
    
    public abstract Fighter getFighter(String fighterSubtype, ArenaMediator arena, String name);

}
