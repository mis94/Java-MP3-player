import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static BufferedImage image;
	private static BufferedImage default_image;
	public ImagePanel() {
		try {
			URL url = ImagePanel.class.getResource("Logo.png");
			default_image = ImageIO.read(url);
			image=default_image;

		} catch (IOException ex) {
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		Image tmp = image.getScaledInstance(536, 225, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(536, 225, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		g.drawImage(dimg, 0, 0, null);
	}

	public void setImage(BufferedImage Image) {
		if (Image != null)
			image = Image;
		else
			image = default_image;
		this.paintComponent(getGraphics());
		this.paintAll(getGraphics());
	}
	

}