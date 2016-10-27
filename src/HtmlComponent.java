import java.awt.Color;

/**
 * An abstract class that can be drawn by a {@link HtmlCanvas}. Every
 * {@code HtmlComponent} must have a {@link Color}, width, and height in order
 * to be drawn.
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 21st, 2016
 * @see HtmlFragment
 * @see HtmlTag
 * @see HtmlPrinter
 * @see HtmlCanvas
 */
public abstract class HtmlComponent {

	/**
	 * The {@link Color} to render with.
	 */
	private final Color color;

	/**
	 * The width in pixels of this {@code HtmlComponent}.
	 */
	private final int htmlComponentWidth;

	/**
	 * The height in pixels of this {@code HtmlComponent}.
	 */
	private final int htmlComponentHeight;

	/**
	 * A constructor for the {@code HtmlComponent} with the color, width, and
	 * height of the component.
	 *
	 * @param  color               the {@link Color} of this component
	 * @param  htmlComponentWidth  the width in pixels of this component
	 * @param  htmlComponentHeight the height in pixels of this component
	 */
	public HtmlComponent(Color color, int htmlComponentWidth,
		int htmlComponentHeight) {
		this.color = color;
		this.htmlComponentWidth = htmlComponentWidth;
		this.htmlComponentHeight = htmlComponentHeight;
	}

	/**
	 * Returns the {@code Color} that this {@code HtmlComponent} uses to render
	 * its text.
	 *
	 * @return The {@code Color}
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Returns the width of this {@code HtmlComponent}, in pixels.
	 *
	 * @return The width
	 */
	public int getHtmlComponentWidth() {
		return this.htmlComponentWidth;
	}

	/**
	 * Returns the height of this {@code HtmlComponent}, in pixels.
	 *
	 * @return The height
	 */
	public int getHtmlComponentHeight() {
		return this.htmlComponentHeight;
	}
}
