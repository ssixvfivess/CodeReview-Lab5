package org.example.Classes;

import org.example.Interfaces.FractionOperations;

/**
 * Класс для работы с дробями, поддерживающий основные арифметические операции.
 * Дробь хранится в несократимом виде с положительным знаменателем.
 */
public class Fraction1 implements FractionOperations<Fraction1> {
    private final int numerator;
    private final int denominator;

    /**
     * Создает новую дробь.
     * @param numerator числитель дроби
     * @param denominator знаменатель дроби (не может быть нулем)
     * @throws IllegalArgumentException если знаменатель равен нулю
     */
    public Fraction1(int numerator, int denominator) {
        validateDenominator(denominator);
        int[] simplified = simplifyFraction(numerator, denominator);
        this.numerator = simplified[0];
        this.denominator = simplified[1];
    }

    /**
     * Проверяет валидность знаменателя.
     * @param denominator знаменатель для проверки
     * @throws IllegalArgumentException если знаменатель равен нулю
     */
    private void validateDenominator(int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Знаменатель не может быть равен нулю.");
        }
    }

    /**
     * Упрощает дробь и обеспечивает положительный знаменатель.
     * @param numerator исходный числитель
     * @param denominator исходный знаменатель
     * @return массив из двух элементов: упрощенный числитель и знаменатель
     */
    private int[] simplifyFraction(int numerator, int denominator) {
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }

        int gcd = calculateGCD(Math.abs(numerator), Math.abs(denominator));
        return new int[]{numerator / gcd, denominator / gcd};
    }

    @Override
    public Fraction1 add(Fraction1 other) {
        int newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction1(newNumerator, newDenominator);
    }

    @Override
    public Fraction1 subtract(Fraction1 other) {
        int newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction1(newNumerator, newDenominator);
    }

    @Override
    public Fraction1 multiply(Fraction1 other) {
        int newNumerator = this.numerator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction1(newNumerator, newDenominator);
    }

    @Override
    public Fraction1 divide(Fraction1 other) {
        validateDivision(other);
        int newNumerator = this.numerator * other.denominator;
        int newDenominator = this.denominator * other.numerator;
        return new Fraction1(newNumerator, newDenominator);
    }

    /**
     * Проверяет возможность деления на другую дробь.
     * @param other дробь, на которую производится деление
     * @throws IllegalArgumentException если other равна нулю
     */
    private void validateDivision(Fraction1 other) {
        if (other.numerator == 0) {
            throw new IllegalArgumentException("Деление на ноль невозможно.");
        }
    }

    @Override
    public Fraction1 subtract(int number) {
        return new Fraction1(this.numerator - number * this.denominator, this.denominator);
    }

    /**
     * Вычисляет наибольший общий делитель двух чисел.
     * @param a первое число
     * @param b второе число
     * @return НОД(a, b)
     */
    private int calculateGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    // Геттеры для числителя и знаменателя (опционально)
    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }
}