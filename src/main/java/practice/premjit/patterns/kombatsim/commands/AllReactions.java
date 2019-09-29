package practice.premjit.patterns.kombatsim.commands;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public enum AllReactions {
    BLOCK ("Block"),
    EVADE ("Evade"),
    FIRE_SHIELD ("Fire Shield"),
    FROZEN_WALL ("Frozen Wall"),
    THORNS_CURSE ("Thorns Curse"),
    BLOCK_AND_ENDURE ("Block and Endure"),
    EVADE_OR_ENDURE ("Evade or Endure");
    
    private String value;
    
    private AllReactions(String v) {
        value = v;
    }
    
    public String value() {
        return this.value;
    }
    
    public static AllReactions getByValue(String v) {
        return Arrays.stream(AllReactions.values())
                .filter(a -> a.value().equals(v))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Such Reaction: "+v));
    }
    
    public Set<AllReactions> allPhysicalBlocks() {
        return EnumSet.of(BLOCK, EVADE);
    }
    
    public Set<AllReactions> allFireBlocks() {
        return EnumSet.of(FIRE_SHIELD);
    }
    
    public Set<AllReactions> allColdBlocks() {
        return EnumSet.of(FROZEN_WALL);
    }
    
    public Set<AllReactions> allDarkBlocks() {
        return EnumSet.of(THORNS_CURSE);
    }

}
