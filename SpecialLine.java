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

public class SpecialLine extends Line {

	private String tag;
	private int lineHeight;

	/**
	 * SpecialLine constructor with all the necessary values
	 * @param  tag       the tag corresponding to the symbol to display
	 * @param  font      the {@link Font} to use
	 * @param  color     the {@link Color} to use
	 * @param  component any old {@link JComponent} to get font metrics from
	 */
	public SpecialLine(String tag, Font font, Color color, JComponent component, int lineHeight) {
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
	public int getLineHeight() {
		return lineHeight;
	}
}
