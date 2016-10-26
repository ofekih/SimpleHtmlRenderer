import java.awt.Font;
import java.awt.Color;
import javax.swing.JComponent;

/**
 * A {@link HtmlComponent} for HTML-style tags, such as the br and hr tags.
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 21st, 2016
 */
public class HtmlTag implements HtmlComponent {

	private Color color;
	private String tag;
	private int lineHeight;

	/**
	 * SpecialHtmlLine constructor with all the necessary values.
	 * @param  tag        the tag corresponding to the symbol to display
	 * @param  color      the {@link Color} to use
	 * @param  lineHeight the height of this component
	 */
	public HtmlTag(String tag, Color color, int lineHeight) {
		this.color = color;
		this.tag = tag;
		this.lineHeight = lineHeight;
	}

	/**
	 * Returns this element's tag name.
	 * @return this element's tag name
	 */
	public String getTag() {
		return tag;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public int getHtmlComponentHeight() {
		return lineHeight;
	}

	@Override
	public int getHtmlComponentWidth() {
		// So far, the tags all have variable width, and therefore should not be taken into account when finding which element is the widest.
		return 0;
	}
}
