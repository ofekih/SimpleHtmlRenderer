/**
 * A client that manages printing to a {@link TextWindow}.
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 21st, 2016
 */

import java.awt.Font;
import java.awt.Color;

import java.util.List;
import java.util.ArrayList;

public class HtmlPrinter {
	public static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 25);
	public static final Color DEFAULT_COLOR = Color.BLACK;

	private static final int HEADING1_FONT_SIZE = 32;
	private static final int HEADING2_FONT_SIZE = 24;
	private static final int HEADING3_FONT_SIZE = 19;
	private static final int HEADING4_FONT_SIZE = 15;
	private static final int HEADING5_FONT_SIZE = 13;
	private static final int HEADING6_FONT_SIZE = 11;

	private static final int BREAK_SIZE = 25;
	private static final int HORIZONTAL_RULE_SIZE = 8;

	private SimpleBrowser browser;
	private TextWindow textWindow;
	private List<HtmlComponent> htmlComponents;
	private Font font;
	private Color color;
	private boolean preventDrawing;

	/**
	 * HtmlPrinter constructor, taking a browser and textWindow instance.
	 * @param  browser    the {@link SimpleBrowser} instance
	 * @param  textWindow the {@link TextWindow} instance
	 */
	public HtmlPrinter(SimpleBrowser browser, TextWindow textWindow) {
		this.browser = browser;
		this.textWindow = textWindow;

		font = DEFAULT_FONT;
		color = DEFAULT_COLOR;
		htmlComponents = new ArrayList<HtmlComponent>();
		preventDrawing = false;
	}

	/**
	 * Draws the {@link HtmlComponent}s onto the {@link TextWindow}.
	 */
	public void drawHtmlComponents() {
		textWindow.printHtmlComponents(htmlComponents);
		browser.cleanupAfterPrint();
	}

	/**
	 * Prints a string with a specific {@link Font} and {@link Color}.
	 * @param str   String to add
	 * @param font  {@link Font} to use
	 * @param color {@link Color} to use
	 */
	public HtmlFragment print(String str, Font font, Color color) {
		breakIfDifferentSize(font);

		HtmlFragment htmlFragment = new HtmlFragment(str, font, color, textWindow);
		htmlComponents.add(htmlFragment);

		if (!preventDrawing)
			drawHtmlComponents();

		return htmlFragment;
	}

	/**
	 * Prints a string with a specific {@link Font}.
	 * @param str  the String to print
	 * @param font the {@link Font} to use
	 */
	private void print(String str, Font font) {
		print(str, font, color);
	}

	/**
	 * Prints a string with specific {@link Color}.
	 * @param str   the String to print
	 * @param color the {@link Color} to use
	 */
	private void print(String str, Color color) {
		print(str, font, color);
	}

	/**
	 * Prints a string with current formatting ({@link Color} and {@link Font}).
	 * This method is identical to printParagraph
	 * @param str the String to print
	 */
	public void print(String str) {
		print(str, font);
	}

	/**
	 * Prints a string with a specific {@link Font} and {@link Color}, breaking after.
	 * @param str   the String to print
	 * @param font  the {@link Font} to use
	 * @param color the {@link Color} to use
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
	 * @param htmlComponent the {@link HtmlComponent} whose height to use
	 */
	private void breakComponent(HtmlComponent htmlComponent) {
		htmlComponents.add(new HtmlTag("br", Color.BLACK, htmlComponent.getHtmlComponentHeight()));
	}

	/**
	 * Print method to print normal text, formatted with setColor and setFont
	 * This method is the same as print(String str)
	 * @param str the String to print
	 */
	public void printParagraph(String str) {
		print(str);
	}

	/**
	 * Prints text formatted as H1
	 * @param str the String to print
	 * @return    an pointer to this printer
	 */
	public void printHeading1(String str) {
		print(str, getHeadingFont(HEADING1_FONT_SIZE));
	}

	/**
	 * Prints text formatted as H2
	 * @param str the String to print
	 */
	public void printHeading2(String str) {
		print(str, getHeadingFont(HEADING2_FONT_SIZE));
	}

	/**
	 * Prints text formatted as H3
	 * @param str the String to print
	 */
	public void printHeading3(String str) {
		print(str, getHeadingFont(HEADING3_FONT_SIZE));
	}

	/**
	 * Prints text formatted as H4
	 * @param str the String to print
	 */
	public void printHeading4(String str) {
		print(str, getHeadingFont(HEADING4_FONT_SIZE));
	}

	/**
	 * Prints text formatted as H5
	 * @param str the String to print
	 */
	public void printHeading5(String str) {
		print(str, getHeadingFont(HEADING5_FONT_SIZE));
	}

	/**
	 * Prints text formatted as H6
	 * @param str the String to print
	 */
	public void printHeading6(String str) {
		print(str, getHeadingFont(HEADING6_FONT_SIZE));
	}

	/**
	 * Prints text with a monospace font (monospaced)
	 * @param str the String to print
	 */
	public void printPreformattedText(String str) {
		print(str, new Font(Font.MONOSPACED, font.getStyle(), font.getSize()));
	}

	/**
	 * Prints text in italics
	 * @param str the String to print
	 */
	public void printItalic(String str) {
		print(str, new Font(font.getFontName(), font.getStyle() | Font.ITALIC, font.getSize()));
	}

	/**
	 * Prints text in bold
	 * @param str the String to print
	 */
	public void printBold(String str) {
		print(str, new Font(font.getFontName(), font.getStyle() | Font.BOLD, font.getSize()));
	}

	/**
	 * Breaks if the previous component is not a break. This breaks using the same size as the previous component (not the default break size).
	 */
	public void breakIfNecessary() {
		if (htmlComponents.isEmpty())
			return;

		HtmlComponent previousComponent = htmlComponents.get(htmlComponents.size() - 1);
		if (!(previousComponent instanceof HtmlTag) || !((HtmlTag)previousComponent).getTag().equals("br"))
			breakComponent(previousComponent);
	}

	/**
	 * Breaks if the {@link Font} used is a different size than the previous font used.
	 * @param font the {@link Font} to use
	 */
	public void breakIfDifferentSize(Font font) {
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
	 * Generates a heading font with the appropriate fontSize
	 * @param  fontSize the font size of the heading
	 * @return          the appropriate heading {@link Font}
	 */
	private Font getHeadingFont(int headingLevel) {
		return new Font(font.getFontName(), font.getStyle() | Font.BOLD, headingLevel);
	}

	/**
	 * Sets the default font to be used when no other is specified
	 * @param font the {@link Font} to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Sets the default color to be used when no other is specified
	 * @param color the {@link Color} to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Sets whether the {@link TextWindow} should repaint after printing.
	 * Setting this to false improves performance, but a call to
	 * drawHtmlComponents is necessary after printing is done.
	 * @param preventDrawing false if to allow drawing, true to prevent
	 */
	public void setPreventDrawing(boolean preventDrawing) {
		this.preventDrawing = preventDrawing;
	}

	/**
	 * Prevents drawing by default after print statements. Must call drawHtmlComponents manually when finished printing.
	 */
	public void preventDrawing() {
		this.preventDrawing = true;
	}
}
