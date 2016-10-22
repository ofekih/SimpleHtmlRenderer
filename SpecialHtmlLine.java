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

public class SpecialHtmlLine extends HtmlLine {

	private String tag;
	private int lineHeight;

	/**
	 * SpecialHtmlLine constructor with all the necessary values
	 * @param  tag       the tag corresponding to the symbol to display
	 * @param  font      the {@link Font} to use
	 * @param  color     the {@link Color} to use
	 * @param  component any old {@link JComponent} to get font metrics from
	 */
	public SpecialHtmlLine(String tag, Font font, Color color, JComponent component, int lineHeight) {
		super("", font, color, component);
		this.tag = tag;
		this.lineHeight = lineHeight;
	}

	/**
	 * Gets the tag for this special line
	 * @return [description]
	 */
	public String getTag() {
		return tag;
	}

	@Override
	public int getHtmlLineHeight() {
		return lineHeight;
	}

	@Override
	public int getHtmlLineWidth() {
		return -1;
	}
}
