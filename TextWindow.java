import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

import java.util.ArrayList;

class TextWindow extends JPanel {

	private ArrayList<Line> lines;
	private int xSize, ySize;

	public TextWindow(int xSize, int ySize) {
		setWindowSize(xSize, ySize);
	}

	public void setWindowSize(int xSize, int ySize) {
		setSize(xSize, ySize);
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public void printLines(ArrayList<Line> lines) {
		this.lines = lines;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (lines != null)
			drawLines(g);
	}

	public void drawLines(Graphics g) {
		int xLoc = 50;
		int yLoc = 50;
		int fontHeight;

		for (Line line : lines) {
			g.setFont(line.getFont());
			fontHeight = line.getLineHeight();
			g.drawString(line.getText(), xLoc, yLoc + fontHeight / 2);
			yLoc += fontHeight;
		}
	}
}