import java.awt.Font;
import java.awt.Color;

import java.util.List;
import java.util.ArrayList;

public class HTMLPrinter {

	private SimpleBrowser browser;
	private TextWindow textWindow;
	private List<Line> lines;
	private Font currentFont;
	private Color currentColor;

	public static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 25);
	public static final Color DEFAULT_COLOR = Color.BLACK;

	/**
	 * HTMLPrinter constructor, taking a browser and textWindow instance.
	 * @param  browser    the {@link SimpleBrowser} instance
	 * @param  textWindow the {@link textWindow} instance
	 */
	public HTMLPrinter(SimpleBrowser browser, TextWindow textWindow) {
		this.browser = browser;
		this.textWindow = textWindow;

		currentFont = DEFAULT_FONT;
		currentColor = DEFAULT_COLOR;
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
		println(str, font, currentColor);
	}

	/**
	 * Adds str to array of {@link Line}s with given color.
	 * @param str   the string to print
	 * @param color the {@link Color} to use
	 */
	private void println(String str, Color color) {
		println(str, currentFont, color);
	}

	/**
	 * Adds str to array of {@link Line}s.
	 * @param str the string to print
	 */
	private void println(String str) {
		println(str, currentFont);
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
	public void print(String str) {
		println(str);
	}

	/**
	 * Adds a horizontal rule to the array of lines
	 */
	public void printHR() {
		lines.add(new SpecialLine("hr", currentFont, currentColor, textWindow));
		browser.cleanupAfterPrint();
	}

	/**
	 * Prints a line formatted as H1
	 * @param str the line to print
	 * @return    an pointer to this printer
	 */
	public HTMLPrinter printH1(String str) {
		println(str, getHeadingFont(32));
		return this;
	}

	/**
	 * Prints a line formatted as H2
	 * @param str the line to print
	 * @return    an pointer to this printer
	 */
	public HTMLPrinter printH2(String str) {
		println(str, getHeadingFont(24));
		return this;
	}

	/**
	 * Prints a line formatted as H3
	 * @param str the line to print
	 * @return    an pointer to this printer
	 */
	public HTMLPrinter printH3(String str) {
		println(str, getHeadingFont(19));
		return this;
	}

	/**
	 * Prints a line formatted as H4
	 * @param str the line to print
	 * @return    an pointer to this printer
	 */
	public HTMLPrinter printH4(String str) {
		println(str, getHeadingFont(15));
		return this;
	}

	/**
	 * Prints a line formatted as H5
	 * @param str the line to print
	 * @return    an pointer to this printer
	 */
	public HTMLPrinter printH5(String str) {
		println(str, getHeadingFont(13));
		return this;
	}

	/**
	 * Prints a line formatted as H6
	 * @param str the line to print
	 * @return    an pointer to this printer
	 */
	public HTMLPrinter printH6(String str) {
		println(str, getHeadingFont(11));
		return this;
	}

	/**
	 * Prints a pre-formatted line (monospace)
	 * @param str the line to print
	 * @return    an pointer to this printer
	 */
	public HTMLPrinter printPre(String str) {
		println(str, new Font("Monospaced", DEFAULT_FONT.getStyle(), DEFAULT_FONT.getSize()));
		return this;
	}

	/**
	 * Generates a heading font given px size
	 * Helper method for setFontByTag
	 * @param sizePx font size in pixels
	 * @return       the font
	 */
	private Font getHeadingFont(int sizePx) {
		return new Font("SansSerif", Font.BOLD, sizePx);
	}

	/**
	 * Sets the default font to be used when no other is specified
	 * @param font the {@link Font} to set
	 * @return     an pointer to this printer
	 */
	public HTMLPrinter setFont(Font font) {
		currentFont = font;
		return this;
	}

	/**
	 * Sets the default color to be used when no other is specified
	 * @param color the {@link Color} to set
	 * @return      an pointer to this printer
	 */
	public HTMLPrinter setColor(Color color) {
		currentColor = color;
		return this;
	}
}