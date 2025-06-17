package ru.vyatkina.Fraction;

import ru.vyatkina.Fraction.Interfaces.FractionOperations;

import java.io.Serializable;
import java.util.Objects;
import java.util.Collection;


/**
 * Иммутабельный класс для представления математических дробей, расширяющий Number
 * и поддерживающий сериализацию. Обеспечивает корректное сравнение дробей и преобразование
 * к примитивным числовым типам. Реализует интерфейс FractionOperations для арифметических операций.
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
     * Возвращает целочисленное значение дроби (целая часть от деления).
     *
     * @return целочисленное значение дроби
     */
    @Override
    public int intValue() {
        return numerator / denominator;
    }

    /**
     * Возвращает значение дроби как long (целая часть от деления).
     *
     * @return значение дроби как long
     */
    @Override
    public long longValue() {
        return (long) numerator / denominator;
    }

    /**
     * Возвращает значение дроби как float.
     *
     * @return значение дроби как float
     */
    @Override
    public float floatValue() {
        return (float) numerator / denominator;
    }

    /**
     * Возвращает значение дроби как double.
     *
     * @return значение дроби как double
     */
    @Override
    public double doubleValue() {
        return (double) numerator / denominator;
    }

    /**
     * Сравнивает эту дробь с другим объектом на равенство.
     *
     * @param obj объект для сравнения
     * @return true если дроби равны, false в противном случае
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fraction4 fraction = (Fraction4) obj;
        return this.numerator * fraction.denominator == fraction.numerator * this.denominator;
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


    /**
     * Складывает текущую дробь с другой дробью.
     *
     * @param other другая дробь для сложения
     * @return новая дробь - результат сложения
     * @throws NullPointerException если other равен null
     */
    @Override
    public Fraction4 add(Fraction4 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        int newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction4(newNumerator, newDenominator);
    }

    /**
     * Вычитает другую дробь из текущей дроби.
     *
     * @param other другая дробь для вычитания
     * @return новая дробь - результат вычитания
     * @throws NullPointerException если other равен null
     */
    @Override
    public Fraction4 subtract(Fraction4 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        int newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction4(newNumerator, newDenominator);
    }

    /**
     * Умножает текущую дробь на другую дробь.
     *
     * @param other другая дробь для умножения
     * @return новая дробь - результат умножения
     * @throws NullPointerException если other равен null
     */
    @Override
    public Fraction4 multiply(Fraction4 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        int newNumerator = this.numerator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction4(newNumerator, newDenominator);
    }

    /**
     * Делит текущую дробь на другую дробь.
     *
     * @param other другая дробь для деления
     * @return новая дробь - результат деления
     * @throws NullPointerException если other равен null
     * @throws ArithmeticException если other представляет нулевую дробь
     */
    @Override
    public Fraction4 divide(Fraction4 other) {
        Objects.requireNonNull(other, "Другая дробь не может быть null");
        if (other.numerator == 0) {
            throw new ArithmeticException("Деление на нулевую дробь невозможно");
        }
        int newNumerator = this.numerator * other.denominator;
        int newDenominator = this.denominator * other.numerator;
        return new Fraction4(newNumerator, newDenominator);
    }

    /**
     * Вычитает целое число из текущей дроби.
     *
     * @param number целое число для вычитания
     * @return новая дробь - результат вычитания
     */
    @Override
    public Fraction4 subtract(int number) {
        return new Fraction4(this.numerator - number * this.denominator, this.denominator);
    }

    /**
     * Создает дробь из целого числа.
     *
     * @param value целое число
     * @return дробь со знаменателем 1
     */
    public static Fraction4 valueOf(int value) {
        return new Fraction4(value, 1);
    }

    /**
     * Парсит строку в формате "a/b" в дробь.
     *
     * @param str строка для парсинга
     * @return созданная дробь
     * @throws NumberFormatException если строка имеет неверный формат
     * @throws IllegalArgumentException если знаменатель равен нулю
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

    /**
     * Вычисляет сумму набора числовых значений в вещественной форме.
     *
     * @param numbers коллекция чисел, реализующих интерфейс Number
     * @return сумма чисел в виде double
     * @throws NullPointerException если numbers или любой его элемент равен null
     */
    public static double sumAsDouble(Collection<? extends Number> numbers) {
        Objects.requireNonNull(numbers, "Коллекция чисел не может быть null");
        return numbers.stream()
                .mapToDouble(n -> {
                    Objects.requireNonNull(n, "Элемент коллекции не может быть null");
                    return n.doubleValue();
                })
                .sum();
    }
}
