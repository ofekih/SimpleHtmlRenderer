import java.awt.Font;
import java.awt.Color;
import javax.swing.JComponent;

public class SpecialLine extends Line {

	private String tag;

	public SpecialLine(String tag, Font font, Color color, JComponent component) {
		super("", font, color, component);
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}
}