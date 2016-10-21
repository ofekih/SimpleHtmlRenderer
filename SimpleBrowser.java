import javax.swing.JFrame;
import javax.swing.JScrollBar;

import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import java.util.ArrayList;

public class SimpleBrowser extends JFrame {

	private final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, 21);
	private final Color DEFAULT_COLOR = Color.BLACK;

	private int windowWidth, windowHeight;
	private TextWindow textWindow;
	private ArrayList<Line> lines;
	private JScrollBar verticalBar, horizontalBar;
	private Font currentFont;
	private Color currentColor;

	public SimpleBrowser(int windowWidth, int windowHeight) {
		super("Simple Browser");
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		currentFont = DEFAULT_FONT;
		setSize(windowWidth, windowHeight);
		center(windowWidth, windowHeight);
		setVisible(true);
		setLayout(null);
		setResizable(false);
		textWindow = createWindow();
		addScrollBars();
		lines = new ArrayList<Line>();
	}

	public SimpleBrowser() {
		this(1000, 750);
	}

	private void center(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)screenSize.getWidth();
		int screenHeight = (int)screenSize.getHeight();
		setLocation((screenWidth - width) / 2, (screenHeight - height) / 2);
	}

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

	public void println(String str, Font font, Color color) {
		currentFont = font;
		currentColor = color;
		str += " ";
		str = str.replace("\t", "    ");
		String[] strings = str.split("\n");

		for (int i = 0; i < strings.length; i++)
			lines.add(new Line(strings[i], font, color, textWindow));

		if (lines.size() == 0)
			lines.add(new Line("", font, color, textWindow));

		textWindow.printLines(lines);
		verticalBar.requestFocus();
		verticalBar.requestFocusInWindow();
	}

	public void println(String str, Font font) {
		println(str, font, currentColor);
	}

	public void println(String str, Color color) {
		println(str, currentFont, color);
	}

	public void println(String str) {
		println(str, currentFont);
	}

	public void println() {
		println("");
	}

	public void printHR() {
		lines.add(new Line("o_OhrO_o", currentFont, currentColor, textWindow));
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
}
