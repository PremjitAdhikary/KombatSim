package practice.premjit.patterns.kombatsim.beats;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BeatObservableImplTest {
    @Mock BeatObserver observer1, observer2;
    BeatObservableImpl beatObservable;
    
    @BeforeEach
    void init() {
        beatObservable = new BeatObservableImpl();
    }

    @Test
    void testRegisterObserver() {
        assertTrue(beatObservable.observers.isEmpty());
        beatObservable.registerObserver(observer1);
        beatObservable.registerObserver(observer2);
        assertEquals(2, beatObservable.observers.size());
    }
    
    @Nested
    @DisplayName("when we have registered observers")
    class WhenObserversAreAlreadyRegistered {
        
        @BeforeEach
        void init() {
            beatObservable.registerObserver(observer1);
            beatObservable.registerObserver(observer2);
        }

        @Test
        void testNotifyObservers() {
            verify(observer1, never()).update();
            verify(observer2, never()).update();
            
            beatObservable.notifyObservers();
            verify(observer1, times(1)).update();
            verify(observer2, times(1)).update();

            beatObservable.notifyObservers();
            verify(observer1, times(2)).update();
            verify(observer2, times(2)).update();
        }

        @Test
        void testUnregisterObserver() {
            assertEquals(2, beatObservable.observers.size());
            beatObservable.unregisterObserver(observer2);
            assertEquals(1, beatObservable.observers.size());
            beatObservable.unregisterObserver(observer1);
            assertTrue(beatObservable.observers.isEmpty());
        }

        @Test
        void testUnregisterObserverThenNotifyAll() {
            verify(observer1, never()).update();
            verify(observer2, never()).update();
            beatObservable.unregisterObserver(observer2);

            beatObservable.notifyObservers();
            verify(observer1, times(1)).update();
            verify(observer2, never()).update();
        }
        
    }

}
