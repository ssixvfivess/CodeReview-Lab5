package ru.vyatkina.Fraction;

import ru.vyatkina.Fraction.Interfaces.FractionOperations;

import java.io.Serializable;
import java.util.Objects;


/**
 * Иммутабельный класс для представления дробей, расширяющий Number и поддерживающий Serializable.
 * Обеспечивает основные арифметические операции и преобразование к примитивным числовым типам.
 * Гарантирует, что дробь всегда хранится в нормализованном виде (сокращенная форма с положительным знаменателем).
 */
public final class Fraction3 extends Number implements Serializable, FractionOperations<Fraction3> {
    private static final long serialVersionUID = 1L;
    private final int numerator;
    private final int denominator;

    /**
     * Создает новую сокращенную дробь с положительным знаменателем.
     *
     * @param numerator числитель дроби
     * @param denominator знаменатель дроби (должен быть отличен от нуля)
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
     * @throws IllegalArgumentException если знаменатель равен нулю
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
        int gcd = computeNOD(Math.abs(numerator), denominator);
        return new int[]{numerator / gcd, denominator / gcd};
    }

    /**
     * Вычисляет наибольший общий делитель (НОД) двух чисел.
     *
     * @param a первое число
     * @param b второе число
     * @return НОД(a, b)
     */
    private int computeNOD(int a, int b) {
        return b == 0 ? a : computeNOD(b, a % b);
    }

    /**
     * Складывает текущую дробь с другой дробью.
     *
     * @param other другая дробь (не может быть null)
     * @return новая дробь - результат сложения
     * @throws NullPointerException если other равен null
     */
    @Override
    public Fraction3 add(Fraction3 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        return new Fraction3(
                this.numerator * other.denominator + other.numerator * this.denominator,
                this.denominator * other.denominator
        );
    }

    /**
     * Вычитает другую дробь из текущей дроби.
     *
     * @param other другая дробь (не может быть null)
     * @return новая дробь - результат вычитания
     * @throws NullPointerException если other равен null
     */
    @Override
    public Fraction3 subtract(Fraction3 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        return new Fraction3(
                this.numerator * other.denominator - other.numerator * this.denominator,
                this.denominator * other.denominator
        );
    }

    /**
     * Умножает текущую дробь на другую дробь.
     *
     * @param other другая дробь (не может быть null)
     * @return новая дробь - результат умножения
     * @throws NullPointerException если other равен null
     */
    @Override
    public Fraction3 multiply(Fraction3 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        return new Fraction3(
                this.numerator * other.numerator,
                this.denominator * other.denominator
        );
    }

    /**
     * Делит текущую дробь на другую дробь.
     *
     * @param other другая дробь (не может быть null)
     * @return новая дробь - результат деления
     * @throws NullPointerException если other равен null
     * @throws ArithmeticException если other представляет нулевую дробь
     */
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

    /**
     * Вычитает целое число из текущей дроби.
     *
     * @param number целое число для вычитания
     * @return новая дробь - результат вычитания
     */
    @Override
    public Fraction3 subtract(int number) {
        return new Fraction3(
                this.numerator - number * this.denominator,
                this.denominator
        );
    }

    /**
     * Возвращает строковое представление дроби в формате "числитель/знаменатель".
     *
     * @return строковое представление дроби
     */
    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    /**
     * Возвращает числовое значение дроби как int (целая часть от деления).
     *
     * @return числовое значение как int
     */
    @Override
    public int intValue() {
        return numerator / denominator;
    }

    /**
     * Возвращает числовое значение дроби как long (целая часть от деления).
     *
     * @return числовое значение как long
     */
    @Override
    public long longValue() {
        return (long) numerator / denominator;
    }

    /**
     * Возвращает числовое значение дроби как float.
     *
     * @return числовое значение как float
     */
    @Override
    public float floatValue() {
        return (float) numerator / denominator;
    }

    /**
     * Возвращает числовое значение дроби как double.
     *
     * @return числовое значение как double
     */
    @Override
    public double doubleValue() {
        return (double) numerator / denominator;
    }

    /**
     * Сравнивает текущую дробь с другим объектом на равенство.
     *
     * @param o объект для сравнения
     * @return true если объекты равны, false в противном случае
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction3 fraction3 = (Fraction3) o;
        return numerator == fraction3.numerator &&
                denominator == fraction3.denominator;
    }

    /**
     * Возвращает хэш-код дроби.
     *
     * @return хэш-код дроби
     */
    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }
}
