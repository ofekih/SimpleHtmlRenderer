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

import java.util.ArrayList;

public class SimpleBrowser extends JFrame {

	private static final int DEFAULT_WINDOW_WIDTH = 1000;
	private static final int DEFAULT_WINDOW_HEIGHT = 750;

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
		setResizable(false);
		textWindow = createWindow();
		addScrollBars();
		lines = new ArrayList<Line>();
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
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)screenSize.getWidth();
		int screenHeight = (int)screenSize.getHeight();
		setLocation((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2);
	}

	/**
	 * Adds scrollbars to JFrame
	 */
	private void addScrollBars() {
		int verticalInset = this.getInsets().top;
		int horizontalInset = this.getInsets().right + this.getInsets().left;
		// JScrollBar horizontalBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 5, 0, 300);
		// horizontalBar.setLocation(0, windowHeight - verticalInset - 15);
		// horizontalBar.setSize(windowWidth - horizontalInset - 10, 18);
		verticalBar = new JScrollBar(JScrollBar.VERTICAL, 0, 10, 0, windowHeight * 2);
		verticalBar.setLocation(windowWidth - 15, 0);
		verticalBar.setSize(16, windowHeight - verticalInset);
		verticalBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textWindow.scrollY(e.getValue());
			}
		});
		// add(horizontalBar);
		add(verticalBar);
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

		if (lines.size() == 0)
			lines.add(new Line("", font, color, textWindow));

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

	public void println() {
		println("");
	}

	public void printHR() {
		lines.add(new SpecialLine("hr", currentFont, currentColor, textWindow));
	}

	public void setFont(Font font) {
		currentFont = font;
	}

	public void setColor(Color color) {
		currentColor = color;
	}

	private TextWindow createWindow() {
		TextWindow window = new TextWindow(windowWidth, windowHeight);
		window.setLocation(0, 0);
		window.setSize(new Dimension(windowWidth - 15, windowHeight));
		add(window);
		return window;
	}

	private void overlayScrollBars() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				verticalBar.updateUI();
			}
		});
	}
}
