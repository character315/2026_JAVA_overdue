
package pet;

import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class PetWindow extends JFrame {
	
	
	private int startX;
	private int startY;
	private int moveX = 2;

    public PetWindow() {
 

        URL imageURL = getClass().getResource("/img/jump.gif");
        ImageIcon dogIcon = new ImageIcon(imageURL);
        JLabel dogLabel = new JLabel(dogIcon);
        setTitle("Desktop Pet");
        setSize(300, 300);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(dogLabel);

        
        
        
        dogLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
            	
            	startX=e.getXOnScreen();
            	startY=e.getYOnScreen();            	

            	System.out.println("x좌표: "+startX);
            	System.out.println("y좌표: "+startY);


            }
        });
        
        
        dogLabel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getXOnScreen() - startX;
                int dy = e.getYOnScreen() - startY;  
                
                setLocation(getX() + dx, getY() + dy);
                startX = e.getXOnScreen();
                startY = e.getYOnScreen();
                
            }
        });
        
        
        
        
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PetWindow();
        });
    }
}
