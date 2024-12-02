package edu.school21.numbers;

import edu.school21.numbers.exceptions.IllegalNumberException;

import static java.lang.Math.sqrt;

public class NumberWorker {
    public static boolean isPrime(int number) {
        if (number < 2) throw new IllegalNumberException("Illegal argument");

        for (int i = 2; i <= sqrt(number); ++i) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static int digitsSum(int number) {
        int sum = 0;

        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }

        return sum;
    }

}
