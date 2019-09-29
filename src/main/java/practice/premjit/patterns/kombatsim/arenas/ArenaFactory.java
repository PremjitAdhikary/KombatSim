package practice.premjit.patterns.kombatsim.arenas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Factory Method to create Arenas.
 * 
 * @author Premjit Adhikary
 *
 */
public class ArenaFactory {
    public static final String BACKYARD = "Backyard";
    public static final String RING = "Ring";
    public static final String MYSTICAL_FOREST = "Mystical Forest";
    static final Map<String, Supplier<ArenaMediator>> arenaMap = new HashMap<>();
    
    static {
        arenaMap.put(BACKYARD, Backyard::new);
        arenaMap.put(RING, Ring::new);
        arenaMap.put(MYSTICAL_FOREST, MysticalForest::new);
    }
    
    private ArenaFactory() {
        // singleton
    }
    
    private static class SingletonHolder {
        private static final ArenaFactory INSTANCE = new ArenaFactory();
    }
    
    public static ArenaFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    public ArenaMediator getArena(String arenaName) {
        if (arenaMap.containsKey(arenaName))
            return arenaMap.get(arenaName).get();
        throw new IllegalArgumentException("Invalid Arena: "+arenaName);
    }

}
