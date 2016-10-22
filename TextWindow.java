/**
 * Performs the actual rendering of {@link HtmlLine}s. Drawing directly to a
 * {@link TextWindow} is not advised; use the {@link HtmlPrinter} that manages
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

	private List<HtmlComponent> htmlComponents = new ArrayList<HtmlComponent>();

	/**
	 * Gets the total height of all the lines
	 * @return the total height of the lines
	 */
	public int getHeight() {
		int height = 0;
		try {
			for (HtmlComponent component : htmlComponents)
				height += component.getHtmlComponentHeight();
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
			for (HtmlComponent component : htmlComponents)
				width = Math.max(width, component.getHtmlComponentWidth());
		} catch (ConcurrentModificationException e) {}
		return width + 2 * X_MARGIN;
	}

	/**
	 * Prints lines from lines list
	 * @param htmlComponents an {@link List} of {@link HtmlComponent}s
	 */
	public void printHtmlComponents(List<HtmlComponent> htmlComponents) {
		this.htmlComponents = htmlComponents;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (htmlComponents != null)
			drawHtmlComponents(g);
	}

	/**
	 * Draws the lines one by one
	 * @param g the {@link Graphics} component
	 */
	private void drawHtmlComponents(Graphics g) {
		int xLoc = X_MARGIN;
		int yLoc = Y_MARGIN;

		try {
			for (HtmlComponent component : htmlComponents) {
				g.setColor(component.getColor());
				if (component instanceof HtmlTag) {
					xLoc = Y_MARGIN;
					drawTag(g, (HtmlTag)component, yLoc);
					yLoc += component.getHtmlComponentHeight();
				} else if (component instanceof HtmlFragment) {
					drawFragment(g, (HtmlFragment)component, xLoc, yLoc);
					xLoc += component.getHtmlComponentWidth();
				}
			}
		} catch (ConcurrentModificationException e) {}
	}

	public void drawFragment(Graphics g, HtmlFragment htmlFragment, int xLoc, int yLoc) {
		g.setFont(htmlFragment.getFont());
		g.drawString(htmlFragment.getText(), xLoc, yLoc + htmlFragment.getAscent());
	}

	/**
	 * Draws a special (non-text) line
	 * @param  g    the {@link Graphics} component
	 * @param  tag  the special character tag
	 * @param  yLoc the current y location for printing
	 */
	private void drawTag(Graphics g, HtmlTag htmlTag, int yLoc) {
		switch (htmlTag.getTag()) {
			case "hr":
				drawHorizontalRule(g, yLoc);
				break;
			case "br":
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
