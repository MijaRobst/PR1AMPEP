package Machine;

public class Machine extends MachineComponent {

    private boolean broken = false;
    
    @Override
    public void setBroken() {
        if (!broken)
            setChanged();
        broken = true;
        // Push because the changed component has to travel to the tree's root.
        notifyObservers(this);
    }

    @Override
    public void repair() {
        if (broken)
            setChanged();
        broken = false;
        notifyObservers(this);
    }

    @Override
    public boolean isBroken() {
        return broken;
    }
    
}
