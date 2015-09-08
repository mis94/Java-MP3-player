import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ButtonModel;
import javax.swing.JButton;
public class RoundButton extends JButton{
	//(192, 192, 192)
	//(82, 82, 82),
	private Color startColor = new Color (24,116,205);
	private Color endColor = new Color(0,0,0);
	private Color rollOverColor = new Color(255, 143, 89);
	private Color pressedColor = new Color(204, 67, 0);;
	private int outerRoundRectSize = 30;
	private int innerRoundRectSize = 28;
	private GradientPaint GP;

	/**
	 * Constructor takes String argument
	 * @param text
	 */
	public RoundButton(String text) {
		super();
		setText(text);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFont(new Font("Thoma", Font.BOLD, 12));
		setForeground(Color.WHITE);
		setFocusable(false);

	}
	
	/**
	 * 
	 * @param startColor
	 * @param endColor
	 * @param rollOverColor
	 * @param pressedColor
	 */
	public RoundButton(Color startColor, Color endColor,
			Color rollOverColor, Color pressedColor) {
		super();
		this.startColor = startColor;
		this.endColor = endColor;
		this.rollOverColor = rollOverColor;
		this.pressedColor = pressedColor;
		setForeground(Color.WHITE);
		setFocusable(false);
		setContentAreaFilled(false);
		setBorderPainted(false);

	}
	
	/**
	 * 
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int h = getHeight();
		int w = getWidth();
		ButtonModel model = getModel();
		
		g2d.setPaint(GP);
		GradientPaint p1;
		GradientPaint p2;
		
		if (model.isPressed()) {
			GP = new GradientPaint(0, 0, pressedColor, 0, h, pressedColor, true);
			g2d.setPaint(GP);
			p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1,
					new Color(100, 100, 100));
			p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 3,
					new Color(255, 255, 255, 100));
		} else {
			p1 = new GradientPaint(0, 0, new Color(100, 100, 100), 0, h - 1,
					new Color(0, 0, 0));
			p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0,
					h - 3, new Color(0, 0, 0, 50));
			GP = new GradientPaint(0, 0, startColor, 0, h, endColor, true);
		}
		
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1,
				h - 1, outerRoundRectSize, outerRoundRectSize);
		Shape clip = g2d.getClip();
		g2d.clip(r2d);
		g2d.fillRect(0, 0, w, h);
		g2d.setClip(clip);
		g2d.setPaint(p1);
		g2d.drawRoundRect(0, 0, w - 1, h - 1, outerRoundRectSize,
				outerRoundRectSize);
		g2d.setPaint(p2);
		g2d.drawRoundRect(1, 1, w - 3, h - 3, innerRoundRectSize,
				innerRoundRectSize);
		g2d.dispose();

		super.paintComponent(g);
		repaint();
	}
    void Swap_colors()
    {
    	Color temp = startColor;
    	startColor=pressedColor;
    	pressedColor=temp;
    	
    	repaint();
    }
	
}
