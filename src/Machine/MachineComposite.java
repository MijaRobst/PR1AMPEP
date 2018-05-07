package Machine;


import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class MachineComposite extends MachineComponent {

    private List<MachineComponent> components = new ArrayList<>();
    private boolean broken = false;
    
    @Override
    public void setBroken() {
        broken = true;
    }

    @Override
    public void repair() {
        broken = false;
    }
    
    public void addComponent(MachineComponent mc) {
        components.add(mc);
    }

    @Override
    public boolean isBroken() {
        if (broken)
            return true;
        
        for (MachineComponent mc : components) {
            if (mc.isBroken())
                return true;
        }
        return false;
    }
    
}
