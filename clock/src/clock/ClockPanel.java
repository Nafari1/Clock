package clock;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class ClockPanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	private volatile boolean start;
	private LocalDateTime dateTime;

	public ClockPanel(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		setOpaque(false);
		dateTime = LocalDateTime.now();
	}

	public void start() {
		this.start = true;
		new Thread(this).start();
	}

	public void run() {
		int prevSecond = -1;
		while (start) {
			dateTime = LocalDateTime.now();
			int second = dateTime.getSecond();
			if (second != prevSecond) {
				prevSecond = second;
				repaint();
			}
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		PaintClock(g2, dateTime);
	}
	
	public void PaintClock(Graphics2D g2, LocalDateTime dateTime) {
        float lineWidth = 3.0f;
        Stroke line = new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        Font font = new Font("Tahoma", Font.PLAIN, getHeight()/10);
        g2.setColor(new Color(200, 200, 255));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 0.65f));
        g2.setStroke(line);
        g2.setFont(font);

        Point2D origin = new Point2D.Double(getWidth() / 2.0, getHeight() / 10);

        String time = dtFormatter.format(dateTime);
        Rectangle2D timeBounds = font.getStringBounds(time, g2.getFontRenderContext());
        g2.drawString(time, Math.round(origin.getX() - timeBounds.getWidth() / 2.0), Math.round(origin.getY() + getHeight() / 3.0));

        double radius = getWidth() / 2.0  - 2.0 * lineWidth;

        //TO DO START 
        double hour = 2*Math.PI*(dateTime.getHour())/12-0.5*Math.PI;
        double minute = 2*Math.PI*(dateTime.getMinute())/60-0.5*Math.PI;
        double second = 2*Math.PI*(dateTime.getSecond())/60-0.5*Math.PI;
        //godzina
        int godzina1=(int)Math.round(27*Math.cos(2*Math.PI*hour/12-0.5*Math.PI)); //20 to długość 
        int godzina2=(int)Math.round(27*Math.sin(2*Math.PI*hour/12-0.5*Math.PI));
        line = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND); //4 to grubość 
        g2.setStroke(line);
        g2.drawLine((int)origin.getX(),  (int)origin.getY()+12, godzina1+(int)origin.getX(), godzina2+(int)origin.getY()+12);
        
        //minuty
        int minuta1=(int)Math.round(45*Math.cos(2*Math.PI*minute/60-0.5*Math.PI));
        int minuta2=(int)Math.round(45*Math.sin(2*Math.PI*minute/60-0.5*Math.PI));
        line = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(line);
        g2.drawLine((int)origin.getX(),  (int)origin.getY()+12, minuta1+(int)origin.getX(), minuta2+(int)origin.getY()+12);
        
        //sekundy
        int sekunda1=(int)Math.round(47*Math.cos(2*Math.PI*second/60-0.5*Math.PI));
        int sekunda2=(int)Math.round(47*Math.sin(2*Math.PI*second/60-0.5*Math.PI));
        line = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(line);
        g2.drawLine((int)origin.getX(),(int)origin.getY()+12, sekunda1+(int)origin.getX(), sekunda2+(int)origin.getY()+12);
        
        //TODO end
        
        //rysowanie kółka
        double innerRadius = radius / 10;
        g2.fill(new Ellipse2D.Double(origin.getX() - innerRadius/2.0, origin.getY() + innerRadius/2.0, innerRadius, innerRadius));
	}
}