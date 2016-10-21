/**
 * SimpleBrowser
 * A simple {@link JFrame} that can print text in different colors, fonts, and can even print some special characters (like horizontal rules).
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 20th, 2016
 */

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

import java.util.ArrayList;

public class SimpleBrowser extends JFrame {

	private static final int DEFAULT_WINDOW_WIDTH = 1000;
	private static final int DEFAULT_WINDOW_HEIGHT = 750;

	private final int SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final int SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	private final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, 21);
	private final Color DEFAULT_COLOR = Color.BLACK;

	private int windowWidth, windowHeight;
	private TextWindow textWindow;
	private ArrayList<Line> lines;
	private JScrollBar verticalBar, horizontalBar;
	private Font currentFont;
	private Color currentColor;

	/**
	 * Main constructor settings width and height.
	 * @param  windowWidth  width of the frame
	 * @param  windowHeight height of the frame
	 */
	public SimpleBrowser(int windowWidth, int windowHeight) {
		super("Simple Browser");
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		currentFont = DEFAULT_FONT;
		currentColor = DEFAULT_COLOR;
		setSize(windowWidth, windowHeight);
		center();
		setVisible(true);
		setLayout(null);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		createWindow();
		addScrollBars();
		lines = new ArrayList<Line>();
		addOnResize();
	}

	/**
	 * Constructor without width and height (uses defaults)
	 */
	public SimpleBrowser() {
		this(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
	}

	/**
	 * Centers JFrame on screen
	 */
	private void center() {
		setLocation((SCREEN_WIDTH - windowWidth) / 2, (SCREEN_HEIGHT - windowHeight) / 2);
	}

	/**
	 * Adds scrollbars to JFrame
	 */
	private void addScrollBars() {
		// JScrollBar horizontalBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 5, 0, 300);
		// horizontalBar.setLocation(0, windowHeight - verticalInset - 15);
		// horizontalBar.setSize(windowWidth - horizontalInset - 10, 18);
		verticalBar = new JScrollBar(JScrollBar.VERTICAL, 0, 10, 0, windowHeight * 2);
		verticalBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textWindow.scrollY(e.getValue());
			}
		});
		positionBars();
		// add(horizontalBar);
		add(verticalBar);
	}

	/**
	 * Positions the scroll bars on the page
	 */
	public void positionBars() {
		int verticalInset = this.getInsets().top;
		int horizontalInset = this.getInsets().right + this.getInsets().left;
		verticalBar.setLocation(windowWidth - 15, 0);
		verticalBar.setSize(16, windowHeight - verticalInset + 1);
	}

	/**
	 * Adds event to be called on resize
	 */
	public void addOnResize() {
		addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				windowWidth = getBounds().width;
				windowHeight = getBounds().height;
				positionBars();
				textWindow.setWindowSize(windowWidth - 15, windowHeight);
			}

			public void componentHidden(ComponentEvent e) {}
			public void componentShown(ComponentEvent e) {}
			public void componentMoved(ComponentEvent e) {}
		});
	}

	/**
	 * Adds str to array of {@link Line}s with given font and color.
	 * @param str   the string to print
	 * @param font  the {@link Font} to use
	 * @param color the {@link Color} to use
	 */
	public void println(String str, Font font, Color color) {
		currentFont = font;
		currentColor = color;
		str += " "; // for trailing \n
		str = str.replace("\t", "    ");
		String[] strings = str.split("\n");

		for (int i = 0; i < strings.length; i++)
			lines.add(new Line(strings[i], font, color, textWindow));

		textWindow.printLines(lines);
		overlayScrollBars();
	}

	/**
	 * Adds str to array of {@link Line}s with given font.
	 * @param str  the string to print
	 * @param font the {@link Font} to use
	 */
	public void println(String str, Font font) {
		println(str, font, currentColor);
	}

	/**
	 * Adds str to array of {@link Line}s with given color.
	 * @param str   the string to print
	 * @param color the {@link Color} to use
	 */
	public void println(String str, Color color) {
		println(str, currentFont, color);
	}

	/**
	 * Adds str to array of {@link Line}s.
	 * @param str the string to print
	 */
	public void println(String str) {
		println(str, currentFont);
	}

	/**
	 * Adds an empty line to the array of lines
	 */
	public void println() {
		println("");
	}

	/**
	 * Adds a horizontal rule to the array of lines
	 */
	public void printHR() {
		lines.add(new SpecialLine("hr", currentFont, currentColor, textWindow));
	}

	/**
	 * Sets the current font
	 * @param font the {@link Font} to set
	 */
	public void setFont(Font font) {
		currentFont = font;
	}

	/**
	 * Sets the current color
	 * @param color the {@link Color} to set
	 */
	public void setColor(Color color) {
		currentColor = color;
	}

	/**
	 * Creates the {@link TextWindow} for this class to fill whole screen except scrollbars
	 */
	private void createWindow() {
		textWindow = new TextWindow(windowWidth, windowHeight);
		textWindow.setLocation(0, 0);
		textWindow.setSize(new Dimension(windowWidth - 15, windowHeight));
		add(textWindow);
	}

	/**
	 * Overlays scroll bars so they stop vanishing
	 */
	private void overlayScrollBars() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				verticalBar.updateUI();
			}
		});
	}

	public static void main(String... pumpkins) {
		SimpleBrowser simpleBrowser = new SimpleBrowser(500, 500);
		simpleBrowser.println("Hello World", new Font("Serif", Font.PLAIN, 18));
		simpleBrowser.println("Other World", new Font("SansSerif", Font.ITALIC, 50));
		simpleBrowser.println("<hr>");
		simpleBrowser.println("Multiple\nangry lines", new Font("Arial", Font.BOLD, 10));
		simpleBrowser.println();
		simpleBrowser.println("I just printed an empty line");
		simpleBrowser.printHR();
		simpleBrowser.println("And now, for some normal text", new Font("Serif", Font.PLAIN, 22));

		simpleBrowser.println("HUGE TEXT :D\n", new Font("Times New Roman", Font.BOLD, 250));

		simpleBrowser.setColor(Color.BLUE);
		simpleBrowser.setFont(new Font("Arial", Font.PLAIN, 22));
		simpleBrowser.println("This should be blue now XD");
		simpleBrowser.println("And now red", Color.RED);
		simpleBrowser.printHR();
		simpleBrowser.println("\t\tI'm glad this works!", Color.GREEN);
	}
}
