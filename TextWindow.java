import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

class TextWindow extends JPanel {

	private int xSize, ySize;

	public TextWindow(int xSize, int ySize) {
		setWindowSize(xSize, ySize);
	}

	public void setWindowSize(int xSize, int ySize) {
		setSize(xSize, ySize);
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void drawBorder(Graphics g) {
		g.setColor(Color.BLACK);
		// g.drawRect(0, 0, xSize - 1, ySize - 1);
	}
}