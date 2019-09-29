package practice.premjit.patterns.kombatsim.common;

public final class Initializer {
    
    static {
        System.out.println("Starting up ...");
        try {
            Class.forName("practice.premjit.patterns.kombatsim.fighters.factory.AmateurFighters");
            Class.forName("practice.premjit.patterns.kombatsim.fighters.factory.ProfessionalFighters");
            Class.forName("practice.premjit.patterns.kombatsim.fighters.factory.EnhancedFighters");
            Class.forName("practice.premjit.patterns.kombatsim.fighters.factory.Heroes");
            Class.forName("practice.premjit.patterns.kombatsim.fighters.factory.Mages");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Up and Running ...");
    }
    
    private Initializer() { }
    
    public static void init() {
        // start it up
    }

}
