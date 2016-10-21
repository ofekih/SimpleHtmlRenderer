import javax.swing.JFrame;
import javax.swing.JScrollBar;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Dimension;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import java.util.ArrayList;

public class SimpleBrowser extends JFrame {

	private final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, 21);

	private int windowWidth, windowHeight;
	private TextWindow textWindow;
	private ArrayList<Line> lines;
	private JScrollBar verticalBar, horizontalBar;
	private Font currentFont;

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

	public void println(String str, Font font) {
		String[] strings = str.split("\n");

		for (int i = 0; i < strings.length; i++)
			lines.add(new Line(strings[i], font, textWindow));

		if (lines.size() == 0)
			lines.add(new Line("", font, textWindow));

		textWindow.printLines(lines);
		verticalBar.requestFocus();
		verticalBar.requestFocusInWindow();
	}

	public void println(String str) {
		println(str, currentFont);
	}

	public void println() {
		println("");
	}

	public void setFont(Font font) {
		currentFont = font;
	}

	private TextWindow createWindow() {
		TextWindow window = new TextWindow(windowWidth, windowHeight);
		window.setLocation(0, 0);
		window.setSize(new Dimension(windowWidth - 15, windowHeight));
		add(window);
		return window;
	}
}
