package Machine;

public class Machine extends MachineComponent {

    private boolean broken = false;
    
    @Override
    public void setBroken() {
        if (!broken)
            setChanged();
        broken = true;
        notifyObservers();
    }

    @Override
    public void repair() {
        if (broken)
            setChanged();
        broken = false;
        notifyObservers();
    }

    @Override
    public boolean isBroken() {
        return broken;
    }
    
}
