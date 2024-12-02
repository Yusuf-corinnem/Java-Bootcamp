import java.util.Scanner;
import static java.lang.Math.sqrt;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        if (number == 0 || number == -1) {
            System.exit(-1);
            System.out.println( "Illegal Argument");
        }

        int i = 2;
        for (; i <= sqrt(number); ++i) {
            if (number % i == 0) {
                System.out.println("false " + (i - 1));
                return;
            }
        }

        System.out.println("true " + (i - 1));
    }
}
