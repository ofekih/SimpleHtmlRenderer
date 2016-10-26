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
 */
public class HtmlTag implements HtmlComponent {

	/**
	 * The {@link Color} to render with.
	 */
	private Color color;

	/**
	 * The HTML tag that this {@code HtmlTag} represents.
	 */
	private String tag;

	/**
	 * The height of this {@code HtmlTag}.
	 */
	private int lineHeight;

	/**
	 * SpecialHtmlLine constructor with all the necessary values.
	 * 
	 * @param  tag        The tag that this {@code HtmlTag} will represent
	 * @param  color      The {@code Color} to use when rendering
	 * @param  lineHeight the height of this {@code HtmlComponent}
	 */
	public HtmlTag(String tag, Color color, int lineHeight) {
		this.color = color;
		this.tag = tag;
		this.lineHeight = lineHeight;
	}

	/**
	 * Returns the HTML tag that this {@code HtmlTag} represents.
	 *
	 * @return The tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Returns the {@code Color} that this {@code HtmlTag} uses to render.
	 *
	 * @return The {@code Color}
	 */
	@Override
	public Color getColor() {
		return color;
	}

	/**
	 * The height of this {@code HtmlTag}, in pixels; this is equal to
	 * its line height.
	 */
	@Override
	public int getHtmlComponentHeight() {
		return lineHeight;
	}

	/**
	 * Returns the width of this {@code HtmlTag}. Because {@code HtmlTag}s do
	 * not have a well defined width, this method returns -1, allowing
	 * {@code HtmlPrinter} to ignore this value when it is calculating the
	 * width of itself.
	 */
	@Override
	public int getHtmlComponentWidth() {
		// HtmlTags have variable width and resize to fit available space; thus
		// they should not change the width calculation
		return -1;
	}
}
