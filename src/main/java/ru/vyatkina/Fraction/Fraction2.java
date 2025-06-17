package ru.vyatkina.Fraction;

import ru.vyatkina.Fraction.Interfaces.FractionOperations;

import java.util.Objects;

/**
 * Задание 3.1:
 * Иммутабельный класс для представления дробей с целыми числителем и знаменателем.
 * Поддерживает основные арифметические операции: сложение, вычитание, умножение и деление.
 * Автоматически сокращает дроби и поддерживает положительный знаменатель.
 * Класс объявлен как final для предотвращения создания изменяемых подклассов.
 */
public final class Fraction2 implements FractionOperations<Fraction2> {
    private final int numerator;
    private final int denominator;

    /**
     * Создает новую несократимую дробь с положительным знаменателем.
     *
     * @param numerator числитель дроби
     * @param denominator знаменатель дроби (должен быть отличен от нуля)
     * @throws IllegalArgumentException если знаменатель равен нулю
     */
    public Fraction2(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Знаменатель не может быть нулем");
        }

        int gcd = computeNOD(Math.abs(numerator), Math.abs(denominator));
        if (denominator < 0) {
            gcd = -gcd; // Сохраняем знак в числителе
        }

        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    /**
     * Вычисляет наибольший общий делитель двух чисел (алгоритм Евклида).
     *
     * @param a первое число
     * @param b второе число
     * @return НОД(a, b)
     */
    private int computeNOD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;

            a = temp;
        }
        return a;
    }

    @Override
    public Fraction2 add(Fraction2 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        int newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction2(newNumerator, newDenominator);
    }

    @Override
    public Fraction2 subtract(Fraction2 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        int newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction2(newNumerator, newDenominator);
    }

    @Override
    public Fraction2 multiply(Fraction2 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        int newNumerator = this.numerator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction2(newNumerator, newDenominator);
    }

    @Override
    public Fraction2 divide(Fraction2 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        if (other.numerator == 0) {
            throw new ArithmeticException("Деление на ноль невозможно");
        }
        int newNumerator = this.numerator * other.denominator;
        int newDenominator = this.denominator * other.numerator;
        return new Fraction2(newNumerator, newDenominator);
    }

    @Override
    public Fraction2 subtract(int number) {
        return new Fraction2(this.numerator - number * this.denominator, this.denominator);
    }

    /**
     * Возвращает числитель дроби.
     * @return числитель
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * Возвращает знаменатель дроби (всегда положительный).
     * @return знаменатель
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * Возвращает десятичное представление дроби.
     * @return результат деления числителя на знаменатель
     */
    public double toDouble() {
        return (double) numerator / denominator;
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction2 fraction = (Fraction2) o;
        return numerator == fraction.numerator &&
                denominator == fraction.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }
}