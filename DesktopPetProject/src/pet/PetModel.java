package pet;

public class PetModel {
    private PetState state = PetState.IDLE;

    public PetState getState() {
        return state;
    }

    public void setState(PetState state) {
        this.state = state;
    }
}
