package org.example.Classes;

import org.example.Interfaces.FractionOperations;

/**
 * Иммутабельный класс для представления дробей с целыми числителем и знаменателем.
 * Поддерживает основные арифметические операции: сложение, вычитание, умножение и деление.
 * Автоматически сокращает дроби и поддерживает положительный знаменатель.
 */
public final class Fraction2 implements FractionOperations<Fraction2> {
    private final int numerator;
    private final int denominator;

    /**
     * Создает новую дробь.
     *
     * @param numerator   числитель дроби
     * @param denominator знаменатель дроби (должен быть отличен от нуля)
     * @throws IllegalArgumentException если знаменатель равен нулю
     */
    public Fraction2(int numerator, int denominator) {
        this.numerator = normalizeNumerator(numerator, denominator);
        this.denominator = normalizeDenominator(denominator);
    }

    /**
     * Нормализует числитель с учетом знака знаменателя.
     *
     * @param numerator   исходный числитель
     * @param denominator исходный знаменатель
     * @return нормализованный числитель
     */
    private int normalizeNumerator(int numerator, int denominator) {
        int gcd = computeGCD(Math.abs(numerator), Math.abs(denominator));
        return denominator < 0 ? -numerator / gcd : numerator / gcd;
    }

    /**
     * Нормализует знаменатель (делает положительным и сокращает).
     *
     * @param denominator исходный знаменатель
     * @return нормализованный знаменатель
     * @throws IllegalArgumentException если знаменатель равен нулю
     */
    private int normalizeDenominator(int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }
        int absDenominator = Math.abs(denominator);
        return absDenominator / computeGCD(Math.abs(numerator), absDenominator);
    }

    @Override
    public Fraction2 add(Fraction2 other) {
        return new Fraction2(
                this.numerator * other.denominator + other.numerator * this.denominator,
                this.denominator * other.denominator
        );
    }

    @Override
    public Fraction2 subtract(Fraction2 other) {
        return new Fraction2(
                this.numerator * other.denominator - other.numerator * this.denominator,
                this.denominator * other.denominator
        );
    }

    @Override
    public Fraction2 multiply(Fraction2 other) {
        return new Fraction2(
                this.numerator * other.numerator,
                this.denominator * other.denominator
        );
    }

    @Override
    public Fraction2 divide(Fraction2 other) {
        if (other.numerator == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return new Fraction2(
                this.numerator * other.denominator,
                this.denominator * other.numerator
        );
    }

    @Override
    public Fraction2 subtract(int number) {
        return new Fraction2(
                this.numerator - number * this.denominator,
                this.denominator
        );
    }

    /**
     * Вычисляет наибольший общий делитель (НОД) двух чисел.
     *
     * @param a первое число
     * @param b второе число
     * @return НОД(a, b)
     */
    private int computeGCD(int a, int b) {
        return b == 0 ? a : computeGCD(b, a % b);
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    // Геттеры (не обязательные, но полезные для тестирования)
    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction2 fraction2 = (Fraction2) o;
        return numerator == fraction2.numerator &&
                denominator == fraction2.denominator;
    }

    @Override
    public int hashCode() {
        return 31 * numerator + denominator;
    }
}
