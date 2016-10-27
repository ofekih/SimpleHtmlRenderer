import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;
import javax.swing.JComponent;

/**
 * A single element of HTML to be rendered by a {@link HtmlCanvas}, containing
 * {@link String}, {@link Font} and a {@link Color}. In general, do not create
 * these directly; a {@link HtmlPrinter} will create and render them.
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 21st, 2016
 * @see HtmlComponent
 * @see HtmlCanvas
 */
public class HtmlFragment extends HtmlComponent {

	/**
	 * The text that this {@code HtmlFragment} will render.
	 */
	private final String text;

	/**
	 * The {@link Font} to render with.
	 */
	private final Font font;

	/**
	 * The distance typical from the baseline to the top of characters.
	 */
	private final int ascent;

	/**
	 * Constructs a {@code HtmlFragment} with the provided values.
	 *
	 * @param  text      The text to display
	 * @param  font      The {@code Font} to use
	 * @param  color     The {@code Color} to use
	 * @param  component The {@link JComponent} this text will will rendered on,
	 *  to generate {@code FontMetrics}
	 */
	public HtmlFragment(String text, Font font, Color color,
		JComponent component) {
		super(color, component.getFontMetrics(font).stringWidth(text),
			component.getFontMetrics(font).getHeight());
		this.text = text;
		this.font = font;
		ascent = component.getFontMetrics(font).getAscent();
	}

	/**
	 * Returns the text of this {@code HtmlFragment}.
	 *
	 * @return The text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Returns the {@code Font} that this {@code HtmlFragment} uses to render
	 * its text.
	 *
	 * @return The {@code Font}
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Returns the ascent (the distance typical from the baseline to the top of
	 * characters) of this {@code HtmlFragment}, in pixels based on its
	 * {@code Font}.
	 * @return The ascent
	 */
	public int getAscent() {
		return ascent;
	}
}
