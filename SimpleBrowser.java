import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.FlowLayout;

public class SimpleBrowser extends JFrame {

	private int windowWidth, windowHeight;
	private int currY;

	public SimpleBrowser(int windowWidth, int windowHeight) {
		super("Simple Browser");
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		setSize(windowWidth, windowHeight);
		setVisible(true);
		setLayout(null);
	}

	public SimpleBrowser() {
		this(1000, 750);
	}

	public TextWindow createWindow(int xSize, int ySize) {
		TextWindow window = new TextWindow(xSize, ySize);
		window.setLocation((windowWidth - xSize) / 2, 15);
		add(window);
		return window;
	}
}
// o TextWindow createWindow(int xSize, int ySize) - displays a text panel of the specified size, returns a pointer to the TextWindow

class TextWindow extends JPanel {

	private int xSize, ySize;

	public TextWindow(int xSize, int ySize) {
		setWindowSize(xSize, ySize);
	}

	public void setWindowSize(int xSize, int ySize) {
		setSize(xSize, ySize);
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void drawBorder(Graphics g) {
		g.setColor(Color.BLACK);
		// g.drawRect(0, 0, xSize - 1, ySize - 1);
	}
}

// o void setWindowSize(int xSize, int ySize) - sets the size of the text window
// o void setFont(Font font) - sets the current font for the text that would follow
// o void print(String str) - prints the string to the window