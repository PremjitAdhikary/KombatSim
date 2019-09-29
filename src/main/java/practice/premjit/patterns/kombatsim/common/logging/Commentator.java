package practice.premjit.patterns.kombatsim.common.logging;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.Map;

import practice.premjit.patterns.kombatsim.commands.AllActions;
import practice.premjit.patterns.kombatsim.commands.AllReactions;
import practice.premjit.patterns.kombatsim.common.Randomizer;

public class Commentator extends KombatLogger {
    private String champion, challenger;
    private String championSubtype, challengerSubtype;

    @Override
    protected void logit(LEVEL level, EVENT_TYPE type, Map<String, String> source, Map<String, String> event) {
        if (level != LEVEL.HIGH)
            return; // only commentate on high level

        switch (type) {
        case INFO:
            logInfo(source, event);
            break;
        case ACTION:
            logAction(source, event);
            break;
        case REACTION:
            logReaction(source, event);
            break;
        }
    }
    
    private void logInfo(Map<String, String> source, Map<String, String> event) {
        String mate;
        switch(event.get(NAME)) {
        case FIGHTER_ADDED:
            if (event.get(AS).equals(CHAMPION)) {
                champion = source.get(FIGHTER_NAME);
                championSubtype = source.get(FIGHTER_SUBTYPE);
                System.out.println("Our first fighter " + champion + "(" + championSubtype + ") enters the arena!");
            }
            if (event.get(AS).equals(CHALLENGER)) {
                challenger = source.get(FIGHTER_NAME);
                challengerSubtype = source.get(FIGHTER_SUBTYPE);
                System.out.println("And to fight him we have " + challenger + "(" + challengerSubtype + ")!!");
            }
            break;
        case FIGHT_START:
            System.out.println("FIGHT !!!!\n");
            break;
        case FIGHT_END:
            System.out.println("It's Over! A " + event.get(RESULT_BY) +"!!!\n");
            break;
        case TEAMMATE_ADDED:
            mate = source.get(FIGHTER_NAME) + "(" + source.get(FIGHTER_SUBTYPE) + ")";
            System.out.println(mate + " added to "+ event.get(TEAM) +"\n");
            break;
        case "Tag And Switch":
            mate = source.get(FIGHTER_NAME) + "(" + source.get(FIGHTER_SUBTYPE) + ")";
            System.out.println(mate + " switched into the ring!\n");
            break;
        case KNOCK_OUT:
            mate = source.get(FIGHTER_NAME) + "(" + source.get(FIGHTER_SUBTYPE) + ")";
            System.out.println(mate + " is Knocked Out!\n");
            break;
        case WON:
        case LOST:
        case DRAW:
            mate = source.get(FIGHTER_NAME) + "(" + source.get(FIGHTER_SUBTYPE) + ")";
            System.out.println(mate + " "+ event.get(NAME) +"!!!\n");
            break;
        default:
            System.out.println("?????");
            System.out.println(event);
            break;
        }
    }
    
    private static String[] actionAdjectives = {"Excellent ", "A powerful ", "Magnificient ", "What a "};
    private static String[] weaponAdjectives = {"A menacing ", "Dangerous ", "A deadly "};
    private void logAction(Map<String, String> source, Map<String, String> event) {
        String attackName = event.get(NAME);
        switch(attackType(AllActions.getByValue(attackName))) {
        case "Physical":
            System.out.println(actionAdjectives[Randomizer.randomInteger(actionAdjectives.length-1)] 
                    + attackName + " by " + source.get(FIGHTER_NAME) + "...");
            break;
        case "Projectile":
            System.out.println(attackName + " thrown by " + source.get(FIGHTER_NAME) + "...");
            break;
        case "Weapon":
            System.out.println(weaponAdjectives[Randomizer.randomInteger(weaponAdjectives.length-1)] 
                    + attackName +" by " + source.get(FIGHTER_NAME) + "...");
            break;
        case "Fire":
            System.out.println("Hot! Hot!! Hot!!! " + attackName + " by " + source.get(FIGHTER_NAME) + "...");
            break;
        case "Cold":
            System.out.println("Brrrrr!!! That's Cold. " + attackName + " by " + source.get(FIGHTER_NAME) + "...");
            break;
        case "Shock":
            System.out.println("Look at that!!! " + attackName + " by " + source.get(FIGHTER_NAME) + ". "
                    + opponent(source.get(FIGHTER_NAME)) + " is shocked!!");
            break;
        case "Buff":
            switch(attackName) {
            case "Haste":
                System.out.println(source.get(FIGHTER_NAME) + " Hastes himself!! Look at the speed...");
                break;
            case "Life Steal":
                System.out.println(source.get(FIGHTER_NAME) + " casts a Life Steal on " 
                        + opponent(source.get(FIGHTER_NAME)) + ". Both Offensive and Defensive Spell!");
                break;
            case "Bleed":
                System.out.println(source.get(FIGHTER_NAME) + " casts a Bleed spell. That's gotta hurt!");
                break;
            }
            break;
        default:
            System.out.println(attackName + " by " + source.get(FIGHTER_NAME) + "???");
        }
    }
    
    private static String attackType(AllActions attack) {
        if (AllActions.allPhysicalAttacks().contains(attack))
            return "Physical";
        if (AllActions.allProjectiles().contains(attack))
            return "Projectile";
        if (AllActions.allWeaponAttacks().contains(attack))
            return "Weapon";
        if (AllActions.allFireAttacks().contains(attack))
            return "Fire";
        if (AllActions.allColdAttacks().contains(attack))
            return "Cold";
        if (AllActions.allShockAttacks().contains(attack))
            return "Shock";
        if (AllActions.allBuffs().contains(attack))
            return "Buff";
        return "";
    }
    
    private void logReaction(Map<String, String> source, Map<String, String> event) {
        if (event == null) {
            System.out.println(source.get(FIGHTER_NAME) + " is hit!! \n");
            return;
        }
        
        switch (AllReactions.getByValue(event.get(NAME))) {
        case BLOCK:
            System.out.println("Blocked by " + source.get(FIGHTER_NAME) + "!");
            break;
        case EVADE:
            if (Double.parseDouble(event.get("Damage Amount")) > 0.0) 
                System.out.println("Impossible for " + source.get(FIGHTER_NAME) + " to evade it!");
            else
                System.out.println(source.get(FIGHTER_NAME) + " easily evades the hit!");
            break;
        case BLOCK_AND_ENDURE:
        case EVADE_OR_ENDURE:
            System.out.println("Batman endures the hit!!");
            break;
        case FIRE_SHIELD:
            System.out.println("A Fire Sheild to burn the enemy");
            break;
        case FROZEN_WALL:
            System.out.println("A Frozen Wall to chill the enemy");
            break;
        case THORNS_CURSE:
            System.out.println("A Thorns Curse to weaken the enemy");
            break;
        default:
            System.out.println("A " + event.get(NAME) + " attempted by " + source.get(FIGHTER_NAME) + "!");
            break;
        }
        System.out.println();
    }
    
    private String opponent(String fighter) {
        return (fighter.equals(champion) ? challenger : champion);
    }

}
