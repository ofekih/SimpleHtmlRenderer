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

	private static final int HORIZONTAL_RULE_SIZE = 8;

	private SimpleBrowser browser;
	private TextWindow textWindow;
	private List<HtmlLine> lines;
	private Font font;
	private Color color;
	private boolean preventDrawing;

	/**
	 * HtmlPrinter constructor, taking a browser and textWindow instance.
	 * @param  browser    the {@link SimpleBrowser} instance
	 * @param  textWindow the {@link textWindow} instance
	 */
	public HtmlPrinter(SimpleBrowser browser, TextWindow textWindow) {
		this.browser = browser;
		this.textWindow = textWindow;

		font = DEFAULT_FONT;
		color = DEFAULT_COLOR;
		lines = new ArrayList<HtmlLine>();
		preventDrawing = false;
	}

	/**
	 * Draws the lines on the {@link TextArea}
	 */
	public void drawHtmlLines() {
		textWindow.printHtmlLines(lines);
		browser.cleanupAfterPrint();
	}

	/**
	 * Prints without new line, adding to old line.
	 * @param str   String to add
	 * @param font  {@link Font} to use
	 * @param color {@link Color} to use
	 */
	public void print(String str, Font font, Color color) {
		if (str.lastIndexOf('\n') == str.length() - 1)
			str += " "; // for trailing \n
		str = str.replace("\t", "    ");
		String[] strings = str.split("\n");

		for (int i = 0; i < strings.length; i++)
			if (i == 0 && lines.size() > 0)
				lines.get(lines.size() - 1).append(strings[i]);
			else lines.add(new HtmlLine(strings[i], font, color, textWindow));

		if (!preventDrawing)
			drawHtmlLines();
	}

	/**
	 * Adds str to array of {@link HtmlLine}s with given font and color.
	 * @param str   the string to print
	 * @param font  the {@link Font} to use
	 * @param color the {@link Color} to use
	 */
	private void println(String str, Font font, Color color) {
		if (str.lastIndexOf('\n') == str.length() - 1)
			str += " "; // for trailing \n
		str = str.replace("\t", "    ");
		String[] strings = str.split("\n");

		for (String string : strings)
			lines.add(new HtmlLine(string, font, color, textWindow));

		if (!preventDrawing)
			drawHtmlLines();
	}

	/**
	 * Adds str to array of {@link HtmlLine}s with given font.
	 * @param str  the string to print
	 * @param font the {@link Font} to use
	 */
	private void println(String str, Font font) {
		println(str, font, color);
	}

	/**
	 * Adds str to array of {@link HtmlLine}s with given color.
	 * @param str   the string to print
	 * @param color the {@link Color} to use
	 */
	private void println(String str, Color color) {
		println(str, font, color);
	}

	/**
	 * Adds str to array of {@link HtmlLine}s.
	 * @param str the string to print
	 */
	private void println(String str) {
		println(str, font);
	}

	/**
	 * Adds an empty line to the array of lines
	 */
	private void println() {
		println("");
	}

	/**
	 * Print method to print normal text, formatted with setColor and setFont
	 * @param str the String to print
	 */
	public void printParagraph(String str) {
		println(str);
	}

	/**
	 * Prints a line formatted as H1
	 * @param str the line to print
	 * @return    an pointer to this printer
	 */
	public void printHeading1(String str) {
		println(str, getHeadingFont(HEADING1_FONT_SIZE));
	}

	/**
	 * Prints a line formatted as H2
	 * @param str the line to print
	 */
	public void printHeading2(String str) {
		println(str, getHeadingFont(HEADING2_FONT_SIZE));
	}

	/**
	 * Prints a line formatted as H3
	 * @param str the line to print
	 */
	public void printHeading3(String str) {
		println(str, getHeadingFont(HEADING3_FONT_SIZE));
	}

	/**
	 * Prints a line formatted as H4
	 * @param str the line to print
	 */
	public void printHeading4(String str) {
		println(str, getHeadingFont(HEADING4_FONT_SIZE));
	}

	/**
	 * Prints a line formatted as H5
	 * @param str the line to print
	 */
	public void printHeading5(String str) {
		println(str, getHeadingFont(HEADING5_FONT_SIZE));
	}

	/**
	 * Prints a line formatted as H6
	 * @param str the line to print
	 */
	public void printHeading6(String str) {
		println(str, getHeadingFont(HEADING6_FONT_SIZE));
	}

	/**
	 * Adds a pre-formatted {@link HtmlLine} (monospace)
	 * @param str the text to add
	 */
	public void printPreformattedText(String str) {
		println(str, new Font(Font.MONOSPACED, font.getStyle(), font.getSize()));
	}

	/**
	 * Adds an italic {@link HtmlLine}
	 * @param str the text to add
	 */
	public void printItalic(String str) {
		println(str, new Font(font.getFontName(), font.getStyle() | Font.ITALIC, font.getSize()));
	}

	/**
	 * Adds a bolded {@link HtmlLine}
	 * @param str the text to add
	 */
	public void printBold(String str) {
		println(str, new Font(font.getFontName(), font.getStyle() | Font.BOLD, font.getSize()));
	}

	/**
	 * Adds a horizontal rule to the array of lines
	 */
	public void printHorizontalRule() {
		lines.add(new SpecialHtmlLine("hr", font, color, textWindow, HORIZONTAL_RULE_SIZE));
		if (!preventDrawing)
			drawHtmlLines();
	}

	public void printBreak() {
		lines.add(new SpecialHtmlLine("br", font, color, textWindow, font.getSize()));
		if (!preventDrawing)
			drawHtmlLines();
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
	 * Setting this to false improves performance, but a call to repaint
	 * is necessary after printing is done.
	 * @param preventDrawing false if to allow drawing, true to prevent
	 */
	public void setPreventDrawing(boolean preventDrawing) {
		this.preventDrawing = preventDrawing;
	}

	/**
	 * Prevents drawing without custom drawing method
	 */
	public void preventDrawing() {
		this.preventDrawing = true;
	}
}
