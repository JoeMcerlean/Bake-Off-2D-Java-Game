import java.io.IOException;

public class Driver{

	public static void main(String[] args) {

		if (System.getProperty("os.name").startsWith("Windows") || System.getProperty("os.name").startsWith("Linux")) {
            String[] args2 = {""};
            gameDriver.main(args2);
        } else {
            try {
                Runtime.getRuntime().exec("java -XstartOnFirstThread -cp lib/jsfml.jar:. gameDriver");
            } catch (IOException ex) {

                System.out.println("starting error - UNIX");
            }
        }
	}
}