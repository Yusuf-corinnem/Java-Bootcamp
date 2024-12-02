package edu.school21.numbers;

import edu.school21.numbers.exceptions.IllegalNumberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NumberWorkerTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5})
    void isPrimeForPrimes_Test(int value) {
        Assertions.assertTrue(NumberWorker.isPrime(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8})
    void isPrimeForNotPrimes_Test(int value) {
        Assertions.assertFalse(NumberWorker.isPrime(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void isPrimeForIncorrectNumbers_Test(int value) {
        assertThrows(IllegalNumberException.class, () -> {
            NumberWorker.isPrime(value);
        });
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void sumDigitsInNumberSet_Test(int number, int expectedSum) {
        Assertions.assertEquals(expectedSum, NumberWorker.digitsSum(number));
    }
}
