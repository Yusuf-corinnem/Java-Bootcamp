import java.util.Scanner;

import static java.lang.Math.sqrt;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = 0, countOfCoffeeRequest = 0;

        while (true) {
            number = scanner.nextInt();
            if (number == 42) break;
            int sum = sumNumbers(number);
            if (isPrimeNumber(sum)) countOfCoffeeRequest++;
        }

        System.out.println("Count of coffee-request – " + countOfCoffeeRequest);
    }

    public static int sumNumbers(int number) {
        int sum = 0, temp = 0;

        for (int i = 0; i < getNumberLength(number); ++i) {
            temp = number % 10;
            sum += temp;
            number = number / 10;
        }

        return sum;
    }

    private static int getNumberLength(int number) {
        int length = 0;

        while (number != 0) {
            number /= 10;
            length++;
        }

        return length;
    }

    public static boolean isPrimeNumber(int number) {
        if (number == 0 || number == -1) {
            System.exit(-1);
            System.out.println("Illegal Argument");
        }

        int i = 2;
        for (; i <= sqrt(number); ++i) {
            if (number % i == 0) return false;
        }

        return true;
    }
}
