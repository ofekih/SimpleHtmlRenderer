import java.awt.Font;

public class Tester {
	public static void main(String... pumpkins) {
		SimpleBrowser frame = new SimpleBrowser(1000, 750);
		frame.println("Hello World", new Font("Serif", Font.PLAIN, 18));
		frame.println("Other World", new Font("SansSerif", Font.ITALIC, 50));
		frame.println("<hr>");
		frame.println("Multiple\nangry lines", new Font("Arial", Font.BOLD, 10));
		frame.println();
		frame.println("I just printed an empty line");
		frame.printHR();
		frame.println("And now, for some normal text");

		frame.println("HUGE TEXT :D", new Font("Times New Roman", Font.BOLD, 250));
	}
}