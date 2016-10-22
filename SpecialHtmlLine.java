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

	private static final int BR_HEIGHT = 25;
	private static final int HR_HEIGHT = 8;

	private String tag;

	/**
	 * SpecialHtmlLine constructor with all the necessary values
	 * @param  tag       the tag corresponding to the symbol to display
	 * @param  font      the {@link Font} to use
	 * @param  color     the {@link Color} to use
	 * @param  component any old {@link JComponent} to get font metrics from
	 */
	public SpecialHtmlLine(String tag, Font font, Color color, JComponent component) {
		super("", font, color, component);
		this.tag = tag;
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
		switch (tag) {
			case "br":
				return BR_HEIGHT;
			case "hr":
				return HR_HEIGHT;
			default:
				return 0;
		}
	}

	@Override
	public int getHtmlLineWidth() {
		switch (tag) {
			case "hr":
				return -1;
			default:
				return super.getHtmlLineWidth();
		}
	}
}
