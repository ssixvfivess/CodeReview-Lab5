package org.example.Classes;

import org.example.Interfaces.FractionOperations;

import java.io.Serializable;
import java.util.Objects;

/**
 * Иммутабельный класс для представления математических дробей, расширяющий Number
 * и поддерживающий сериализацию. Обеспечивает корректное сравнение дробей и преобразование
 * к примитивным числовым типам.
 */
public final class Fraction4 extends Number implements Serializable, FractionOperations<Fraction4> {
    private static final long serialVersionUID = 1L;

    private final int numerator;
    private final int denominator;

    /**
     * Создает новую нормализованную дробь.
     *
     * @param numerator числитель дроби
     * @param denominator знаменатель дроби (не может быть нулем)
     * @throws IllegalArgumentException если знаменатель равен нулю
     */
    public Fraction4(int numerator, int denominator) {
        validateDenominator(denominator);
        int[] normalized = normalizeFraction(numerator, denominator);
        this.numerator = normalized[0];
        this.denominator = normalized[1];
    }

    /**
     * Проверяет валидность знаменателя.
     *
     * @param denominator проверяемый знаменатель
     * @throws IllegalArgumentException если знаменатель равен нулю
     */
    private void validateDenominator(int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Знаменатель не может быть нулевым");
        }
    }

    /**
     * Нормализует дробь (делает знаменатель положительным и сокращает дробь).
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

    /**
     * Вычисляет наибольший общий делитель двух чисел (алгоритм Евклида).
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

    /**
     * Сравнивает эту дробь с другой дробью.
     *
     * @return отрицательное число, ноль или положительное число, если текущая дробь
     *         меньше, равна или больше другой дроби соответственно
     * @throws NullPointerException если other равен null
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fraction4 fraction = (Fraction4) obj;
        // Кросс-умножение для корректного сравнения дробей
        return this.numerator * fraction.denominator == fraction.numerator * this.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    // Геттеры для доступа к состоянию объекта
    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    /**
     * Создает дробь из целого числа.
     *
     * @return дробь со знаменателем 1
     */

    @Override
    public Fraction4 add(Fraction4 other) {
        int newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction4(newNumerator, newDenominator);
    }

    @Override
    public Fraction4 subtract(Fraction4 other) {
        int newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction4(newNumerator, newDenominator);
    }

    @Override
    public Fraction4 multiply(Fraction4 other) {
        int newNumerator = this.numerator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction4(newNumerator, newDenominator);
    }

    @Override
    public Fraction4 divide(Fraction4 other) {
        if (other.numerator == 0) {
            throw new ArithmeticException("Division by zero");
        }
        int newNumerator = this.numerator * other.denominator;
        int newDenominator = this.denominator * other.numerator;
        return new Fraction4(newNumerator, newDenominator);
    }

    @Override
    public Fraction4 subtract(int number) {
        return new Fraction4(this.numerator - number * this.denominator, this.denominator);
    }

    public static Fraction4 valueOf(int value) {
        return new Fraction4(value, 1);
    }

    /**
     * Парсит строку в формате "a/b" в дробь.
     *
     * @param str строка для парсинга
     * @return созданная дробь
     * @throws NumberFormatException если строка имеет неверный формат
     */
    public static Fraction4 parseFraction(String str) {
        String[] parts = str.split("/");
        if (parts.length != 2) {
            throw new NumberFormatException("Неверный формат дроби. Ожидается a/b");
        }
        try {
            int num = Integer.parseInt(parts[0]);
            int denom = Integer.parseInt(parts[1]);
            return new Fraction4(num, denom);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Неверный числовой формат в дроби");
        }
    }
}
