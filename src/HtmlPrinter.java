import java.awt.Font;
import java.awt.Color;

import java.util.List;
import java.util.ArrayList;

/**
 * A client that manages printing to a {@link HtmlCanvas}. Usually
 * {@link HtmlPrinter} is managed by {@link SimpleHtmlRenderer}; as such,
 * constructing one is rare. Use {@link SimpleHtmlRenderer#getHtmlPrinter} to
 * access it when necessary.
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 21st, 2016
 */
public class HtmlPrinter {
	/**
	 * The default {@code Font} used for rendering Paragraph text.
	 */
	public static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 25);

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
	private static final int BREAK_SIZE = 25;

	/**
	 * The height, in pixels, of a Horizontal Rule.
	 */
	private static final int HORIZONTAL_RULE_SIZE = 8;

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
	 * Whether this {@code HtmlPrinter} should prevent automatic drawing after every
	 * print statement, for performance reasons. The default value of this is
	 * false.
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
	 * Prints a string with a specific {@code Font} and {@code Color}.
	 *
	 * @param str   String to add
	 * @param font  {@code Font} to use
	 * @param color {@code Color} to use
	 */
	private HtmlFragment print(String str, Font font, Color color) {
		breakIfDifferentSize(font);

		HtmlFragment htmlFragment = new HtmlFragment(str, font, color, htmlCanvas);
		htmlComponents.add(htmlFragment);

		if (!preventDrawing)
			drawHtmlComponents();

		return htmlFragment;
	}

	/**
	 * Prints a string with a specific {@code Font}.
	 * @param str  the String to print
	 * @param font the {@code Font} to use
	 */
	private void print(String str, Font font) {
		print(str, font, color);
	}

	/**
	 * Prints a string with specific {@code Color}.
	 * @param str   the String to print
	 * @param color the {@code Color} to use
	 */
	private void print(String str, Color color) {
		print(str, font, color);
	}

	/**
	 * Prints a string with current formatting ({@code Color} and {@code Font}).
	 * This method is identical to printParagraph
	 * @param str the String to print
	 */
	public void print(String str) {
		print(str, font);
	}

	/**
	 * Prints a string with a specific {@code Font} and {@code Color}, breaking after.
	 * @param str   the String to print
	 * @param font  the {@code Font} to use
	 * @param color the {@code Color} to use
	 */
	private void println(String str, Font font, Color color) {
		HtmlFragment htmlFragment = print(str, font, color);
		breakComponent(htmlFragment);
	}

	/**
	 * Breaks with a height equal to to the height of the last component / fragment. Used to move onto the next line.
	 */
	public void println() {
		if (htmlComponents.isEmpty())
			printBreak();
		else breakComponent(htmlComponents.get(htmlComponents.size() - 1));
	}

	/**
	 * Inserts a break with a line height equal to the height of the last component.
	 * @param htmlComponent the {@code HtmlComponent} whose height to use
	 */
	private void breakComponent(HtmlComponent htmlComponent) {
		htmlComponents.add(new HtmlTag("br", Color.BLACK, htmlComponent.getHtmlComponentHeight()));
	}

	/**
	 * Print method to print normal text, formatted with setColor and setFont.
	 * This method is the same as print(String str)
	 * @param str the String to print
	 */
	public void printParagraph(String str) {
		print(str);
	}

	/**
	 * Prints text formatted as H1.
	 * @param str the String to print
	 */
	public void printHeading1(String str) {
		print(str, getHeadingFont(HEADING1_FONT_SIZE));
	}

	/**
	 * Prints text formatted as H2.
	 * @param str the String to print
	 */
	public void printHeading2(String str) {
		print(str, getHeadingFont(HEADING2_FONT_SIZE));
	}

	/**
	 * Prints text formatted as H3.
	 * @param str the String to print
	 */
	public void printHeading3(String str) {
		print(str, getHeadingFont(HEADING3_FONT_SIZE));
	}

	/**
	 * Prints text formatted as H4.
	 * @param str the String to print
	 */
	public void printHeading4(String str) {
		print(str, getHeadingFont(HEADING4_FONT_SIZE));
	}

	/**
	 * Prints text formatted as H5.
	 * @param str the String to print
	 */
	public void printHeading5(String str) {
		print(str, getHeadingFont(HEADING5_FONT_SIZE));
	}

	/**
	 * Prints text formatted as H6.
	 * @param str the String to print
	 */
	public void printHeading6(String str) {
		print(str, getHeadingFont(HEADING6_FONT_SIZE));
	}

	/**
	 * Prints text with a monospace font (Monospaced).
	 * @param str the String to print
	 */
	public void printPreformattedText(String str) {
		print(str, new Font(Font.MONOSPACED, font.getStyle(), font.getSize()));
	}

	/**
	 * Prints text in italics.
	 * @param str the String to print
	 */
	public void printItalic(String str) {
		print(str, new Font(font.getFontName(), font.getStyle() | Font.ITALIC, font.getSize()));
	}

	/**
	 * Prints text in bold.
	 * @param str the String to print
	 */
	public void printBold(String str) {
		print(str, new Font(font.getFontName(), font.getStyle() | Font.BOLD, font.getSize()));
	}

	/**
	 * Breaks if the previous component is not a break. This breaks using the same size as the previous component (not the default break size).
	 */
	private void breakIfNecessary() {
		if (htmlComponents.isEmpty())
			return;

		HtmlComponent previousComponent = htmlComponents.get(htmlComponents.size() - 1);
		if (!(previousComponent instanceof HtmlTag) || !((HtmlTag)previousComponent).getTag().equals("br"))
			breakComponent(previousComponent);
	}

	/**
	 * Breaks if the {@code Font} used is a different size than the previous font used.
	 * @param font the {@code Font} to use
	 */
	private void breakIfDifferentSize(Font font) {
		if (htmlComponents.isEmpty())
			return;

		HtmlComponent previousComponent = htmlComponents.get(htmlComponents.size() - 1);
		if (previousComponent instanceof HtmlFragment && ((HtmlFragment)previousComponent).getFont().getSize() != font.getSize())
			breakComponent(previousComponent);
	}

	/**
	 * Prints a break, using the default break size. If already in the middle of a line, breaks out of that line first, and then prints a break. Made to mimic the html br tag.
	 */
	public void printBreak() {
		breakIfNecessary();
		htmlComponents.add(new HtmlTag("br", color, BREAK_SIZE));
		if (!preventDrawing)
			drawHtmlComponents();
	}


	/**
	 * Prints a horizontal rule. If already in the middle of a line, breaks out of that line first, and then prints the rule. Made to mimic the html hr tag.
	 */
	public void printHorizontalRule() {
		breakIfNecessary();
		htmlComponents.add(new HtmlTag("hr", color, HORIZONTAL_RULE_SIZE));
		if (!preventDrawing)
			drawHtmlComponents();
	}

	/**
	 * Generates a heading font with the appropriate fontSize.
	 * @param  fontSize the font size of the heading
	 * @return          the appropriate heading {@code Font}
	 */
	private Font getHeadingFont(int fontSize) {
		return new Font(font.getFontName(), font.getStyle() | Font.BOLD, fontSize);
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
	 * managed by this {@code HtmlPrinter} onto the {@code HtmlCanvas}. Preventing
	 * automatic redrawing improves performace, but
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
	 * this {@code HtmlPrinter} onto the {@code HtmlCanvas}. Improves performace, but
	 * {@link HtmlPrinter#drawHtmlComponents} must be called after printing is
	 * done to display changes.
	 */
	public void preventDrawing() {
		this.preventDrawing = true;
	}
}
