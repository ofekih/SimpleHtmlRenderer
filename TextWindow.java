/**
 * Performs the actual rendering of {@link HtmlComponents}s. Drawing directly
 * to a {@link TextWindow} is not advised; use the {@link HtmlPrinter} that
 * manages this {@link TextWindow} instead.
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
	 * Gets the total height of all the lines.
	 * @return the total height of the lines
	 */
	public int getHeight() {
		int height = 0;
		try {
			for (HtmlComponent component : htmlComponents)
				if (component instanceof HtmlTag)
					height += component.getHtmlComponentHeight();
		} catch (ConcurrentModificationException e) {}
		return height + 2 * Y_MARGIN;
	}

	/**
	 * Gets the width of the widest line.
	 * @return the width of the widest line
	 */
	public int getWidth() {
		int width = 0;
		int tempWidth = 0;
		try {
			for (HtmlComponent component : htmlComponents) {
				if (isBreak(component)) {
					width = Math.max(width, tempWidth);
					tempWidth = 0;
				}
				else tempWidth += component.getHtmlComponentWidth();
			}
		} catch (ConcurrentModificationException e) {}
		return width + 2 * X_MARGIN;
	}

	/**
	 * Tests if a component is a break.
	 * @param  component the {@link HtmlComponent} to test
	 * @return           true if break, false otherwise
	 */
	private boolean isBreak(HtmlComponent component) {
		return component instanceof HtmlTag && ((HtmlTag)component).getTag().equals("br");
	}

	/**
	 * Draws all the htmlComponents onto the window.
	 * @param htmlComponents the {@link HtmlComponent}s to draw
	 */
	public void drawHtmlComponents(List<HtmlComponent> htmlComponents) {
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
	 * Draws all the {@link HtmlComponent}s onto the window.
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

	/**
	 * Draws text onto the screen defined by an htmlFragment at a specific xLoc and yLoc.
	 * @param g            the {@link Graphics} component
	 * @param htmlFragment the {@link HtmlFragment} to draw
	 * @param xLoc         the current x location to draw from
	 * @param yLoc         the current y location to draw from
	 */
	public void drawFragment(Graphics g, HtmlFragment htmlFragment, int xLoc, int yLoc) {
		g.setFont(htmlFragment.getFont());
		g.drawString(htmlFragment.getText(), xLoc, yLoc + htmlFragment.getAscent());
	}

	/**
	 * Draws a special (non-text) {@link HtmlComponent} at a specific xLoc.
	 * @param  g    the {@link Graphics} component
	 * @param  tag  the {@link HtmlTag} to draw
	 * @param  yLoc the current y location to draw from
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
	 * Draws a horizontal rule.
	 * @param  g    the {@link Graphics} component
	 * @param  yLoc the current y location for printing
	 */
	private void drawHorizontalRule(Graphics g, int yLoc) {
		g.fillRect(X_MARGIN / 2, yLoc + 3, getWidth() - X_MARGIN, 2);
	}
}
