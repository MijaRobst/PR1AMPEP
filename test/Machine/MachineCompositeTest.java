package Machine;

import java.util.Observable;
import java.util.Observer;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class MachineCompositeTest {
    
    MachineComposite mc;
    Machine m1, m2;
    ObserverSpy obs;
    
    public MachineComposite buildMachine(Machine leaf1, Machine leaf2) {
        MachineComposite root = new MachineComposite();
        root.addComponent(leaf1);
        
        MachineComposite child = new MachineComposite();
        child.addComponent(new Machine());
        child.addComponent(leaf2);
        
        root.addComponent(child);
        return root;
    }
    
    @Before
    public void initialitzations() {
        m1 = new Machine();
        m2 = new Machine();
        mc = buildMachine(m1, m2);
        obs = new ObserverSpy();
        mc.addObserver(obs);
    }
    
    @Test
    public void machine_does_not_notify_when_nothing_happens() {
        assertTrue(obs.get() == null);
    }
    
    @Test
    public void machine_does_not_notify_if_repaired_when_not_broken() {
        mc.repair();
        assertTrue(obs.get() == null);
    }
    
    @Test
    public void machine_notifies_when_broken() {
        mc.setBroken();
        assertEquals(obs.get(), mc);
    }
    
    @Test
    public void machine_notifies_once_if_broken_twice() {
        mc.setBroken();
        assertEquals(obs.get(), mc);
        mc.setBroken();
        assertTrue(obs.get() == null);
    }
    
    @Test
    public void machine_notifies_when_repaired() {
        mc.setBroken();
        obs.mc = null;
        mc.repair();
        assertEquals(obs.get(), mc);
    }
    
    @Test
    public void machine_notifies_once_if_repaired_twice() {
        mc.setBroken();
        obs.mc = null;
        mc.repair();
        obs.mc = null;
        mc.repair();
        assertTrue(obs.mc == null);
    }
    
    @Test
    public void machine_notifies_if_one_component_breaks() {
        m1.setBroken();
        assertEquals(obs.mc, mc);
    }
    
    @Test
    public void machine_notifies_if_all_components_repaired() {
        m1.setBroken();
        obs.mc = null;
        m1.repair();
        assertEquals(obs.mc, mc);
    }
    
    @Test
    public void machine_does_not_notify_if_second_component_breaks() {
        m1.setBroken();
        obs.mc = null;
        m2.setBroken();
        assertTrue(obs.mc == null);
    }
    
    @Test
    public void machine_does_not_notify_if_component_repaired_but_another_is_broken() {
        m1.setBroken();
        m2.setBroken();
        obs.mc = null;
        m1.repair();
        assertTrue(obs.mc == null);
    }
    
    @Test
    public void machine_notifies_if_not_broken_and_broken_component_is_added() {
        Machine otherM = new Machine();
        MachineComposite other = buildMachine(new Machine(), otherM);
        otherM.setBroken();
        
        mc.addComponent(other);
        assertEquals(obs.mc, (MachineComponent) mc);
    }
    
    @Test
    public void machine_does_not_notify_if_broken_and_broken_component_is_added() {
        Machine otherM = new Machine();
        MachineComposite other = buildMachine(new Machine(), otherM);
        otherM.setBroken();
        
        mc.setBroken();
        obs.mc = null;
        mc.addComponent(other);
        assertTrue(obs.mc == null);
    }
    
    @Test
    public void machine_does_not_notify_if_not_broken_and_not_broken_component_is_added() {
        MachineComposite other = buildMachine(new Machine(), new Machine());
        
        mc.addComponent(other);
        assertTrue(obs.mc == null);
    }
    
    private static class ObserverSpy implements Observer {
        
        private MachineComponent mc;
        
        @Override
        public void update(Observable o, Object arg) {
            mc = (MachineComponent) o;
        }
        
        public MachineComponent get() {
            MachineComponent old = mc;
            mc = null;
            return old;
        }
    }
}
