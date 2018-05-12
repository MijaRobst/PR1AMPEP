package Machine;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MachineComposite extends MachineComponent implements Observer {

    private List<MachineComponent> components = new ArrayList<>();
    private boolean broken = false; // Independent from components
    private int brokenComponents = 0; // Use list to keep track of changes?
    
    @Override
    public void setBroken() {
        if (!isBroken())
            setChanged();
        broken = true;
        notifyObservers(this);
    }

    @Override
    public void repair() {
        if (broken) {
            broken = false;
            if (!isBroken())
                setChanged();
        }
        notifyObservers(this);
    }
    
    public void addComponent(MachineComponent mc) {
        if (mc.isBroken()) {
            brokenComponents += 1;
            if (!isBroken()) {
                setChanged();
            }
        }
        mc.addObserver(this);
        components.add(mc);
        notifyObservers(this);
    }

    @Override
    public boolean isBroken() {
        return broken || brokenComponents > 0;
    }

    @Override
    public void update(Observable o, Object arg) {
        MachineComponent mc = (MachineComponent) arg;
        if (mc.isBroken()) {
            if (!isBroken())
                setChanged();
            brokenComponents += 1;
        } else {
            brokenComponents -= 1;
            if (!isBroken())
                setChanged();
        }
        notifyObservers(mc); // Push because the UI has to know the broken component.
    }
    
}
