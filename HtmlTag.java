/**
 * A {@link HtmlLine} for special HTML components that are not expressed well
 * in text, such as horizontal breaks.
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 20th, 2016
 */

import java.awt.Font;
import java.awt.Color;
import javax.swing.JComponent;

public class HtmlTag implements HtmlComponent {

	private Color color;
	private String tag;
	private int lineHeight;

	/**
	 * SpecialHtmlLine constructor with all the necessary values
	 * @param  tag       the tag corresponding to the symbol to display
	 * @param  color     the {@link Color} to use
	 */
	public HtmlTag(String tag, Color color, int lineHeight) {
		this.color = color;
		this.tag = tag;
		this.lineHeight = lineHeight;
	}

	/**
	 * Gets the color of this line
	 * @return the {@link Color}
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Gets the tag for this special line
	 * @return [description]
	 */
	public String getTag() {
		return tag;
	}

	public int getHtmlComponentHeight() {
		return lineHeight;
	}

	public int getHtmlComponentWidth() {
		return -1;
	}
}
