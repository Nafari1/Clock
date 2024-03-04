package clock;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class Main {
    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    GraphicsDevice gd = ge.getDefaultScreenDevice();
                    boolean isPerPixelTranslucencySupported = gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSLUCENT);
                    if(isPerPixelTranslucencySupported){
                        Main window = new Main();
                        window.frame.pack();
                        window.frame.setVisible(true);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public Main(){
        initialize();
    }

    public void initialize(){
        frame = new JFrame();
        frame.setType(Window.Type.UTILITY);
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(0, 350); //położenie na ekranie
        frame.setAlwaysOnTop(true);
        ClockPanel clockPanel = new ClockPanel(300, 300);  //wielkość 
        clockPanel.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseReleased(MouseEvent me) {
                if(me.isPopupTrigger())System.exit(0);
                frame.setLocation(frame.getLocation().x + me.getX()-125, frame.getLocation().y + me.getY()-35);
                
            }
        });
        frame.setContentPane(clockPanel);
        clockPanel.start();
    }
}



