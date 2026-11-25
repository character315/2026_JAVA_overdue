package pet;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

class PetMovementHandler extends MouseAdapter {
    private final PetView view;
    private final Runnable onActivity;
    private int startX;
    private int startY;

    PetMovementHandler(PetView view, Runnable onActivity) {
        this.view = view;
        this.onActivity = onActivity;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        onActivity.run();
        if (SwingUtilities.isLeftMouseButton(e)) {
            startX = e.getXOnScreen();
            startY = e.getYOnScreen();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        int x = view.getX() + e.getXOnScreen() - startX;
        int y = view.getY() + e.getYOnScreen() - startY;
        Rectangle screen = screenBounds(e.getLocationOnScreen());

        x = Math.max(screen.x, Math.min(x, screen.x + screen.width - view.getWidth()));
        y = Math.max(screen.y, Math.min(y, screen.y + screen.height - view.getHeight()));
        view.setLocation(x, y);

        startX = e.getXOnScreen();
        startY = e.getYOnScreen();
        onActivity.run();
    }

    private Rectangle screenBounds(Point point) {
        GraphicsConfiguration config = view.getGraphicsConfiguration();
        for (GraphicsDevice device : GraphicsEnvironment
                .getLocalGraphicsEnvironment().getScreenDevices()) {
            GraphicsConfiguration candidate = device.getDefaultConfiguration();
            if (candidate.getBounds().contains(point)) {
                config = candidate;
                break;
            }
        }
        Rectangle bounds = new Rectangle(config.getBounds());
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);
        bounds.x += insets.left;
        bounds.y += insets.top;
        bounds.width -= insets.left + insets.right;
        bounds.height -= insets.top + insets.bottom;
        return bounds;
    }
}
