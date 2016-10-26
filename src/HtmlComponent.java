import java.awt.Color;

/**
 * An interface that can be drawn by a {@link HtmlCanvas}. Every
 * {@link HtmlComponent} must have a {@link Color}, width, and height in order
 * to be drawn.
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 21st, 2016
 */
public interface HtmlComponent {

	/**
	 * Returns the {@code Color} that this {@code HtmlComponent} uses to render
	 * its text.
	 *
	 * @return The {@code Color}
	 */
	public Color getColor();

	/**
	 * Returns the width of this {@code HtmlComponent}, in pixels.
	 *
	 * @return The width
	 */
	public int getHtmlComponentWidth();

	/**
	 * Returns the height of this {@code HtmlComponent}, in pixels.
	 *
	 * @return The height
	 */
	public int getHtmlComponentHeight();
}