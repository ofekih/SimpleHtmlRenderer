import java.awt.Font;
import java.awt.Color;

import java.util.List;
import java.util.ArrayList;

/**
 * A client that manages printing to a {@link HtmlCanvas}. Usually
 * {@code HtmlPrinter} is managed by {@link SimpleHtmlRenderer}; as such,
 * constructing one is rare. Use {@link SimpleHtmlRenderer#getHtmlPrinter} to
 * access it when necessary.
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 21st, 2016
 * @see SimpleHtmlRenderer
 * @see HtmlCanvas
 */
public class HtmlPrinter {
	/**
	 * The default {@code Font} used for rendering Paragraph text.
	 */
	public static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF,
		Font.PLAIN, 16);

	/**
	 * The default {@code Color} used for rendering Paragraph text.
	 */
	public static final Color DEFAULT_COLOR = Color.BLACK;

	/**
	 * The {@code Font} size used for rendering Heading 1.
	 */
	private static final int HEADING1_FONT_SIZE = 32;

	/**
	 * The {@code Font} size used for rendering Heading 2.
	 */
	private static final int HEADING2_FONT_SIZE = 24;

	/**
	 * The {@code Font} size used for rendering Heading 3.
	 */
	private static final int HEADING3_FONT_SIZE = 19;

	/**
	 * The {@code Font} size used for rendering Heading 4.
	 */
	private static final int HEADING4_FONT_SIZE = 15;

	/**
	 * The {@code Font} size used for rendering Heading 5.
	 */
	private static final int HEADING5_FONT_SIZE = 13;

	/**
	 * The {@code Font} size used for rendering Heading 6.
	 */
	private static final int HEADING6_FONT_SIZE = 11;

	/**
	 * The height, in pixels, of a Break.
	 */
	private static final int BREAK_HEIGHT = 25;

	/**
	 * The height, in pixels, of a Horizontal Rule.
	 */
	private static final int HORIZONTAL_RULE_HEIGHT = 8;

	/**
	 * The width, in pixels, of a single Monospaced character.
	 */
	private final int MONOSPACED_CHAR_WIDTH;

	/**
	 * The {@code SimpleHtmlRenderer} that contains this {@code HtmlPrinter}.
	 */
	private SimpleHtmlRenderer browser;

	/**
	 * The {@code HtmlCanvas} to draw on.
	 */
	private HtmlCanvas htmlCanvas;

	/**
	 * The {@code List} of {@code HtmlComponents} to draw on the canvas.
	 */
	private List<HtmlComponent> htmlComponents;

	/**
	 * The current {@code Font} used for rendering.
	 */
	private Font font;

	/**
	 * The current {@code Color} used for rendering.
	 */
	private Color color;

	/**
	 * Whether this {@code HtmlPrinter} should prevent automatic drawing after
	 * every print statement, for performance reasons. The default value of this
	 * is false.
	 */
	private boolean preventDrawing;

	/**
	 * Constructs a {@code HtmlPrinter} with a containing
	 * {@code SimpleHtmlRenderer} and a {@code SimpleHtmlRenderer} to draw on.
	 *
	 * @param  browser    The {@code SimpleHtmlRenderer} container
	 * @param  htmlCanvas The {@code HtmlCanvas} for drawing
	 */
	public HtmlPrinter(SimpleHtmlRenderer browser, HtmlCanvas htmlCanvas) {
		this.browser = browser;
		this.htmlCanvas = htmlCanvas;

		font = DEFAULT_FONT;
		color = DEFAULT_COLOR;
		htmlComponents = new ArrayList<HtmlComponent>();
		preventDrawing = false;

		MONOSPACED_CHAR_WIDTH = htmlCanvas.getFontMetrics(new Font(
			Font.MONOSPACED, Font.PLAIN, DEFAULT_FONT.getSize()))
			.stringWidth(" ");
	}

	/**
	 * Returns the {@code List} of {@code HtmlComponents} that this
	 * {@code HtmlPrinter} manages.
	 *
	 * @return The {@code List} of {@code HtmlComponents}
	 */
	public List<HtmlComponent> getHtmlComponents() {
		return htmlComponents;
	}

	/**
	 * Draws the {@code HtmlComponent}s managed by this {@code HtmlPrinter} onto
	 * the {@code HtmlCanvas} and deals with associated canvas resizing. This
	 * method should only be called if automatic drawing prevention is enabled.
	 */
	public void drawHtmlComponents() {
		htmlCanvas.repaint();
		browser.cleanupAfterPrint();
	}

	/**
	 * Prints a {@code String} with the current {@code Color} and {@code Font}.
	 *
	 * @param string The {@code String} to print
	 */
	public void print(String string) {
		print(string, font);
	}

	/**
	 * Prints a {@code String} with a specific {@code Font} and the current
	 * {@code Color}.
	 *
	 * @param string The {@code String} to print
	 * @param font   The {@code Font} to use
	 */
	private void print(String string, Font font) {
		print(string, font, color);
	}

	/**
	 * Prints {@code String} with specific {@code Color} and the current
	 * {@code Font}.
	 *
	 * @param string The {@code String} to print
	 * @param color  The {@code Color} to use
	 */
	private void print(String string, Color color) {
		print(string, font, color);
	}

	/**
	 * Prints a {@code String} with a specific {@code Font} and {@code Color},
	 * breaking after.
	 *
	 * @param string The String to print
	 * @param font   The {@code Font} to use
	 * @param color  The {@code Color} to use
	 */
	private void println(String string, Font font, Color color) {
		print(string, font, color);
		breakComponent(getLastComponent());
	}

	/**
	 * Prints a {@code String} with a specific {@code Font} and {@code Color}.
	 *
	 * @param string The {@code String} to print
	 * @param font   The {@code Font} to use
	 * @param color  The {@code Color} to use
	 */
	private void print(String string, Font font, Color color) {
		breakIfDifferentSize(font);

		htmlComponents.add(new HtmlFragment(string, font, color,
			htmlCanvas));

		if (!preventDrawing)
			drawHtmlComponents();
	}

	/**
	 * Breaks if the {@code Font} used is a different size than the previous
	 * font used.
	 *
	 * @param font The {@code Font} to use for breaking
	 */
	private void breakIfDifferentSize(Font font) {
		if (htmlComponents.isEmpty())
			return;

		HtmlComponent previousComponent = getLastComponent();
		if (previousComponent instanceof HtmlFragment &&
			((HtmlFragment)previousComponent).getFont().getSize() !=
			font.getSize())
			breakComponent(previousComponent);
	}

	/**
	 * Moves {@code HtmlPrinter}'s cursor down one line and returns it to the
	 * left-hand margin.
	 */
	public void println() {
		if (htmlComponents.isEmpty())
			printBreak();
		else breakComponent(getLastComponent());
	}

	/**
	 * Prints a {@code String} formatted as Header 1, with the current {@code Font}
	 * and {@code Color}.
	 *
	 * @param string The String to print
	 */
	public void printHeading1(String string) {
		print(string, getHeadingFont(HEADING1_FONT_SIZE));
	}

	/**
	 * Prints a {@code String} formatted as Header 2, with the current {@code Font}
	 * and {@code Color}.
	 *
	 * @param string The String to print
	 */
	public void printHeading2(String string) {
		print(string, getHeadingFont(HEADING2_FONT_SIZE));
	}

	/**
	 * Prints a {@code String} formatted as Header 3, with the current {@code Font}
	 * and {@code Color}.
	 *
	 * @param string The String to print
	 */
	public void printHeading3(String string) {
		print(string, getHeadingFont(HEADING3_FONT_SIZE));
	}

	/**
	 * Prints a {@code String} formatted as Header 4, with the current {@code Font}
	 * and {@code Color}.
	 *
	 * @param string The String to print
	 */
	public void printHeading4(String string) {
		print(string, getHeadingFont(HEADING4_FONT_SIZE));
	}

	/**
	 * Prints a {@code String} formatted as Header 5, with the current {@code Font}
	 * and {@code Color}.
	 *
	 * @param string The String to print
	 */
	public void printHeading5(String string) {
		print(string, getHeadingFont(HEADING5_FONT_SIZE));
	}

	/**
	 * Prints a {@code String} formatted as Header 6, with the current {@code Font}
	 * and {@code Color}.
	 *
	 * @param string The String to print
	 */
	public void printHeading6(String string) {
		print(string, getHeadingFont(HEADING6_FONT_SIZE));
	}

	/**
	 * Generates a heading {@code Font} for the {@code Font} size given.
	 *
	 * @param  size The {@code Font} size of the heading
	 * @return The {@code Font}
	 */
	private Font getHeadingFont(int size) {
		return new Font(font.getFontName(), font.getStyle() | Font.BOLD, size);
	}

	/**
	 * Prints a {@code String} formatted as Preformatted Text, with a monospaced
	 * {@code Font} and the current {@code Color}.
	 *
	 * @param string The String to print
	 */
	public void printPreformattedText(String string) {
		print(string, new Font(Font.MONOSPACED, font.getStyle(),
			font.getSize()));
	}

	/**
	 * Prints a {@code String} formatted as Italic, with a bold {@code Font}
	 * and the current {@code Color}.
	 *
	 * @param string The String to print
	 */
	public void printBold(String string) {
		print(string, new Font(font.getFontName(), font.getStyle() | Font.BOLD,
			font.getSize()));
	}

	/**
	 * Prints a {@code String} formatted as Italic, with a italic {@code Font}
	 * and the current {@code Color}.
	 *
	 * @param string The String to print
	 */
	public void printItalic(String string) {
		print(string, new Font(font.getFontName(), font.getStyle() |
				Font.ITALIC, font.getSize()));
	}

	/**
	 * Prints a Break, using the default break size. If already in the middle of
	 * a line, breaks out of that line first, and then prints a break.
	 */
	public void printBreak() {
		breakIfNecessary();
		htmlComponents.add(new HtmlTag("br", color, 0, BREAK_HEIGHT));
		if (!preventDrawing)
			drawHtmlComponents();
	}

	/**
	 * Prints a Horizontal Rule. If already in the middle of a line, breaks out
	 * of that line first, and then prints the rule.
	 */
	public void printHorizontalRule() {
		breakIfNecessary();
		htmlComponents.add(new HtmlTag("hr", color, 0, HORIZONTAL_RULE_HEIGHT));
		if (!preventDrawing)
			drawHtmlComponents();
	}

	/**
	 * Prints a break, but only if the last component is not a {@code HtmlTag}.
	 */
	private void breakIfNecessary() {
		if (htmlComponents.isEmpty())
			return;

		HtmlComponent previousComponent = getLastComponent();
		if (!(previousComponent instanceof HtmlTag) ||
			!((HtmlTag)previousComponent).getTag().equals("br"))
			breakComponent(previousComponent);
	}

	/**
	 * Moves {@code HtmlPrinter}'s cursor down by the height of the specified
	 * {@code HtmlComponent} and returns it to the left-hand margin.
	 *
	 * @param htmlComponent The {@code HtmlComponent} used to calculate height
	 */
	private void breakComponent(HtmlComponent htmlComponent) {
		htmlComponents.add(new HtmlTag("br", Color.BLACK, 0,
			htmlComponent.getHtmlComponentHeight()));
	}

	/**
	 * Returns the last {@link HtmlComponent} on the queue.
	 * @return the last {@link HtmlComponent} on the queue.
	 */
	private HtmlComponent getLastComponent() {
		return htmlComponents.get(htmlComponents.size() - 1);
	}

	/**
	 * Draws a vertical line after the specified number of characters.
	 *
	 * @param lineMarkColumns The column number to print the mark at
	 */
	public void drawLineMark(int lineMarkColumns) {
		htmlCanvas.setLineMark(lineMarkColumns * MONOSPACED_CHAR_WIDTH);
		if (!preventDrawing)
			drawHtmlComponents();
	}

	/**
	 * Hides the {@link HtmlCanvas}'s line mark.
	 */
	public void hideLineMark() {
		htmlCanvas.hideLineMark();
		if (!preventDrawing)
			drawHtmlComponents();
	}

	/**
	 * Sets the {@code Font} to be used for rendering Paragraph text.
	 *
	 * @param font The {@code Font} to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Sets the {@code Color} to be used for rendering Paragraph text.
	 *
	 * @param color The {@code Color} to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Sets prevention of automatic redrawing of the {@code HtmlComponent}s
	 * managed by this {@code HtmlPrinter} onto the {@code HtmlCanvas}.
	 * Preventing automatic redrawing improves performance, but
	 * {@link HtmlPrinter#drawHtmlComponents} must be called after printing is
	 * done to display changes.
	 *
	 * @param preventDrawing Whether to enable automatic drawing prevention
	 */
	public void setPreventDrawing(boolean preventDrawing) {
		this.preventDrawing = preventDrawing;
	}

	/**
	 * Prevents automatic redrawing of the {@code HtmlComponent}s managed by
	 * this {@code HtmlPrinter} onto the {@code HtmlCanvas}. Improves
	 * performance, but {@link HtmlPrinter#drawHtmlComponents} must be called
	 * after printing is done to display changes.
	 */
	public void preventDrawing() {
		this.preventDrawing = true;
	}
}
