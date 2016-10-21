import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;
import javax.swing.JComponent;

public class Line {

	private String text;
	private Font font;
	private Color color;
	private FontMetrics fontMetrics;

	public Line(String text, Font font, Color color, JComponent component) {
		this.text = text;
		this.font = font;
		this.color = color;
		fontMetrics = component.getFontMetrics(font);
	}

	public String getText() {
		return text;
	}

	public Font getFont() {
		return font;
	}

	public Color getColor() {
		return color;
	}

	public int getLineHeight() {
		return fontMetrics.getHeight();
	}

	public int getAscent() {
		return fontMetrics.getAscent();
	}
}