package org.example.Fraction;

import org.example.Fraction.Interfaces.FractionOperations;
import java.io.Serializable;
import java.util.Objects;

/**
 * Иммутабельный класс для представления дробей, расширяющий Number и поддерживающий Serializable.
 * Обеспечивает основные арифметические операции и преобразование к примитивным числовым типам.
 */
public final class Fraction3 extends Number implements Serializable, FractionOperations<Fraction3> {
    private static final long serialVersionUID = 1L;
    private final int numerator;
    private final int denominator;

    /**
     * Создает новую сокращенную дробь с положительным знаменателем.
     *
     * @param numerator числитель
     * @param denominator знаменатель (не может быть нулем)
     * @throws IllegalArgumentException если знаменатель равен нулю
     */
    public Fraction3(int numerator, int denominator) {
        validateDenominator(denominator);
        int[] normalized = normalizeFraction(numerator, denominator);
        this.numerator = normalized[0];
        this.denominator = normalized[1];
    }

    /**
     * Проверяет валидность знаменателя.
     *
     * @param denominator проверяемый знаменатель
     * @throws IllegalArgumentException если знаменатель нулевой
     */
    private void validateDenominator(int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Знаменатель не может быть нулевым");
        }
    }

    /**
     * Нормализует дробь (сокращает и делает знаменатель положительным).
     *
     * @param numerator исходный числитель
     * @param denominator исходный знаменатель
     * @return массив из двух элементов: [нормализованный числитель, нормализованный знаменатель]
     */
    private int[] normalizeFraction(int numerator, int denominator) {
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        int gcd = computeGCD(Math.abs(numerator), denominator);
        return new int[]{numerator / gcd, denominator / gcd};
    }

    @Override
    public Fraction3 add(Fraction3 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        return new Fraction3(
                this.numerator * other.denominator + other.numerator * this.denominator,
                this.denominator * other.denominator
        );
    }

    @Override
    public Fraction3 subtract(Fraction3 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        return new Fraction3(
                this.numerator * other.denominator - other.numerator * this.denominator,
                this.denominator * other.denominator
        );
    }

    @Override
    public Fraction3 multiply(Fraction3 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        return new Fraction3(
                this.numerator * other.numerator,
                this.denominator * other.denominator
        );
    }

    @Override
    public Fraction3 divide(Fraction3 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        if (other.numerator == 0) {
            throw new ArithmeticException("Деление на нулевую дробь невозможно");
        }
        return new Fraction3(
                this.numerator * other.denominator,
                this.denominator * other.numerator
        );
    }

    @Override
    public Fraction3 subtract(int number) {
        return new Fraction3(
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

    // Реализация методов Number
    @Override
    public int intValue() {
        return numerator / denominator;
    }

    @Override
    public long longValue() {
        return (long) numerator / denominator;
    }

    @Override
    public float floatValue() {
        return (float) numerator / denominator;
    }

    @Override
    public double doubleValue() {
        return (double) numerator / denominator;
    }

    // Реализация equals и hashCode для корректной работы с коллекциями
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction3 fraction3 = (Fraction3) o;
        return numerator == fraction3.numerator &&
                denominator == fraction3.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    // Геттеры (опционально, но полезно для тестирования)
    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }
}
