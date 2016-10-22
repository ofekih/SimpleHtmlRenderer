/**
 * Performs the actual rendering of {@link HTMLLine}s. Drawing directly to a
 * {@link TextWindow} is not advised; use the {@link HTMLPrinter} that manages
 * this {@link TextWindow} instead.
 * 
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 20th, 2016
 */

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.util.List;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

class TextWindow extends JPanel {

	private final int X_MARGIN = 50;
	private final int Y_MARGIN = 50;

	private List<HTMLLine> lines = new ArrayList<HTMLLine>();

	/**
	 * Gets the total height of all the lines
	 * @return the total height of the lines
	 */
	public int getHeight() {
		int height = 0;
		try {
			for (HTMLLine line : lines)
				height += line.getHTMLLineHeight();
		} catch (ConcurrentModificationException e) {}
		return height + 2 * Y_MARGIN;
	}

	/**
	 * Gets the width of the widest line
	 * @return the width of the widest line
	 */
	public int getWidth() {
		int width = 0;
		try {
			for (HTMLLine line : lines)
				width = Math.max(width, line.getHTMLLineWidth());
		} catch (ConcurrentModificationException e) {}
		return width + 2 * X_MARGIN;
	}

	/**
	 * Prints lines from lines list
	 * @param lines an {@link List} of {@link HTMLLine}s
	 */
	public void printHTMLLines(List<HTMLLine> lines) {
		this.lines = lines;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (lines != null)
			drawHTMLLines(g);
	}

	/**
	 * Draws the lines one by one
	 * @param g the {@link Graphics} component
	 */
	private void drawHTMLLines(Graphics g) {
		int yLoc = Y_MARGIN;

		try {
			for (HTMLLine line : lines) {
				g.setColor(line.getColor());
				if (line instanceof SpecialHTMLLine)
					drawSpecial(g, ((SpecialHTMLLine)line).getTag(), yLoc);
				else {
					g.setFont(line.getFont());
					g.drawString(line.getText(), X_MARGIN, yLoc + line.getAscent());
				}
				yLoc += line.getHTMLLineHeight();
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
				break;
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
