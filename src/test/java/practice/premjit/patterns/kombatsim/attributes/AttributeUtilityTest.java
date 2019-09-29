package practice.premjit.patterns.kombatsim.attributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttributeUtilityTest {
    EnumMap<AttributeType, Attribute> attributeMap;
    
    @Test
    void testIsVariableAttribute() {
        assertFalse(AttributeUtility.isVariableAttribute(AttributeType.STRENGTH));
        assertFalse(AttributeUtility.isVariableAttribute(AttributeType.DEXTERITY));
        assertTrue(AttributeUtility.isVariableAttribute(AttributeType.LIFE));
        assertTrue(AttributeUtility.isVariableAttribute(AttributeType.STAMINA));
        assertTrue(AttributeUtility.isVariableAttribute(AttributeType.MOJO));
        assertTrue(AttributeUtility.isVariableAttribute(AttributeType.MANA));
    }
    
    @Test
    void testBuildStrength() {
        double s = 20;
        Attribute strength = AttributeUtility.buildStrength(s);
        assertEquals(s, strength.base());
        assertEquals(s, strength.net());
        assertEquals(AttributeType.STRENGTH, strength.type());
        assertFalse(strength.isPrimary());
    }
    
    @Test
    void testBuildStrengthAsPrimary() {
        double s = 40;
        Attribute strength = AttributeUtility.buildStrengthAsPrimary(s);
        assertEquals(s, strength.base());
        assertEquals(s, strength.net());
        assertEquals(AttributeType.STRENGTH, strength.type());
        assertTrue(strength.isPrimary());
    }
    
    @Test
    void testBuildDexterity() {
        double d = 20;
        Attribute dexterity = AttributeUtility.buildDexterity(d);
        assertEquals(d, dexterity.base());
        assertEquals(d, dexterity.net());
        assertEquals(AttributeType.DEXTERITY, dexterity.type());
        assertFalse(dexterity.isPrimary());
    }
    
    @Test
    void testBuildDexterityAsPrimary() {
        double d = 40;
        Attribute dexterity = AttributeUtility.buildDexterityAsPrimary(d);
        assertEquals(d, dexterity.base());
        assertEquals(d, dexterity.net());
        assertEquals(AttributeType.DEXTERITY, dexterity.type());
        assertTrue(dexterity.isPrimary());
    }
    
    @Test
    void testBuildLife() {
        double l = 100;
        VariableAttribute life = AttributeUtility.buildLife(l);
        assertEquals(l, life.base());
        assertEquals(l, life.net());
        assertEquals(l, life.current());
        assertEquals(AttributeType.LIFE, life.type());
        assertFalse(life.isPrimary());
    }
    
    @Test
    void testBuildStamina() {
        double s = 100;
        double sc = 80;
        VariableAttribute stamina = AttributeUtility.buildStamina(s, sc);
        assertEquals(s, stamina.base());
        assertEquals(s, stamina.net());
        assertEquals(sc, stamina.current());
        assertEquals(AttributeType.STAMINA, stamina.type());
        assertFalse(stamina.isPrimary());
    }
    
    @Test
    void testBuildStaminaAsPrimary() {
        double s = 100;
        double sc = 80;
        VariableAttribute stamina = AttributeUtility.buildStaminaAsPrimary(s, sc);
        assertEquals(s, stamina.base());
        assertEquals(s, stamina.net());
        assertEquals(sc, stamina.current());
        assertEquals(AttributeType.STAMINA, stamina.type());
        assertTrue(stamina.isPrimary());
    }
    
    @Test
    void testBuildSMojo() {
        double m = 90;
        double mc = 70;
        VariableAttribute mojo = AttributeUtility.buildMojo(m, mc);
        assertEquals(m, mojo.base());
        assertEquals(m, mojo.net());
        assertEquals(mc, mojo.current());
        assertEquals(AttributeType.MOJO, mojo.type());
        assertFalse(mojo.isPrimary());
    }
    
    @Test
    void testBuildMojoAsPrimary() {
        double m = 90;
        double mc = 70;
        VariableAttribute mojo = AttributeUtility.buildMojoAsPrimary(m, mc);
        assertEquals(m, mojo.base());
        assertEquals(m, mojo.net());
        assertEquals(mc, mojo.current());
        assertEquals(AttributeType.MOJO, mojo.type());
        assertTrue(mojo.isPrimary());
    }
    
    @Test
    void testBuildSMana() {
        double m = 90;
        double mc = 70;
        VariableAttribute mana = AttributeUtility.buildMana(m, mc);
        assertEquals(m, mana.base());
        assertEquals(m, mana.net());
        assertEquals(mc, mana.current());
        assertEquals(AttributeType.MANA, mana.type());
        assertFalse(mana.isPrimary());
    }
    
    @Test
    void testBuildManaAsPrimary() {
        double m = 90;
        double mc = 70;
        VariableAttribute mana = AttributeUtility.buildManaAsPrimary(m, mc);
        assertEquals(m, mana.base());
        assertEquals(m, mana.net());
        assertEquals(mc, mana.current());
        assertEquals(AttributeType.MANA, mana.type());
        assertTrue(mana.isPrimary());
    }
    
    @Nested
    @DisplayName("when attributes map not initialized")
    class WhenAttributesMapNotInitialized {
        
        @Test
        void testGetAttribute() {
            Exception exception = assertThrows(IllegalArgumentException.class, 
                    () -> AttributeUtility.getAttribute(attributeMap, AttributeType.DEXTERITY) );
            assertEquals("Map not initialized.", exception.getMessage());
        }
        
        @Test
        void testGetVariableAttribute() {
            Exception exception = assertThrows(IllegalArgumentException.class, 
                    () -> AttributeUtility.getVariableAttribute(attributeMap, AttributeType.LIFE) );
            assertEquals("Map not initialized.", exception.getMessage());
        }
        
        @Test
        void testGetPrimaryAttribute() {
            Exception exception = assertThrows(IllegalArgumentException.class, 
                    () -> AttributeUtility.getPrimaryAttribute(attributeMap) );
            assertEquals("Map not initialized.", exception.getMessage());
        }
        
    }
    
    @Nested
    @DisplayName("when attributes present")
    class WhenAttributesPresent {
        
        @BeforeEach
        public void init() {
            attributeMap = new EnumMap<>(AttributeType.class);
            attributeMap.put(AttributeType.STRENGTH, new Attribute(AttributeType.STRENGTH, 100, true));
            attributeMap.put(AttributeType.DEXTERITY, new Attribute(AttributeType.DEXTERITY, 80));
            attributeMap.put(AttributeType.LIFE, new VariableAttribute(AttributeType.LIFE, 150));
        }
        
        @Test
        void testGetAttribute() {
            assertEquals(AttributeType.DEXTERITY, 
                    AttributeUtility.getAttribute(attributeMap, AttributeType.DEXTERITY).get().type());
            assertEquals(AttributeType.STRENGTH, 
                    AttributeUtility.getAttribute(attributeMap, AttributeType.STRENGTH).get().type());
            assertEquals(AttributeType.LIFE, 
                    AttributeUtility.getAttribute(attributeMap, AttributeType.LIFE).get().type());
        }
        
        @Test
        void testGetPrimaryAttribute() {
            Attribute attr = AttributeUtility.getPrimaryAttribute(attributeMap).get();
            assertTrue(attr.isPrimary());
            assertEquals(AttributeType.STRENGTH, attr.type());
        }
        
        @Test
        void testGetVariableAttribute() {
            assertFalse(AttributeUtility.getVariableAttribute(attributeMap, AttributeType.DEXTERITY).isPresent());
            assertFalse(AttributeUtility.getVariableAttribute(attributeMap, AttributeType.STRENGTH).isPresent());
            assertEquals(AttributeType.LIFE, 
                    AttributeUtility.getVariableAttribute(attributeMap, AttributeType.LIFE).get().type());
        }
        
    }
    
    @Nested
    @DisplayName("when attributes not present")
    class WhenAttributesNotPresent {
        
        @BeforeEach
        public void init() {
            attributeMap = new EnumMap<>(AttributeType.class);
        }
        
        @Test
        void testGetAttribute() {
            assertFalse(AttributeUtility.getAttribute(attributeMap, AttributeType.DEXTERITY).isPresent());
            assertFalse(AttributeUtility.getAttribute(attributeMap, AttributeType.STRENGTH).isPresent());
            assertFalse(AttributeUtility.getAttribute(attributeMap, AttributeType.LIFE).isPresent());
        }
        
        @Test
        void testGetPrimaryAttribute() {
            assertFalse(AttributeUtility.getPrimaryAttribute(attributeMap).isPresent());
        }
        
        @Test
        void testGetVariableAttribute() {
            assertFalse(AttributeUtility.getVariableAttribute(attributeMap, AttributeType.DEXTERITY).isPresent());
            assertFalse(AttributeUtility.getVariableAttribute(attributeMap, AttributeType.STRENGTH).isPresent());
            assertFalse(AttributeUtility.getVariableAttribute(attributeMap, AttributeType.LIFE).isPresent());
        }
        
    }

}
