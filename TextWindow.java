import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

class TextWindow extends JPanel {

	private final int MARGIN_LEFT = 50;

	private ArrayList<Line> lines;
	private int xSize, ySize;
	private int yScroll, xScroll;

	public TextWindow(int xSize, int ySize) {
		yScroll = xScroll = 0;
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

	public void scrollX(int xScroll) {
		this.xScroll = xScroll;
		repaint();
	}

	public void scrollY(int yScroll) {
		this.yScroll = yScroll;
		repaint();
	}

	private int getX(int x) {
		return x - xScroll;
	}

	private int getY(int y) {
		return y - yScroll;
	}

	private void drawLines(Graphics g) {
		int yLoc = 50;

		try {
			for (Line line : lines) {
				int tempHeight = drawSpecial(g, line.getText(), yLoc);
				if (tempHeight == 0) {
					g.setFont(line.getFont());
					g.drawString(line.getText(), getX(MARGIN_LEFT), getY(yLoc + line.getAscent()));
					yLoc += line.getLineHeight();
				} else yLoc += tempHeight;
			}
		} catch (ConcurrentModificationException e) {}
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
		g.fillRect(getX(MARGIN_LEFT / 2), getY(yLoc), xSize - MARGIN_LEFT, 2);
		return 5;
	}
}