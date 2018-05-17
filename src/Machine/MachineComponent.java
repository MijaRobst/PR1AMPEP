package Machine;

import java.util.Observable;

public abstract class MachineComponent extends Observable {
    protected boolean broken = false;
    
    public void setBroken() {
        if (!isBroken())
            setChanged();
        broken = true;
        notifyObservers();
    }
    
    public void repair() { broken = false; }
    
    public abstract boolean isBroken();
}
