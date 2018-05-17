package Machine;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MachineComposite extends MachineComponent implements Observer {

    private List<MachineComponent> components = new ArrayList<>();
    private int brokenComponents = 0; // This MachineComponent not counted.

    @Override
    public void repair() {
        if (broken) {
            broken = false;
            if (!isBroken())
                setChanged();
        }
        notifyObservers();
    }
    
    public void addComponent(MachineComponent mc) {
        if (mc.isBroken()) {
            if (!isBroken()) {
                setChanged();
            }
            brokenComponents += 1;
        }
        mc.addObserver(this);
        components.add(mc);
        notifyObservers();
    }

    @Override
    public boolean isBroken() {
        return broken || brokenComponents > 0;
    }

    @Override
    public void update(Observable o, Object arg) {
        MachineComponent mc = (MachineComponent) o;
        if (mc.isBroken()) {
            if (!isBroken())
                setChanged();
            brokenComponents += 1;
        } else {
            brokenComponents -= 1;
            if (!isBroken())
                setChanged();
        }
        notifyObservers();
    }
    
}
