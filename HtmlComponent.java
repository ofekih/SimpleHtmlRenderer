import java.awt.Color;

/**
 * Interface to be used by all components to be drawn using the {@link HtmlCanvas}. Defines their color, width, and height.
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 21st, 2016
 */
public interface HtmlComponent {
	/**
	 * Returns the {@link Color} of this {@link HtmlComponent}.
	 * @return the {@link Color} to draw this component with.
	 */
	public Color getColor();

	/**
	 * Returns the width of this component in pixels.
	 * @return the width of this component
	 */
	public int getHtmlComponentWidth();

	/**
	 * Returns the height of this component in pixels.
	 * @return the height of this component
	 */
	public int getHtmlComponentHeight();
}