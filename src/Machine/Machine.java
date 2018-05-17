package Machine;

public class Machine extends MachineComponent {
    
    @Override
    public void repair() {
        if (broken) {
            broken = false;
            setChanged();
        }
        notifyObservers();
    }

    @Override
    public boolean isBroken() {
        return broken;
    }
    
}
