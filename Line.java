import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JComponent;

public class Line {

	private String text;
	private Font font;
	private FontMetrics fontMetrics;

	public Line(String text, Font font, JComponent component) {
		this.text = text;
		this.font = font;
		fontMetrics = component.getFontMetrics(font);
	}

	public String getText() {
		return text;
	}

	public Font getFont() {
		return font;
	}

	public int getLineHeight() {
		return fontMetrics.getHeight();
	}

	public int getAscent() {
		return fontMetrics.getAscent();
	}
}