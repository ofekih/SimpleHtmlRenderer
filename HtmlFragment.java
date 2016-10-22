/**
 * A single element of HTML to be rendered by a {@link TextWindow}, containing
 * text, font and color. In general, do not create these directly; a
 * {@link HtmlPrinter} will create and render them.
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 20th, 2016
 */

import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;
import javax.swing.JComponent;

public class HtmlFragment implements HtmlComponent {

	private String text;
	private Font font;
	private Color color;
	private FontMetrics fontMetrics;

	/**
	 * HtmlFragment constructor with all the necessary values.
	 * @param  text      the text to display
	 * @param  font      the {@link Font} to use
	 * @param  color     the {@link Color} to use
	 * @param  component any old {@link JComponent} to get font metrics from
	 */
	public HtmlFragment(String text, Font font, Color color, JComponent component) {
		this.text = text;
		this.font = font;
		this.color = color;
		fontMetrics = component.getFontMetrics(font);
	}

	/**
	 * Appends text to this fragment.
	 * @param text String of text to append
	 * @deprecated there shouldn't be any reason to append to this fragment, just add a new {@link HtmlFragment} instead.
	 */
	@Deprecated
	public void append(String text) {
		this.text += text;
	}

	/**
	 * Returns the text content of this fragment.
	 * @return the String text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Returns the font of this fragment.
	 * @return the {@link Font}
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Gets how high below the text is offset.
	 * @return the ascent
	 */
	public int getAscent() {
		return fontMetrics.getAscent();
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public int getHtmlComponentHeight() {
		return fontMetrics.getHeight();
	}

	@Override
	public int getHtmlComponentWidth() {
		return fontMetrics.stringWidth(text);
	}
}