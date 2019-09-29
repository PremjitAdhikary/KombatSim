package practice.premjit.patterns.kombatsim.beats;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;

@ExtendWith(MockitoExtension.class)
class TikTokTest {
    TikTok tt;
    @Mock BeatObserver tikObserver, tokObserver;
    
    @BeforeEach
    void init() {
        KombatLogger.getLogger().disableLogging();
    }
    
    @Nested
    @DisplayName("when bounded")
    class WhenBounded {
        
        @BeforeEach
        void init() {
            tt = new TikTok(10);
            tt.addTikObserver(tikObserver);
            tt.addTokObserver(tokObserver);
        }
        
        @Test
        void testBounded() {
            tt.start();
            verify(tikObserver, times(10)).update();
            verify(tokObserver, times(10)).update();
            assertFalse(tt.isRunning());
        }
        
    }
    
    @Nested
    @DisplayName("when unbounded")
    class WhenUnbounded {
        
        @BeforeEach
        void init() {
            tt = new TikTok();
            tt.addTikObserver(tikObserver);
            tt.addTokObserver(tokObserver);
        }
        
        @Test
        void testUnbounded() {
            doAnswer((Answer) invocation -> {
                if (tt.currentCycle == 5)
                    tt.stop();
                return null;
            }).when(tikObserver).update();
            
            tt.start();
            verify(tikObserver, times(6)).update();
            verify(tokObserver, times(5)).update();
            assertFalse(tt.isRunning());
        }
        
    }
    
    @Nested
    @DisplayName("when unregistered")
    class WhenUnRegistering {
        
        @BeforeEach
        void init() {
            tt = new TikTok(20);
            tt.addTikObserver(tikObserver);
            tt.addTokObserver(tokObserver);
        }
        
        @Test
        void testUnregister() {
            doAnswer((Answer) invocation -> {
                if (tt.currentCycle == 5)
                    tt.removeTikObserver(tikObserver);
                return null;
            }).when(tikObserver).update();
            
            doAnswer((Answer) invocation -> {
                if (tt.currentCycle == 7)
                    tt.removeTokObserver(tokObserver);
                return null;
            }).when(tokObserver).update();
            
            tt.start();
            verify(tikObserver, times(6)).update();
            verify(tokObserver, times(8)).update();
            assertFalse(tt.isRunning());
        }
        
    }

}
