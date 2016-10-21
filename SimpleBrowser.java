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
	private Printer printer;

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

		textWindow.setPreferredSize(new Dimension(500, 500));
		setLayout(new BorderLayout());
		addScrollPane();
		printer = new Printer(this, textWindow);
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
		textWindow.setLocation(0, 0);
		textWindow.setSize(new Dimension(windowWidth - 15, windowHeight));
		add(textWindow);
	}

	/**
	 * Returns the {@link Printer} object for the {@link TextWindow}.
	 * @return the {@link Printer} object
	 */
	public Printer getPrinter() {
		return printer;
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
		SimpleBrowser simpleBrowser = new SimpleBrowser(1000, 750);
		Printer printer = simpleBrowser.getPrinter();
		printer.println("Hello World", new Font("Serif", Font.PLAIN, 18));
		printer.println("Other World", new Font("SansSerif", Font.ITALIC, 50));
		printer.println("<hr>");

		printer.printPre("This text is pre-formatted!");
		printer.printPre("Words     line   up       !");

		printer.println("Multiple\nangry little\nlines", new Font("Arial", Font.BOLD, 10));
		printer.println();
		printer.println("I just printed an empty line");

		printer.printHR();

		printer.println("And now, for some normal text", new Font("Serif", Font.PLAIN, 22));

		printer.println("HUGE TEXT :D\n", new Font("Times New Roman", Font.BOLD, 250));

		printer.setColor(Color.BLUE);
		printer.setFont(new Font("Arial", Font.PLAIN, 22));

		printer.println("This should be blue now XD");
		printer.println("And now red", Color.RED);
		printer.printHR();
		printer.setFont(Printer.DEFAULT_FONT);
		printer.println("\t\tI'm glad this works!", Color.GREEN);
	}
}
