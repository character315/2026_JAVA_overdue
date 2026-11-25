package pet;

import javax.swing.SwingUtilities;

public class PetApplication {
    public static PetController createPet() {
        if (!SwingUtilities.isEventDispatchThread()) {
            throw new IllegalStateException("Create the pet on the Swing EDT.");
        }
        PetModel model = new PetModel();
        PetView view = new PetView();
        PetController controller = new PetController(model, view);
        view.setVisible(true);
        return controller;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createPet());
    }
}
