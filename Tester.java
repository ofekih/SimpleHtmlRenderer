import java.awt.Font;

public class Tester {
	public static void main(String... pumpkins) {
		SimpleBrowser frame = new SimpleBrowser();
		frame.print("Hello World", new Font("Serif", Font.PLAIN, 18));
		frame.print("Other World", new Font("SansSerif", Font.ITALIC, 50));
	}
}