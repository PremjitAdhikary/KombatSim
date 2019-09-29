package practice.premjit.patterns.kombatsim.arenas;

import practice.premjit.patterns.kombatsim.beats.TikTok;

/**
 * Most basic arena, has no restrictions.
 * 
 * @author Premjit Adhikary
 *
 */
public class Backyard extends AbstractArena {
    
    public Backyard() {
        super(ArenaFactory.BACKYARD);
        tiktok = new TikTok();
    }

}
