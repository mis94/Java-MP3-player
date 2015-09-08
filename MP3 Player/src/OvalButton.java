import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ButtonModel;
import javax.swing.JButton;

public class OvalButton extends JButton {
//(244, 244, 244)
	private Color startColor = new Color(24,116,205);
	private Color endColor = new Color(82, 82, 82);
	private Color pressedColor = new Color(204, 67, 0);
	private GradientPaint GP;

	/**
	 * Constructor takes String argument
	 * 
	 * @param text
	 */
	public OvalButton(String text) {
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
	 * @wbp.parser.constructor
	 */
	public OvalButton() {
		setForeground(Color.WHITE);
		setFocusable(false);
		setContentAreaFilled(false);
		setBorderPainted(false);

	}

	/**
	 * 
	 */
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int h = getHeight();
		ButtonModel model = getModel();

		g2d.setPaint(GP);
		GradientPaint p1;
		GradientPaint p2;

		if (model.isPressed()) {
			GP = new GradientPaint(0, 0, pressedColor, 0, h, pressedColor, true);
			g2d.setPaint(GP);
			p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1, new Color(100, 100, 100));
			p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 3, new Color(255, 255, 255, 100));
		} else {
			p1 = new GradientPaint(0, 0, new Color(100, 100, 100), 0, h - 1, new Color(0, 0, 0));
			p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0, h - 3, new Color(0, 0, 0, 50));
			GP = new GradientPaint(0, 0, startColor, 0, h, endColor, true);
		}
		RenderingHints hints = new RenderingHints(null);
		hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(hints);
		g2d.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
		
		super.paintComponent(g2d);
		repaint();
	}

}