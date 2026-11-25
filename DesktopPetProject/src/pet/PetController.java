package pet;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class PetController {
    private final PetModel model;
    private final PetView view;
    private final Timer danceTimer;
    private final Timer idleTimer;
    private boolean closed;

    PetController(PetModel model, PetView view) {
        this.model = model;
        this.view = view;

        danceTimer = new Timer(3000, e -> showIdle());
        danceTimer.setRepeats(false);
        idleTimer = new Timer(60000, e -> {
            if (model.getState() == PetState.IDLE) {
                showSleep();
            }
        });
        idleTimer.setRepeats(false);

        view.bindMenuActions(this::showIdle, this::showDance,
                             this::showSleep, this::close);

        view.getDogLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!SwingUtilities.isLeftMouseButton(e)) return;
                if (model.getState() == PetState.SLEEP) {
                    showIdle();
                } else {
                    playDance();
                }
            }
        });

        PetMovementHandler movement = new PetMovementHandler(view, this::resetIdleTimer);
        view.getDogLabel().addMouseListener(movement);
        view.getDogLabel().addMouseMotionListener(movement);
        showIdle();
    }

    private void changeState(PetState state) {
        model.setState(state);
        view.updateAnimation(state);
    }

    private void resetIdleTimer() {
        if (!closed && model.getState() == PetState.IDLE) {
            idleTimer.restart();
        }
    }

    // 모든 명령을 Swing 이벤트 스레드에서 처리한다.
    private void runOnEdt(Runnable task) {
        Runnable action = () -> {
            if (!closed) task.run();
        };
        if (SwingUtilities.isEventDispatchThread()) {
            action.run();
        } else {
            SwingUtilities.invokeLater(action);
        }
    }

    public void showIdle() {
        runOnEdt(() -> {
            danceTimer.stop();
            changeState(PetState.IDLE);
            idleTimer.restart();
        });
    }

    public void showDance() {
        runOnEdt(() -> {
            danceTimer.stop();
            idleTimer.stop();
            changeState(PetState.DANCE);
        });
    }

    public void showSleep() {
        runOnEdt(() -> {
            danceTimer.stop();
            idleTimer.stop();
            changeState(PetState.SLEEP);
        });
    }

    public void playDance() {
        runOnEdt(() -> {
            idleTimer.stop();
            changeState(PetState.DANCE);
            danceTimer.restart();
        });
    }

    public void close() {
        runOnEdt(() -> {
            closed = true;
            danceTimer.stop();
            idleTimer.stop();
            view.dispose();
        });
    }
}
