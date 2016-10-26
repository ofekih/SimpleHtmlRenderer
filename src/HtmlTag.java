import java.awt.Font;
import java.awt.Color;
import javax.swing.JComponent;

/**
 * A {@link HtmlComponent} for HTML-style tags that cannot be expressed easily
 * as text, such as the {@code <br>} and {@code <hr>} tags.
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 21st, 2016
 * @see HtmlComponent
 * @see HtmlCanvas
 */
public class HtmlTag extends HtmlComponent {

	/**
	 * The HTML tag that this {@code HtmlTag} represents.
	 */
	private String tag;

	/**
	 * The constructor with the tag, color, width and height of the component.
	 *
	 * @param  tag        The tag that this {@code HtmlTag} will represent
	 * @param  color      The {@code Color} to use when rendering
	 * @param  lineWidth  the width of this {@code HtmlComponent}
	 * @param  lineHeight the height of this {@code HtmlComponent}
	 */
	public HtmlTag(String tag, Color color, int lineWidth, int lineHeight) {
		super(color, lineWidth, lineHeight);
		this.tag = tag;
	}

	/**
	 * Returns the HTML tag that this {@code HtmlTag} represents.
	 *
	 * @return The tag
	 */
	public String getTag() {
		return tag;
	}
}
