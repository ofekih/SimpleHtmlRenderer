import java.awt.Font;
import java.awt.Color;

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
		frame.println("And now, for some normal text", new Font("Serif", Font.PLAIN, 22));

		frame.println("HUGE TEXT :D\n", new Font("Times New Roman", Font.BOLD, 250));

		frame.setColor(Color.BLUE);
		frame.setFont(new Font("Arial", Font.PLAIN, 22));
		frame.println("This should be blue now XD");
		frame.println("And now red", Color.RED);
		frame.printHR();
		frame.println("\t\tI'm glad this works!", Color.GREEN);
	}
}