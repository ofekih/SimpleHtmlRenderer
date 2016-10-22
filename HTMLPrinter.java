import java.awt.Font;
import java.awt.Color;

import java.util.List;
import java.util.ArrayList;

public class HTMLPrinter {
	public static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 25);
	public static final Color DEFAULT_COLOR = Color.BLACK;

	private static final int HEADING1_FONT_SIZE = 32;
	private static final int HEADING2_FONT_SIZE = 24;
	private static final int HEADING3_FONT_SIZE = 19;
	private static final int HEADING4_FONT_SIZE = 15;
	private static final int HEADING5_FONT_SIZE = 13;
	private static final int HEADING6_FONT_SIZE = 11;

	private SimpleBrowser browser;
	private TextWindow textWindow;
	private List<Line> lines;
	private Font font;
	private Color color;

	/**
	 * HTMLPrinter constructor, taking a browser and textWindow instance.
	 * @param  browser    the {@link SimpleBrowser} instance
	 * @param  textWindow the {@link textWindow} instance
	 */
	public HTMLPrinter(SimpleBrowser browser, TextWindow textWindow) {
		this.browser = browser;
		this.textWindow = textWindow;

		font = DEFAULT_FONT;
		color = DEFAULT_COLOR;
		lines = new ArrayList<Line>();
	}

	/**
	 * Adds str to array of {@link Line}s with given font and color.
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
			lines.add(new Line(string, font, color, textWindow));

		textWindow.printLines(lines);
		browser.cleanupAfterPrint();
	}

	/**
	 * Adds str to array of {@link Line}s with given font.
	 * @param str  the string to print
	 * @param font the {@link Font} to use
	 */
	private void println(String str, Font font) {
		println(str, font, color);
	}

	/**
	 * Adds str to array of {@link Line}s with given color.
	 * @param str   the string to print
	 * @param color the {@link Color} to use
	 */
	private void println(String str, Color color) {
		println(str, font, color);
	}

	/**
	 * Adds str to array of {@link Line}s.
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
	 * Prints a pre-formatted line (monospace)
	 * @param str the line to print
	 */
	public void printPreformattedText(String str) {
		println(str, getPreformattedTextFont());
	}

	public void printItalic(String str) {
		println(str, getItalicFont());
	}

	/**
	 * Adds a horizontal rule to the array of lines
	 */
	public void printHorizontalRule() {
		lines.add(new SpecialLine("hr", font, color, textWindow));
		browser.cleanupAfterPrint();
	}

	private Font getHeadingFont(int headingLevel) {
		return new Font(font.getFontName(), font.getStyle() | Font.BOLD, headingLevel);
	}

	private Font getPreformattedTextFont() {
		return new Font("Monospaced", font.getStyle(), font.getSize());
	}

	private Font getItalicFont() {
		return new Font(font.getFontName(), font.getStyle() | Font.ITALIC, font.getSize());
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
}