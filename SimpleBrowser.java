/**
 * SimpleBrowser
 * A simple {@link JFrame} that can print text in different colors, fonts, and can even print some special characters (like horizontal rules).
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 20th, 2016
 */

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Dimension;

public class SimpleBrowser extends JFrame {

	private static final int DEFAULT_WINDOW_WIDTH = 1000;
	private static final int DEFAULT_WINDOW_HEIGHT = 750;

	private final int SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final int SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	private int windowWidth, windowHeight;
	private TextWindow textWindow;
	private JScrollPane scrollPane;
	private HTMLPrinter htmlPrinter;

	/**
	 * Main constructor settings width and height.
	 * @param  windowWidth  width of the frame
	 * @param  windowHeight height of the frame
	 */
	public SimpleBrowser(int windowWidth, int windowHeight) {
		super("Simple Browser");
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;

		setSize(windowWidth, windowHeight);
		center();
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		createWindow();

		textWindow.setPreferredSize(new Dimension(windowWidth, windowHeight));
		setLayout(new BorderLayout());
		addScrollPane();
		htmlPrinter = new HTMLPrinter(this, textWindow);
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
	 * Repaints components after print call
	 */
	public void cleanupAfterPrint() {
		textWindow.setPreferredSize(new Dimension(textWindow.getWidth(), textWindow.getHeight()));
		revalidate();
		scrollPane.revalidate();
	}

	/**
	 * Creates the {@link TextWindow} for this class to fill whole screen except scrollbars
	 */
	private void createWindow() {
		textWindow = new TextWindow();
		add(textWindow);
	}

	/**
	 * Returns the {@link HTMLPrinter} object for the {@link TextWindow}.
	 * @return the {@link HTMLPrinter} object
	 */
	public HTMLPrinter getHTMLPrinter() {
		return htmlPrinter;
	}

	/**
	 * Adds a scroll pane and adds its increment
	 */
	private void addScrollPane() {
		scrollPane = new JScrollPane(textWindow);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		add(scrollPane, BorderLayout.CENTER);
		scrollPane.requestFocus();
	}

	public static void main(String... pumpkins) {
		SimpleBrowser simpleBrowser = new SimpleBrowser();
		HTMLPrinter htmlPrinter = simpleBrowser.getHTMLPrinter();

		htmlPrinter.printPreformattedText("This text is pre-formatted!");
		htmlPrinter.printPreformattedText("Words     line   up       !");

		htmlPrinter.printItalic("Leaning Tower of Pisa");

		htmlPrinter.setFont(new Font("Serif", Font.PLAIN, 18));
		htmlPrinter.printParagraph("Hello World");
		htmlPrinter.setFont(new Font("SansSerif", Font.ITALIC, 50));
		htmlPrinter.printParagraph("Other World");

		htmlPrinter.setFont(new Font("Arial", Font.BOLD, 10));
		htmlPrinter.printParagraph("Multiple\nangry little\nlines");
		htmlPrinter.printParagraph(""); // this prints an empty line
		htmlPrinter.setFont(HTMLPrinter.DEFAULT_FONT);
		htmlPrinter.printParagraph("I just printed an empty line");

		htmlPrinter.printHorizontalRule();

		htmlPrinter.setFont(new Font("Serif", Font.PLAIN, 22));
		htmlPrinter.printParagraph("Some normal text is much needed over here");
		htmlPrinter.setFont(new Font("Times New Roman", Font.BOLD, 250));
		htmlPrinter.printParagraph("HUGE TEXT :D");

		htmlPrinter.printHeading1("H1 And now for something completely different\n");
		htmlPrinter.printHeading3("Colors!!!");
		htmlPrinter.setFont(new Font("Arial", Font.PLAIN, 20));
		htmlPrinter.printParagraph("");

		htmlPrinter.setColor(Color.BLUE);
		htmlPrinter.setFont(new Font("Arial", Font.PLAIN, 22));

		htmlPrinter.printParagraph("This should be blue now XD");
		htmlPrinter.setColor(Color.RED);
		htmlPrinter.printParagraph("And now red");
		htmlPrinter.printHorizontalRule();
		htmlPrinter.setFont(HTMLPrinter.DEFAULT_FONT);
		htmlPrinter.setColor(Color.GREEN);
		htmlPrinter.printParagraph("\t\tI'm glad this works!");
	}
}