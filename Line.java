import java.awt.Font;
import javax.swing.JComponent;

public class Line {

	private String text;
	private Font font;
	private int lineHeight;

	public Line(String text, Font font, JComponent component) {
		this.text = text;
		this.font = font;
		lineHeight = component.getFontMetrics(font).getHeight();
	}

	public String getText() {
		return text;
	}

	public Font getFont() {
		return font;
	}

	public int getLineHeight() {
		return lineHeight;
	}
}