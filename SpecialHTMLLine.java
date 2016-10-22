/**
 * A special line object with it's special tag symbol. Can be used for horizontal bars.
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 20th, 2016
 */

import java.awt.Font;
import java.awt.Color;
import javax.swing.JComponent;

public class SpecialHTMLLine extends HTMLLine {

	private String tag;

	/**
	 * SpecialHTMLLine constructor with all the necessary values
	 * @param  tag       the tag corresponding to the symbol to display
	 * @param  font      the {@link Font} to use
	 * @param  color     the {@link Color} to use
	 * @param  component any old {@link JComponent} to get font metrics from
	 */
	public SpecialHTMLLine(String tag, Font font, Color color, JComponent component) {
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
	public int getHTMLLineHeight() {
		switch (tag) {
			case "hr":
				return 8;
			default:
				return 0;
		}
	}

	@Override
	public int getHTMLLineWidth() {
		switch (tag) {
			case "hr":
				return -1;
			default:
				return super.getHTMLLineWidth();
		}
	}
}
