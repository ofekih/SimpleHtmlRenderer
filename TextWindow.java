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

import java.util.List;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

class TextWindow extends JPanel {

	private final int X_MARGIN = 50;
	private final int Y_MARGIN = 50;

	private List<Line> lines = new ArrayList<Line>();

	public int getHeight() {
		int height = 0;
		try {
			for (Line line : lines)
				height += line.getLineHeight();
		} catch (ConcurrentModificationException e) {}
		return height + 2 * Y_MARGIN;
	}

	public int getWidth() {
		int width = 0;
		try {
			for (Line line : lines)
				width = Math.max(width, line.getLineWidth());
		} catch (ConcurrentModificationException e) {}
		return width + 2 * X_MARGIN;
	}

	/**
	 * Prints lines from lines list
	 * @param lines an {@link List} of {@link Line}s
	 */
	public void printLines(List<Line> lines) {
		this.lines = lines;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (lines != null)
			drawLines(g);
	}

	/**
	 * Draws the lines one by one
	 * @param g the {@link Graphics} component
	 */
	private void drawLines(Graphics g) {
		int yLoc = Y_MARGIN;

		try {
			for (Line line : lines) {
				g.setColor(line.getColor());
				if (line instanceof SpecialLine)
					drawSpecial(g, ((SpecialLine)line).getTag(), yLoc);
				else {
					g.setFont(line.getFont());
					g.drawString(line.getText(), X_MARGIN, yLoc + line.getAscent());
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
		g.fillRect(X_MARGIN / 2, yLoc + 3, getWidth() - X_MARGIN, 2);
	}
}