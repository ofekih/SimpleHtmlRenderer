import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

import java.util.ArrayList;

class TextWindow extends JPanel {

	private final int MARGIN_LEFT = 50;

	private ArrayList<Line> lines;
	private int xSize, ySize;

	public TextWindow(int xSize, int ySize) {
		setWindowSize(xSize, ySize);
	}

	private void setWindowSize(int xSize, int ySize) {
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

	private void drawLines(Graphics g) {
		int yLoc = 50;

		for (Line line : lines) {
			int tempHeight = drawSpecial(g, line.getText(), yLoc);
			if (tempHeight == 0) {
				g.setFont(line.getFont());
				g.drawString(line.getText(), MARGIN_LEFT, yLoc + line.getAscent());
				yLoc += line.getLineHeight();
			} else yLoc += tempHeight;
		}
	}

	private int drawSpecial(Graphics g, String tag, int yLoc) {
		switch (tag) {
			case "<hr>":
				return drawHorizontalRule(g, yLoc);
			default: return 0;
		}
	}

	private int drawHorizontalRule(Graphics g, int yLoc) {
		g.setColor(Color.BLACK);
		// System.out.println
		g.fillRect(MARGIN_LEFT / 2, yLoc, xSize - MARGIN_LEFT, 2);
		return 5;
	}
}