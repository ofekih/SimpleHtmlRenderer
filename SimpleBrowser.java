import javax.swing.JFrame;

public class SimpleBrowser extends JFrame {

	private int windowWidth, windowHeight;

	public SimpleBrowser(int windowWidth, int windowHeight) {
		super("Simple Browser");
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		setSize(windowWidth, windowHeight);
		setVisible(true);
		setLayout(null);
		createWindow();
	}

	public SimpleBrowser() {
		this(1000, 750);
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