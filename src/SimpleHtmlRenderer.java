import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

/**
 * The main class for SimpleHtmlRenderer, a program that can help pretty-print
 * tokenized HTML. {@code SimpleHtmlRenderer} supports various colors, fonts
 * and even some special tags such as horizontal rules. A {@link JFrame}, it
 * manages a single {@link HtmlCanvas} and allows printing to it through a
 * {@link HtmlPrinter}.
 *
 * @author Ofek Gila
 * @author Saagar Jha
 * @since October 20th, 2016
 * @see HtmlPrinter
 * @see HtmlCanvas
 */
public class SimpleHtmlRenderer extends JFrame {

	/**
	 * The default value for the width of the a {@code SimpleHtmlRenderer}
	 * window, in pixels.
	 */
	public static final int DEFAULT_WINDOW_WIDTH = 900;

	/**
	 * The default value for the height of the a {@code SimpleHtmlRenderer}
	 * window, in pixels.
	 */
	public static final int DEFAULT_WINDOW_HEIGHT = 500;

	/**
	 * The width of the screen, in pixels.
	 */
	private final int SCREEN_WIDTH =
		(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();

	/**
	 * The height of the screen, in pixels.
	 */
	private final int SCREEN_HEIGHT =
		(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	/**
	 * This {@code SimpleHtmlRenderer}'s {@code HtmlCanvas}.
	 */
	private HtmlCanvas htmlCanvas;

	/**
	 * The {@code JScrollPane} that bounds this {@code SimpleHtmlRenderer}'s
	 * {@code HtmlCanvas}.
	 */
	private JScrollPane scrollPane;

	/**
	 * The {@code HtmlPrinter} that manages printing for this
	 * {@code SimpleHtmlRenderer}'s {@code HtmlCanvas}.
	 *
	 * @see SimpleHtmlRenderer#getHtmlPrinter
	 */
	private HtmlPrinter htmlPrinter;

	/**
	 * Constructs a {@code SimpleHtmlRenderer} and creates a window with the
	 * default width and height.
	 *
	 * @see SimpleHtmlRenderer#DEFAULT_WINDOW_WIDTH
	 * @see SimpleHtmlRenderer#DEFAULT_WINDOW_HEIGHT
	 */
	public SimpleHtmlRenderer() {
		this(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
	}

	/**
	 * Constructs a {@code SimpleHtmlRenderer} and creates a window with the
	 * given width and height.
	 *
	 * @param width  The width of the window to create
	 * @param height The height of the window to create
	 */
	public SimpleHtmlRenderer(int width, int height) {
		super("Simple HTML Renderer");

		setSize(width, height);
		centerOnScreen();
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		setLayout(new BorderLayout());
		createCanvas();
		htmlPrinter = new HtmlPrinter(this, htmlCanvas);
		htmlCanvas.setHtmlComponents(htmlPrinter.getHtmlComponents());
		addScrollPane();
	}

	/**
	 * Creates the {@code HtmlCanvas} for this class and adds it to the content
	 * pane, making sure it fills the window.
	 */
	private void createCanvas() {
		htmlCanvas = new HtmlCanvas();
		htmlCanvas.setPreferredSize(new Dimension(getWidth(), getHeight()));
		add(htmlCanvas, BorderLayout.CENTER);
	}

	/**
	 * Adds a {@code JScrollPane} containing this {@code SimpleHtmlPrinter}'s
	 * {@code HtmlCanvas} to the content pane and sets its default scrolling
	 * increment.
	 */
	private void addScrollPane() {
		scrollPane = new JScrollPane(htmlCanvas);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		add(scrollPane);
		scrollPane.requestFocus(); // So that we can scroll using the keyboard
		                           // arrow keys
	}

	/**
	 * Centers this {@code SimpleHtmlRenderer} on the screen.
	 */
	private void centerOnScreen() {
		setLocation((SCREEN_WIDTH - getWidth()) / 2,
			(SCREEN_HEIGHT - getHeight()) / 2);
	}

	/**
	 * Returns the {@code HtmlPrinter} that manages drawing for this
	 * {@code SimpleHtmlRenderer}'s {@code HtmlCanvas}.
	 *
	 * @return The {@code HtmlPrinter}
	 */
	public HtmlPrinter getHtmlPrinter() {
		return htmlPrinter;
	}

	/**
	 * Informs the {@code JScrollPane} that the {@code HtmlCanvas}'s size has
	 * changed. You should not need to call this method; {@code HtmlPrinter}
	 * will take care of it automatically.
	 */
	public void cleanupAfterPrint() {
		htmlCanvas.setPreferredSize(new Dimension(htmlCanvas.getWidth(),
			htmlCanvas.getHeight()));
		revalidate(); // Notify the content pane of the size change
		scrollPane.revalidate(); // Notify the scroll pane of the size change
	}

	/**
	 * The main method for {@code SimpleHtmlRenderer}. This method is not
	 * intended to be called in production code; its exists solely as an example
	 * to showcase Simple HTML Renderer's capabilities.
	 *
	 * @param args Command line arguments, currently unused
	 */
	public static void main(String[] args) {
		SimpleHtmlRenderer simpleHtmlPrinter = new SimpleHtmlRenderer();
		HtmlPrinter htmlPrinter = simpleHtmlPrinter.getHtmlPrinter();

		// An example, for testing purposes
		htmlPrinter.printPreformattedText("This text is pre-formatted!");
		htmlPrinter.println(); // this break is necessary as the fonts are the same size (try removing it)
		htmlPrinter.printPreformattedText("Words     line   up       !");
		htmlPrinter.println();

		htmlPrinter.print("normal ");
		htmlPrinter.printItalic("italic ");
		htmlPrinter.printBold("bold");
		htmlPrinter.printBreak();

		htmlPrinter.setFont(new Font("SansSerif", Font.ITALIC, 22));
		htmlPrinter.print("Leaning Tower of ");
		htmlPrinter.printBold("Pisa");

		htmlPrinter.setFont(new Font("Serif", Font.PLAIN, 18));
		htmlPrinter.print("Hello World");
		htmlPrinter.setFont(new Font("SansSerif", Font.ITALIC, 50));
		htmlPrinter.print("Other World");

		htmlPrinter.printHorizontalRule();

		htmlPrinter.setFont(new Font("Serif", Font.PLAIN, 22));
		htmlPrinter.print("Some normal text is much needed over here");

		htmlPrinter.printBreak();
		htmlPrinter.printBreak();
		htmlPrinter.print("Time to take a break (or two)!");

		htmlPrinter.setFont(new Font("Times New Roman", Font.BOLD, 250));
		htmlPrinter.print("HUGE TEXT :D");

		htmlPrinter.printHeading1("H1 And now for something completely different");
		htmlPrinter.println(); // this println call is redundant
		// since heading 3 font is a different size than heading 1, it would
		// automatically println before outputting.
		htmlPrinter.printHeading3("Colors!!!");
		htmlPrinter.setFont(new Font("Arial", Font.PLAIN, 20));
		htmlPrinter.printBreak();

		htmlPrinter.setColor(Color.BLUE);
		htmlPrinter.setFont(new Font("Arial", Font.PLAIN, 22));

		htmlPrinter.print("This should be ");
		htmlPrinter.printBold("blue");
		htmlPrinter.print(" now XD");

		htmlPrinter.setColor(Color.RED);
		htmlPrinter.print(" And now red");

		htmlPrinter.printHorizontalRule();

		htmlPrinter.setFont(HtmlPrinter.DEFAULT_FONT);
		htmlPrinter.setColor(Color.GREEN);
		htmlPrinter.print("I'm glad this works!");

		htmlPrinter.drawLineMark(80);

		htmlPrinter.printBreak();
		htmlPrinter.setFont(HtmlPrinter.DEFAULT_FONT);
		htmlPrinter.setColor(Color.BLACK);
		htmlPrinter.printPreformattedText("111111111122222222223333333333" +
			"44444444445555555555666666666677777777778888888888");
		htmlPrinter.print("Line break at 80 :D");
	}
}
