package Machine;

import java.util.Observable;

public abstract class MachineComponent extends Observable {
    public abstract void setBroken();
    public abstract void repair();
    public abstract boolean isBroken();
}
