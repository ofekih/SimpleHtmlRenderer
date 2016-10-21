/**
 * TextWindow
 * Creates the JPanel that actually displays the text and characters
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 20th, 2016
 */

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

	/**
	 * Initializes text window with given width and height
	 * @param  xSize width of the window
	 * @param  ySize height of the window
	 */
	public TextWindow(int xSize, int ySize) {
		yScroll = xScroll = 0;
		setWindowSize(xSize, ySize);
	}

	/**
	 * Sets the window's size
	 * @param xSize width of the window
	 * @param ySize height of the window
	 */
	public void setWindowSize(int xSize, int ySize) {
		setSize(xSize, ySize);
		this.xSize = xSize;
		this.ySize = ySize;
		repaint();
	}

	/**
	 * Prints lines from lines arraylist
	 * @param lines an {@link ArrayList} of {@link Line}s
	 */
	public void printLines(ArrayList<Line> lines) {
		this.lines = lines;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (lines != null)
			drawLines(g);
	}

	/**
	 * Scrolls along x-axis
	 * @param xScroll x scroll value
	 */
	public void scrollX(int xScroll) {
		this.xScroll = xScroll;
		repaint();
	}

	/**
	 * Scrolls along y-axis
	 * @param yScroll y scroll value
	 */
	public void scrollY(int yScroll) {
		this.yScroll = yScroll;
		repaint();
	}

	public int getScrollY() {
		return yScroll;
	}

	/**
	 * Gets real x coordinate adjusted for scrolling
	 * @param  x old x coordinate
	 * @return   real x coordinate
	 */
	private int getX(int x) {
		return x - xScroll;
	}

	/**
	 * Gets real y coordinate adjusted for scrolling
	 * @param  y old y coordinate
	 * @return   read y coordinate
	 */
	private int getY(int y) {
		return y - yScroll;
	}

	/**
	 * Draws the lines one by one
	 * @param g the {@link Graphics} component
	 */
	private void drawLines(Graphics g) {
		int yLoc = 50;

		try {
			for (Line line : lines) {
				g.setColor(line.getColor());
				if (line instanceof SpecialLine)
					drawSpecial(g, ((SpecialLine)line).getTag(), yLoc);
				else {
					g.setFont(line.getFont());
					g.drawString(line.getText(), getX(MARGIN_LEFT), getY(yLoc + line.getAscent()));
				}
				yLoc += line.getLineHeight();
			}
		} catch (ConcurrentModificationException e) {}
	}

	/**
	 * Draws a special (non-text) line
	 * @param  g    the {@link Graphics} component
	 * @param  tag  the special character tag
	 * @param  yLoc the current y location for printing
	 */
	private void drawSpecial(Graphics g, String tag, int yLoc) {
		switch (tag) {
			case "hr":
				drawHorizontalRule(g, yLoc);
		}
	}

	/**
	 * Draws a horizontal rule
	 * @param  g    the {@link Graphics} component
	 * @param  yLoc the current y location for printing
	 */
	private void drawHorizontalRule(Graphics g, int yLoc) {
		g.fillRect(getX(MARGIN_LEFT / 2), getY(yLoc + 3), xSize - MARGIN_LEFT, 2);
	}
}