import javax.swing.JFrame;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Dimension;

import java.util.ArrayList;

public class SimpleBrowser extends JFrame {

	private final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, 21);

	private int windowWidth, windowHeight;
	private TextWindow textWindow;
	private ArrayList<Line> lines;

	public SimpleBrowser(int windowWidth, int windowHeight) {
		super("Simple Browser");
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		setSize(windowWidth, windowHeight);
		center(windowWidth, windowHeight);
		setVisible(true);
		setLayout(null);
		textWindow = createWindow();
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

	public void print(String str, Font font) {
		String[] strings = str.split("\n");

		for (int i = 0; i < strings.length; i++)
			lines.add(new Line(strings[i], font, textWindow));

		textWindow.printLines(lines);
	}

	public void print(String str) {
		print(str, DEFAULT_FONT);
	}

	private TextWindow createWindow() {
		TextWindow window = new TextWindow(windowWidth, windowHeight);
		window.setLocation(0, 0);
		add(window);
		return window;
	}
}

// o TextWindow createWindow(int xSize, int ySize) - displays a text panel of the specified size, returns a pointer to the TextWindow
// o void setWindowSize(int xSize, int ySize) - sets the size of the text window
// o void setFont(Font font) - sets the current font for the text that would follow
// o void print(String str) - prints the string to the window