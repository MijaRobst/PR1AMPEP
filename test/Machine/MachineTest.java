package Machine;

import java.util.Observable;
import java.util.Observer;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class MachineTest {

    ObserverSpy obs;
    private MachineComponent m;

    @Before
    public void initializations() {
        obs = new ObserverSpy();
        m = new Machine();
        m.addObserver(obs);
    }

    @Test
    public void machine_does_not_notify_when_nothing_happens() {
        assertFalse(obs.hasChanged());
    }
    
    @Test
    public void machine_does_not_notify_if_repaired_when_not_broken() {
        m.repair();
        assertFalse(obs.hasChanged());
    }
    
    @Test
    public void machine_notifies_when_broken() {
        m.setBroken();
        assertTrue(obs.hasChanged());
    }
    
    @Test
    public void machine_notifies_once_if_broken_twice() {
        m.setBroken();
        assertTrue(obs.hasChanged());
        m.setBroken();
        assertFalse(obs.hasChanged());
    }
    
    @Test
    public void machine_notifies_when_repaired() {
        m.setBroken();
        obs.changed = false;
        m.repair();
        assertTrue(obs.hasChanged());
    }
    
    @Test
    public void machine_notifies_once_if_repaired_twice() {
        m.setBroken();
        obs.changed = false;
        m.repair();
        obs.changed = false;
        m.repair();
        assertFalse(obs.hasChanged());
    }

    private static class ObserverSpy implements Observer {

        private boolean changed = false;

        @Override
        public void update(Observable o, Object arg) {
            changed = true;
        }

        public boolean hasChanged() {
            boolean old = changed;
            changed = false;
            return old;
        }
    }
}
